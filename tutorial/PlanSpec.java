package tutorial;
import com.atlassian.bamboo.specs.util.Logger.LogLevel;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.*;
import com.atlassian.bamboo.specs.api.BambooSpec;
import com.atlassian.bamboo.specs.api.builders.plan.Job;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.builders.plan.PlanIdentifier;
import com.atlassian.bamboo.specs.api.builders.plan.Stage;
import com.atlassian.bamboo.specs.api.builders.plan.branches.BranchCleanup;
import com.atlassian.bamboo.specs.api.builders.plan.branches.PlanBranchManagement;
import com.atlassian.bamboo.specs.api.builders.project.Project;
import com.atlassian.bamboo.specs.api.builders.repository.VcsRepository;
import com.atlassian.bamboo.specs.api.builders.requirement.Requirement;
import com.atlassian.bamboo.specs.builders.task.ScriptTask;
import com.atlassian.bamboo.specs.builders.task.VcsCheckoutTask;
import com.atlassian.bamboo.specs.builders.trigger.RepositoryPollingTrigger;
import com.atlassian.bamboo.specs.builders.repository.git.GitRepository;
import com.atlassian.bamboo.specs.builders.repository.bitbucket.*;
import com.atlassian.bamboo.specs.builders.repository.bitbucket.server.BitbucketServerRepository;
import com.atlassian.bamboo.specs.builders.task.CheckoutItem;
import com.atlassian.bamboo.specs.util.BambooServer;
import com.atlassian.bamboo.specs.util.Logger;
import com.atlassian.bamboo.specs.api.builders.permission.Permissions;
import com.atlassian.bamboo.specs.api.builders.Variable;
import com.atlassian.bamboo.specs.api.builders.applink.ApplicationLink;
import com.atlassian.bamboo.specs.api.builders.permission.PermissionType;
import com.atlassian.bamboo.specs.api.builders.permission.PlanPermissions;

/**
 * Plan configuration for Bamboo.
 * Learn more on: <a href="https://confluence.atlassian.com/display/BAMBOO/Bamboo+Specs">https://confluence.atlassian.com/display/BAMBOO/Bamboo+Specs</a>
 */
@BambooSpec
public class PlanSpec {

    /**
     * Run main to publish plan on Bamboo
     */
	private static final Logger log = Logger.getLogger(BambooServer.class);
	public static List<Variable> varList = new ArrayList<Variable>();//to store plan variables
    public static void main(final String[] args) throws Exception
    {
        //By default credentials are read from the '.credentials' file.
        BambooServer bambooServer = new BambooServer("https://bamboo.honeywell.com");
        BufferedReader reader;
    	String line = "";
    	api obj = new api();
    	int c=0;
		try {
			reader = new BufferedReader(new FileReader(
					"C:/Users/h280674/Desktop/plans2.csv"));
			line = reader.readLine();
			while (line != null) {
				c+=1;
				if (c!=3 )// to only publish sent login
				{
					line = reader.readLine();
					continue;
				}
				if (c==7)//just to ignore redis for now
				{
					System.out.println("ignoring this one because its redis ");
					line = reader.readLine();
					continue;
				}
				if (c==9)//only had to do this for workflow
					api.projname = "SENDKR-";
				String[] data = line.split(",");
				System.out.print(data[0] + " and link is " + data[1]);
				System.out.println();
				varList.clear();
				obj.mainapi(data[1]);
				line = reader.readLine();
				Plan plan = new PlanSpec().createPlan(data);
		        
		        //uncomment the next line to publish the plan
		        bambooServer.publish(plan);

		        PlanPermissions planPermission = new PlanSpec().createPlanPermission(plan.getIdentifier());
		        
		        //uncomment next line to publish new plan permissions.
		        bambooServer.publish(planPermission);
		        break;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("job is done ^_^");
        
    }
    //main ends here
    
    PlanPermissions createPlanPermission(PlanIdentifier planIdentifier) {
        Permissions permission = new Permissions()
                .userPermissions("h280674", PermissionType.ADMIN, PermissionType.CLONE, PermissionType.EDIT, PermissionType.VIEW )
                //.groupPermissions("bamboo-admin", PermissionType.ADMIN)
                .loggedInUserPermissions(PermissionType.EDIT,PermissionType.ADMIN, PermissionType.CLONE,PermissionType.VIEW)
                .anonymousUserPermissionView();
        return new PlanPermissions(planIdentifier.getProjectKey(), planIdentifier.getPlanKey()).permissions(permission);
    }

    Project project() {
        return new Project()
                .name("Sentience IAM")
                .key("SIAM");//name and key of the project you want to publish your plan to.
    }

    Plan createPlan(String[] data) {
    	 //making new task for checking out repo
        CheckoutItem defaultRepository = new CheckoutItem()
        	    .defaultRepository();
        	
        Variable[] varArray = varList.toArray(new Variable[varList.size()]);
        // converting list to variable array.
        
        String repoUrl = "ssh://git@bitbucket.honeywell.com:7999/" + data[3] + ".git";//changed from qa to bitbucket.
        String column3 = data[3];
        int ind = column3.lastIndexOf('/');//to get name of the repository(repository slug)
        String repoSlug = column3.substring(ind+1);
        System.out.println("The repo SLug is ---->  " + repoSlug);
        System.out.println("The repo URL is ---->  " + repoUrl);
        return new Plan(
                project(),
                data[0], data[1])
        		.planRepositories(new BitbucketServerRepository()
                        .name(data[2])
                        .server(new ApplicationLink()
                            .name("Honeywell Bitbucket")
                        	.id("eb31011a-a875-39ec-98a0-1e914cf0d5d7"))
                        .projectKey("SIMSS")
                        .repositorySlug(repoSlug)
                        //.sshCloneUrl(repoUrl) //version 6.2.5 doesnt have sshCloneUrl attribute. 
                        .branch(data[4]))
                .description("")
                .triggers(new RepositoryPollingTrigger()
                        .pollEvery(60, TimeUnit.SECONDS))//confirm if 60 or 180
                .variables(varArray)
                .planBranchManagement(new PlanBranchManagement()
                        .createForVcsBranch()
                        .notificationForCommitters()
                        .delete(new BranchCleanup()
                            .whenInactiveInRepositoryAfterDays(30)
                            .whenRemovedFromRepositoryAfterDays(7)))
                        
                
                
                .stages(new Stage(data[5])
                        .jobs(new Job(data[6], "JOB")
                        		.requirements(new Requirement("EIT_actaz-prod-b20(10.8.84.222)"))
                        		.tasks(new VcsCheckoutTask()
                        			    .description(data[7])
                        			    .cleanCheckout(false)
                        			    .checkoutItems(defaultRepository))
                        		.tasks(new ScriptTask().description(data[8])
                        				 .interpreterBinSh()
                        				    .fileFromPath(data[9])
                        				    .argument(data[10])
                        				    )));
                        				    
                        				    
        
    }
    
}

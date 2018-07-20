package tutorial;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringBufferInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.atlassian.bamboo.specs.api.builders.Variable;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;

/*import org.jdom2.Attribute;
//import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;*/

import org.w3c.dom.*;
import javax.xml.parsers.*;
public class api 
{
	public static String projname = "SI-";
	public static void mainapi(String planKey) throws Exception
	{
		
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		      public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		        return null;
		      }

		      @Override
			public void checkClientTrusted(X509Certificate[] certs, String authType)  {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void checkServerTrusted(X509Certificate[] certs, String authType)  {
				// TODO Auto-generated method stub
				
			}
		    } };

		    SSLContext sc = SSLContext.getInstance("SSL");
		    sc.init(null, trustAllCerts, new java.security.SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		    
		    String something = getVariables(planKey);
		    System.out.println(something);
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new StringBufferInputStream(something));
		    Element root = document.getDocumentElement();
		    System.out.println(root.getNodeName());
		    NodeList nList = document.getElementsByTagName("variable");
		    System.out.println("============================");

		    for (int temp = 0; temp < nList.getLength(); temp++)
		    {
		     Node node = nList.item(temp);
		     System.out.println("");    //Just a separator
		     if (node.getNodeType() == Node.ELEMENT_NODE)
		     {
		        Element eElement = (Element) node;
		        String key = eElement.getElementsByTagName("key").item(0).getTextContent();
		        String isPass = eElement.getElementsByTagName("isPassword").item(0).getTextContent();
		        String isGlobal = eElement.getElementsByTagName("variableType").item(0).getTextContent();
		        Boolean isPassword = Boolean.valueOf(isPass);
		        String value = "";
		        if (!isPassword)
		        	value = eElement.getElementsByTagName("value").item(0).getTextContent();
		        System.out.println("key : "  + key);
		        System.out.println("isPassword : "  + isPass);
		        System.out.println("Value : "  + value);
		        if (!isGlobal.equals("GLOBAL"))
		        	PlanSpec.varList.add(new Variable(key,value));
		     }
		    }
	}
	public static String getVariables(String planKey) throws Exception
	{
		//planKey = planKey.substring(0, planKey.length() - 1);//to remove last character.
		String url = "https://build.chevalpartners.com/rest/api/latest/plan/"+ projname + planKey + "/?expand=variableContext";
		
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("Authorization", "Basic aDI4MDY3NDpoMjgwNjc0");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
		return response.toString();
	}
}

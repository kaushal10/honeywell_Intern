using System;
using System.Collections.Generic;
using System.Linq;

namespace kaushal
{

    class MainClass
    {
        public static string result = "";
        static int zeroPos = 5;
        // state space tree nodes
        class Node
        {
            // stores parent node of current node
            // helps in tracing path when answer is found
            public Node parent;

            // stores matrix
            public int[,] mat = new int[4, 4];

            // stores blank tile cordinates
            public int x, y;

            // stores the number of misplaced tiles
            public int cost;

            // stores the number of moves so far
            public int level;
        };

        // Function to print N x N matrix
        static void printMatrix(int[,] mat)
        {
            int pos = 0;
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 4; j++)
                {
                    Console.Write(mat[i, j] + " ");
                    if (mat[i, j] == 0)
                        pos = 4 * i + j;
                }

                Console.Write("\n");
            }
            getDirection(pos);
        }

        //function to get direction
        public static void getDirection(int pos)
        {
            Console.WriteLine("zeroPos is {0} and pos is {1}", zeroPos, pos);
            if (pos - zeroPos == 1)
                result += "R";
            if (pos - zeroPos == 3)
                result += "D";
            if (pos - zeroPos == -1)
                result += "L";
            if (pos - zeroPos == -3)
                result += "U";
            zeroPos = pos;
        }


        // Function to allocate a new node
        static Node newNode(int[,] mat, int x, int y, int newX,
                      int newY, int level, Node parent)
        {
            Node node = new Node();

            // set pointer for path to root
            node.parent = parent;

            // copy data from parent node to current node
            node.mat = (int[,])mat.Clone();
            //memcpy(node.mat, mat, sizeof node->mat);

            // move tile by 1 postion
            int temp = node.mat[x, y];
            node.mat[x, y] = node.mat[newX, newY];
            node.mat[newX, newY] = temp;

            // set number of misplaced tiles
            node.cost = Int32.MaxValue;

            // set number of moves so far
            node.level = level;

            // update new blank tile cordinates
            node.x = newX;
            node.y = newY;

            return node;
        }

        // botton, left, top, right
        static int[] row = { 1, 0, -1, 0 };
        static int[] col = { 0, -1, 0, 1 };

        // Function to calculate the the number of misplaced tiles
        // ie. number of non-blank tiles not in their goal position
        static int calculateCost(int[,] initial, int[,] final)
        {
            //int count = 0;
            //for (int i = 0; i < 3; i++)
            //    for (int j = 0; j < 3; j++)
            //        if (initial[i,j] != 0 && initial[i,j] != final[i,j])
            //        //if (initial[i, j] != final[i, j])
            //            count++;
            //return count;
            int sum = 0;
            for (int i = 0; i < 4; i++) 
                for (int j = 0; j < 4; j++) {
                    int ele = initial[i, j];
                    int index;
                    for (int k = 0; k < 4; k++) {
                        for (int l = 0; l < 4; l++) {
                            if(final[k,l] == ele) {
                                sum += Math.Abs(k - i) + Math.Abs(j - l);
                                k = 10; // just to break from outer loop
                                break;
                            }
                        }
                }
            }
            return sum;

        }



        // Function to check if (x, y) is a valid matrix cordinate
        static bool isSafe(int x, int y)
        {
            return (x >= 0 && x < 4 && y >= 0 && y < 4);
        }

        // print path from root node to destination node
        static void printPath(Node root)
        {
            if (root == null)
                return;
            printPath(root.parent);
            printMatrix(root.mat);

            Console.Write("\n");
        }

        static void printpq(List<Node> pq) {
            foreach(Node p in pq) {
                printMatrix(p.mat);
                Console.WriteLine(p.cost + p.level);
                Console.WriteLine("^^^^^^^^^^^^^^^^^^");
            }

        }

        static Node minNode(List<Node> list)
        {

            Node min = null;
            int mincost = 0;
            foreach (Node n in list)
            {
                if (min == null)
                {
                    min = n;
                    mincost = n.cost + n.level;
                    continue;

                }

                if (n.cost + n.level < mincost)
                {
                    min = n;
                    mincost = n.cost + n.level;
                }
            }
            return min;
        }

        static void insert(List<Node> list, Node node) {
            if (list.Count == 0) { list.Add(node); return; }
            int x = 0;
            for (x = 0; x < list.Count; x++) {
                Node n = list.ElementAt(x);
                if (n.cost + n.level > node.cost + node.level)
                    break;
            }
            list.Insert(x, node);
        }

        // Function to solve N*N - 1 puzzle algorithm using
        // Branch and Bound. x and y are blank tile coordinates
        // in initial state
        static void solve(int[,] initial, int x, int y,
                   int[,] final)
        {
            // Create a priority queue to store live nodes of
            List<Node> pq = new List<Node>();

            //priority_queue<Node*, std::vector<Node*>, comp> pq;

            // create a root node and calculate its cost
            Node root = newNode(initial, x, y, x, y, 0, null);
            root.cost = calculateCost(initial, final);

            // Add root to list of live nodes;
            //insert(pq, root);
            pq.Add(root);

            // Finds a live node with least cost,
            // add its childrens to list of live nodes and
            // finally deletes it from the list.
            while (pq.Count != 0)
            {
                //Console.WriteLine("########################");
                //printpq(pq);
                Node min = minNode(pq);
                pq.Remove(min);

                //Node min = pq.ElementAt(0);
                //pq.RemoveAt(0);
                //// Find a live node with least estimated cost
                //Node min = pq.top();

                // The found node is deleted from the list of
                // live nodes
                //pq.pop();

                // if min is an answer node
                if (min.cost == 0)
                {
                    // print the path from root to destination;
                    printPath(min);
                    return;
                }

                // do for each child of min
                // max 4 children for a node
                for (int i = 0; i < 4; i++)
                {
                    if (isSafe(min.x + row[i], min.y + col[i]))
                    {
                        // create a child node and calculate
                        // its cost
                        Node child = newNode(min.mat, min.x,
                                      min.y, min.x + row[i],
                                      min.y + col[i],
                                      min.level + 1, min);
                        child.cost = calculateCost(child.mat, final);

                        //bool xo = false;
                        //foreach (Node n in pq)
                        //{
                        //    bool m = false;
                        //    for (int xx = 0; xx < 3; xx++)
                        //    {
                        //        for (int yy = 0; yy < 3; yy++)
                        //        {
                        //            if (n.mat[xx, yy] != child.mat[xx, yy])
                        //            { m = true; break; }
                        //        }

                        //    }
                        //    if (m == false) { xo = true; break; }
                        //}
                        //insert(pq, child);
                            pq.Add(child);
                    }


                }
            }
        }

        // Driver code
        public static void solveAi(int [,] initial , int [,] final , int x , int y)
        {
            DateTime startTime = System.DateTime.Now;
            // Initial configuration
            // Value 0 is used for empty space


            /*int[,] initial =
            {
            {1, 2, 3},
            {5, 6, 0},
            {7, 8, 4}
        };

            // Solvable Final configuration
            // Value 0 is used for empty space
            int[,] final =
            {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };

            // Blank tile coordinates in initial
            // configuration
            int x = 1, y = 2;*/
            zeroPos = 4*x + y;
            solve(initial, x, y, final);
            DateTime endTime = System.DateTime.Now;
            Console.WriteLine("This program took {0} seconds to complete.", (endTime - startTime).TotalSeconds);
            Console.WriteLine("result is {0}", result);
            #if DEBUG
            Console.WriteLine("Press enter to close...");
            Console.ReadLine();
#endif
        }
    }
}
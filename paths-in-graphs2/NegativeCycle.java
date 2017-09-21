import java.util.ArrayList;
import java.util.Scanner;

public class NegativeCycle {
    private static int negativeCycle(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int n) {
        int[] dist = new int[n];
        int[] prev = new int[n];

        // finding sum of all the costs
        int maxCost = 0;
        for(int j=0;j<n;j++) {
            for(int i=0;i<cost[j].size();i++) maxCost+=cost[j].get(i);
        }

        for(int i=0;i<n;i++) {
            dist[i] = maxCost+100;
            prev[i] = -1;
        }

        // taking source node to be 0th node
        dist[0] = 0;

        // |V|-1 times
        int u, v;
        for(int i=1;i<n;i++) {
            for(int j=0;j<n;j++) {
                for(int k=0;k<adj[j].size();k++) {
                    u = j;
                    v = adj[u].get(k);
                    // relaxing all the edges
                    if(dist[v] > dist[u] + cost[u].get(k)) {
                        dist[v] = dist[u] + cost[u].get(k);
                        prev[v] = u;
                    }
                }
            }
        }

        // [V|th iteration, if distances are further updated in this, negative cycle exists
        for(int j=0;j<n;j++) {
            for(int k=0;k<adj[j].size();k++) {
                u = j;
                v = adj[u].get(k);
                // relaxing all the edges
                if(dist[v] > dist[u] + cost[u].get(k)) {
                    return 1;
                }
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        ArrayList<Integer>[] cost = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
            cost[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y, w;
            x = scanner.nextInt();
            y = scanner.nextInt();
            w = scanner.nextInt();
            adj[x - 1].add(y - 1);
            cost[x - 1].add(w);
        }
        System.out.println(negativeCycle(adj, cost, n));
    }
}
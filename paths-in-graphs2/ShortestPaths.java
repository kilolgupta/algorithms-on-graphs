import java.util.*;

public class ShortestPaths {

    private static int findNegativeCycle(int s, ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int[] dist, int[] prev) {
        int discoveredNodeInCycle, prevNode, u, v;
        for(int k=0;k<adj[s].size(); k++) {
            u = s;
            v = adj[s].get(k);
            if (dist[v] > dist[u] + cost[u].get(k)) {
                dist[v] = Integer.MIN_VALUE;
                discoveredNodeInCycle = v;
                prevNode = prev[discoveredNodeInCycle];
                while(prevNode!=v) {
                    dist[prevNode] = Integer.MIN_VALUE;
                    discoveredNodeInCycle = prevNode;
                    prevNode = prev[discoveredNodeInCycle];
                }
                return 1; // negative cycle found
            }
        }
        return 0; // no negative cycle found yet
    }

    private static int relaxEdges(int s, ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int[] dist, int[] prev, Map <Integer, Integer> relaxedEdges) {
        int u, v;
        for(int k=0;k<adj[s].size(); k++) {
            u = s;
            v = adj[s].get(k);
            for(Map.Entry<Integer, Integer> entry: relaxedEdges.entrySet()) {
                if(entry.getKey()==u && entry.getValue()==v) return -1;
            }
            relaxedEdges.put(u, v);
            if (dist[v] > dist[u] + cost[u].get(k)) {
                dist[v] = dist[u] + cost[u].get(k);
                prev[v] = u;
            }
        }
        return 1;
    }

    private static void shortestPaths(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int n, int[] dist, int maxCost) {

        int[] prev = new int[n];
        for(int i=0;i<n;i++) {
            dist[i] = maxCost+100;
            prev[i] = -1;
        }

        // source node
        dist[s] = 0;

        Queue<Integer> q = new LinkedList<>();
        Map <Integer, Integer> relaxedEdges = new HashMap<Integer, Integer>();
        int check;
        // modified bellman ford to relax only those edges reachable from source node
        for(int i=1;i<n;i++) {
            q.add(s);
            while(!q.isEmpty()) {
                int currentNode = q.remove();
                check = relaxEdges(currentNode, adj, cost, dist, prev, relaxedEdges);
                if(check == -1) break;
                for (int k = 0; k < adj[currentNode].size(); k++) {
                    if (!q.contains(adj[currentNode].get(k)))
                        q.add(adj[currentNode].get(k));
                }
            }
        }

        Queue<Integer> q1 = new LinkedList<>();
        q1.add(s);
        int result;
        while(!q1.isEmpty()) {
            int currentNode = q1.remove();
            result = findNegativeCycle(currentNode, adj, cost, dist, prev);
            if(result==1) break; // negative cycle found, break
            for(int k=0;k<adj[currentNode].size();k++) {
                if(!q1.contains(adj[currentNode].get(k)))
                    q1.add(adj[currentNode].get(k));
            }
        }
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
        int s = scanner.nextInt() - 1;
        int[] dist = new int[n];
        // finding sum of all the costs
        int maxCost = 0;
        for(int j=0;j<n;j++) {
            for(int i=0;i<cost[j].size();i++) maxCost+=cost[j].get(i);
        }

        shortestPaths(adj, cost, s, n, dist, maxCost);

        for(int i=0;i<n;i++) {
            if(dist[i]==Integer.MIN_VALUE) System.out.println('-');
            else if(dist[i]==maxCost+100) System.out.println('*');
            else System.out.println(dist[i]);
        }
    }

}
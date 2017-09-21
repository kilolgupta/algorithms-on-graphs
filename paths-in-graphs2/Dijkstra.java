import java.util.*;

public class Dijkstra {

    private static class Node implements Comparable<Node> {
        private int distance;
        private int index;

        public Node(int distance, int index) {
            this.distance = distance;
            this.index = index;
        }

        public int getDistance() {
            return distance;
        }

        public int getIndex() {
            return index;
        }

        @Override
        public int compareTo(Node other) {
            if(this.getDistance() > other.getDistance()) return 1;
            else if (this.getDistance() < other.getDistance()) return -1;
            else return 0;
        }

    }

    private static int distance(ArrayList<Integer>[] adj, ArrayList<Integer>[] cost, int s, int t, int n) {
        int[] dist = new int[n];
        int[] prev = new int[n];
        PriorityQueue<Node> q = new PriorityQueue<>();

        // finding sum of all the costs
        int maxCost = 0;
        for(int j=0;j<n;j++) {
            for(int i=0;i<cost[j].size();i++) maxCost+=cost[j].get(i);
        }

        // source node's distance from itself is 0
        dist[s] = 0;
        prev[s] = -1;
        Node sourceNode = new Node(dist[s], s);
        q.add(sourceNode);

        // initialising
        for(int i=0;i<n;i++) {
            if(i==s) continue;
            else {
                dist[i] = maxCost+100;
                prev[i] = -1;
                Node node = new Node(dist[i], i);
                q.add(node);
            }
        }

        while(!q.isEmpty()) {
            Node minNode = q.poll();
            int u = minNode.getIndex();
            int v;
            for(int i =0; i<adj[u].size();i++) {
                v = adj[u].get(i);

                //relax the edges
                if(dist[v] > dist[u] + cost[u].get(i)) {
                    dist[v] = dist[u] + cost[u].get(i);
                    prev[v] = u;

                    // changing priority
                    Node newNode = new Node(dist[v], v);
                    for(Node node:q){
                        if(node.getIndex()==v) {
                            q.remove(node);
                            q.add(newNode);
                            break;
                        }
                    }
                }
            }
        }
        if(dist[t]==maxCost+100)
            return -1;
        else
            return dist[t];
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
        int x = scanner.nextInt() - 1;
        int y = scanner.nextInt() - 1;
        System.out.println(distance(adj, cost, x, y, n));
    }
}
import java.util.ArrayList;
import java.util.Scanner;

public class ConnectedComponents {

    private static int result = 1;
    private static int numberOfComponents(ArrayList<Integer>[] adj) {
        if(adj.length==0) return 0;
        else {
            int[] visited = new int[adj.length];
            int[] label = new int[adj.length];
            for(int i=0; i<adj.length;i++) {
                if(visited[i]==0) {
                    explore(adj, visited, label, i);
                    result++;
                }
            }
            return result-1;
        }
    }

    private static void explore(ArrayList<Integer>[] adj, int[] visited, int[] label, int x) {
        visited[x] = 1;
        label[x] = result;

        for(int i=0;i<adj[x].size();i++) {
            if(visited[adj[x].get(i)]==0) {
                explore(adj, visited, label, adj[x].get(i));
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        ArrayList<Integer>[] adj = (ArrayList<Integer>[])new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < m; i++) {
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            adj[x - 1].add(y - 1);
            adj[y - 1].add(x - 1);
        }
        System.out.println(numberOfComponents(adj));
    }
}


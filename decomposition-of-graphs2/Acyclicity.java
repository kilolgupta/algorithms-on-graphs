import java.util.ArrayList;
import java.util.Scanner;

public class Acyclicity {
    private static int acyclic(ArrayList<Integer>[] adj) {
        int[] visited = new int[adj.length];
        int[] recStack = new int[adj.length];

        for(int i=0;i<adj.length;i++) {
            if(visited[i]==0) {
                if(dfs(adj, visited, recStack, i)==1) return 1;
            }
        }
        return 0;
    }

    private static int dfs(ArrayList<Integer>[] adj, int[] visited, int[] recStack, int x) {
        visited[x] = 1;
        recStack[x] = 1;

        for(int i=0;i<adj[x].size();i++) {
            if(visited[adj[x].get(i)]==0 && dfs(adj, visited, recStack, adj[x].get(i))==1) return 1;
            else if(recStack[adj[x].get(i)]==1) return 1;
        }
        recStack[x] = 0;
        return 0;
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
        }
        System.out.println(acyclic(adj));
    }
}
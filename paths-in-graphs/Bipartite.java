import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Bipartite {
    private static int bipartite(ArrayList<Integer>[] adj, int n) {
        int[] color = new int[n];
        for(int i=0;i<n;i++) color[i] = -1;
        int flag = -1;

        int s=0;
        Queue<Integer> q = new LinkedList<>();
        for(int i=0;i<adj.length;i++) {
            if(adj[i].size()!=0) {
                s = i;
                break;
            }
        }
        q.add(s);
        color[s] = 0;

        while(!q.isEmpty()) {
            int u = q.remove();
            int v;
            for(int i =0; i<adj[u].size();i++) {
                v = adj[u].get(i);
                if(color[v]==-1) {
                    q.add(v);
                    if(color[u]==1)
                        color[v] = 0;
                    else
                        color[v] = 1;
                }
                else {
                    if(color[u]+color[v]==1) flag = 1;
                    else {
                        flag = 0;
                        return flag;
                    }
                }
            }
        }
        return flag;
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
        System.out.println(bipartite(adj, n));
    }
}
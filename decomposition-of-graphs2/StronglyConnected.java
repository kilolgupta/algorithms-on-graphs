import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class StronglyConnected {

    private static int clock = 1;
    private static int numberOfSCC = 0;

    private static int findMaxIndex(int[] array) {
        int max=0;
        for(int i=1;i<array.length;i++) {
            if(array[i]>array[max]) max=i;
        }
        return max;
    }

    private static int numberOfStronglyConnectedComponents(ArrayList<Integer>[] adj) {
        ArrayList<Integer>[] reverseGraph = (ArrayList<Integer>[])new ArrayList[adj.length];
        for(int i =0;i<adj.length;i++) {
            reverseGraph[i] = new ArrayList<Integer>();
        }

        for(int j =0;j<adj.length;j++) {
            for(int k =0;k<adj[j].size();k++) {
                reverseGraph[adj[j].get(k)].add(j);
            }
        }

        //variable to store postOrder times of vertices
        int[] postOrder = new int[adj.length];
        int[] preOrder = new int[adj.length];
        int[] visited = new int[adj.length];
        int[] label = new int[adj.length];
        computePostOrder(reverseGraph, postOrder, preOrder);

        for(int i=0;i<postOrder.length;i++) {
            int max = findMaxIndex(postOrder);
            if (visited[max] == 0) {
                dfs(adj, visited, label, max);
                numberOfSCC += 1;
            }
            postOrder[max] = Integer.MIN_VALUE;
        }
        return numberOfSCC;
    }

    private static void dfs(ArrayList<Integer>[] adj, int[] visited, int[] label, int x) {
        visited[x] = 1;
        label[x] = numberOfSCC;

        for(int i=0;i<adj[x].size();i++) {
            if(visited[adj[x].get(i)]==0) {
                dfs(adj, visited, label, adj[x].get(i));
            }
        }
    }

    private static void computePostOrder(ArrayList<Integer>[] adj, int[] postOrder, int[] preOrder) {
        int[] visited = new int[adj.length];
        for(int i=0;i<adj.length;i++) {
            if(visited[i]==0) explore(adj, postOrder, preOrder, visited, i);
        }
    }

    private static void explore(ArrayList<Integer>[] adj, int[] postOrder, int[] preOrder, int[] visited, int x) {
        visited[x] = 1;
        preVisit(x, preOrder);
        for(int i=0;i<adj[x].size();i++) {
            if(visited[adj[x].get(i)]==0) explore(adj, postOrder, preOrder, visited, adj[x].get(i));
        }
        postVisit(x, postOrder);
    }

    private static void preVisit(int x, int[] preOrder) {
        preOrder[x] = clock;
        clock+=1;
    }

    private static void postVisit(int x, int[] postOrder) {
        postOrder[x] = clock;
        clock+=1;
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
        System.out.println(numberOfStronglyConnectedComponents(adj));
    }
}
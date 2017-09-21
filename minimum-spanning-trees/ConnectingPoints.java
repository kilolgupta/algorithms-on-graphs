import java.util.PriorityQueue;
import java.util.Scanner;
import java.lang.Math;

public class ConnectingPoints {

    // Consists of distance, x and y coordinate of a vertex
    private static class Node implements Comparable<Node> {
        private double distance;
        private int x;
        private int y;

        public Node(double distance, int x, int y) {
            this.distance = distance;
            this.x = x;
            this.y = y;
        }

        public double getDistance() {
            return distance;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public int compareTo(Node other) {
            if(this.getDistance() > other.getDistance()) return 1;
            else if (this.getDistance() < other.getDistance()) return -1;
            else return 0;
        }

    }

    // To calculate the distance between to vertices
    private static double distance(int[] x, int[] y) {
        return Math.sqrt(Math.pow(x[1]-x[0], 2) + Math.pow(y[1]-y[0], 2));
    }


    // similar to djikstra's Prim's algorithm
    private static double minimumDistance(int[] x, int[] y, int n) {
        double[] distance = new double[n];
        PriorityQueue<Node> q = new PriorityQueue<>();
        double maxCost = Integer.MAX_VALUE;

        Node sourceNode = new Node(0, x[0], y[0]);
        distance[0] = 0;
        q.add(sourceNode);

        for(int i=1;i<x.length;i++) {
            Node newNode = new Node(maxCost, x[i], y[i]);
            distance[i] = maxCost;
            q.add(newNode);
        }

        while(!q.isEmpty()) {
            Node minNode = q.poll();
            for(int i=0;i<x.length;i++) {
                if(x[i]!=minNode.x || y[i]!=minNode.y) {
                    int[] x1 = {minNode.x, x[i]};
                    int[] y1 = {minNode.y, y[i]};
                    double cost = distance(x1, y1);

                    Node newNode = new Node(distance[i], x[i], y[i]);
                    for(Node node:q){
                        if(node.x==newNode.x && node.y==newNode.y && distance[i] > cost) {
                            distance[i] = cost;

                            // changing priority
                            newNode = new Node(distance[i], x[i], y[i]);
                            q.remove(node);
                            q.add(newNode);
                            break;
                        }
                    }
                }
            }
        }

        double sum = 0;
        for(double dist: distance) sum+=dist;
        return sum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
        }
        System.out.println(minimumDistance(x, y, n));
    }
}
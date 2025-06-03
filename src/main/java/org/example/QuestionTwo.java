package org.example;

import java.util.*;

public class QuestionTwo {

    public static void main(String[] args) {
        int n = 7; // מספר צמתים
        List<List<Integer>> graph = new ArrayList<>();

        // אתחול גרף
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // בניית עץ לא מכוון
        addEdge(graph, 0, 1);
        addEdge(graph, 1, 2);
        addEdge(graph, 1, 3);
        addEdge(graph, 3, 4);
        addEdge(graph, 3, 5);
        addEdge(graph, 5, 6);

        // שלב 1: מצא צומת רחוק מ-0
        System.out.println("🔍 שלב 1: מציאת הצומת הרחוק ביותר מ-0");
        int[] bfs1 = bfs(graph, 0);
        int far1 = getFarthestNode(bfs1);
        System.out.println("👀 הצומת הרחוק ביותר מ-0: " + far1);
        System.out.println("מרחקים מהצומת 0: " + Arrays.toString(bfs1));

        // שלב 2: מצא את הצומת הרחוק ביותר ממנו
        System.out.println("\n🔍 שלב 2: מציאת הצומת הרחוק ביותר מהצומת " + far1);
        int[] bfs2 = bfs(graph, far1);
        int far2 = getFarthestNode(bfs2);
        int diameter = bfs2[far2];
        System.out.println("👀 הצומת הרחוק ביותר מ-" + far1 + ": " + far2);
        System.out.println("מרחקים מהצומת " + far1 + ": " + Arrays.toString(bfs2));

        // הדפסת הקוטר
        System.out.println("\n🎯 קצה שני של הקוטר: " + far2);
        System.out.println("📏 קוטר העץ הוא: " + diameter);
    }

    static void addEdge(List<List<Integer>> graph, int u, int v) {
        graph.get(u).add(v);
        graph.get(v).add(u);
        System.out.println("🔗 נוספה קשת בין הצמתים " + u + " ל-" + v);
    }

    static int[] bfs(List<List<Integer>> graph, int start) {
        int n = graph.size();
        int[] dist = new int[n];
        Arrays.fill(dist, -1);
        Queue<Integer> q = new LinkedList<>();

        q.add(start);
        dist[start] = 0;
        System.out.println("\n🌱 התחלנו BFS מצומת " + start);

        while (!q.isEmpty()) {
            int curr = q.poll();
            System.out.println("👣 כרגע בביקור בצומת " + curr + " עם מרחק " + dist[curr]);
            for (int neighbor : graph.get(curr)) {
                if (dist[neighbor] == -1) {
                    dist[neighbor] = dist[curr] + 1;
                    q.add(neighbor);
                    System.out.println("➡️ הוספנו את הצומת " + neighbor + " עם מרחק " + dist[neighbor]);
                }
            }
        }

        return dist;
    }

    static int getFarthestNode(int[] dist) {
        int maxDist = -1;
        int node = -1;
        for (int i = 0; i < dist.length; i++) {
            if (dist[i] > maxDist) {
                maxDist = dist[i];
                node = i;
            }
        }
        return node;
    }
}

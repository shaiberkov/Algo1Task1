package org.example;


import java.util.*;

public class QuestionThree {

    // פונקציה ראשית: בודקת האם הגרף קשיר היטב
    public static boolean isStronglyConnected(List<List<Integer>> graph) {
        int n = graph.size();

        // בדיקה ראשונה: מהצומת 0 בגרף המקורי
        boolean[] visited = new boolean[n];
        dfs(0, graph, visited);
        if (!allVisited(visited)) {
            return false;
        }

        // יצירת הגרף ההפוך
        List<List<Integer>> reversedGraph = reverseGraph(graph);

        // בדיקה שנייה: מהצומת 0 בגרף ההפוך
        Arrays.fill(visited, false);
        dfs(0, reversedGraph, visited);
        if (!allVisited(visited)) {
            return false;
        }

        // אם הצלחנו בשתי הבדיקות - קשיר היטב
        return true;
    }

    // DFS רגיל
    private static void dfs(int node, List<List<Integer>> graph, boolean[] visited) {
        visited[node] = true;
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor, graph, visited);
            }
        }
    }

    // פונקציה שבודקת אם כולם ביקרו בהם
    private static boolean allVisited(boolean[] visited) {
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        return true;
    }

    // הפיכת כיווני הגרף
    private static List<List<Integer>> reverseGraph(List<List<Integer>> graph) {
        int n = graph.size();
        List<List<Integer>> reversed = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            reversed.add(new ArrayList<>());
        }

        for (int u = 0; u < n; u++) {
            for (int v : graph.get(u)) {
                reversed.get(v).add(u); // הופכים את הכיוון
            }
        }

        return reversed;
    }

    // דוגמה לשימוש
    public static void main(String[] args) {
        int n = 4;
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // יצירת גרף מכוון:
        graph.get(0).add(1);
        graph.get(1).add(2);
        graph.get(2).add(3);
        graph.get(3).add(0);

        if (isStronglyConnected(graph)) {
            System.out.println("הגרף קשיר היטב!");
        } else {
            System.out.println("הגרף לא קשיר היטב.");
        }
    }
}

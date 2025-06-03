package org.example;

import java.util.*;

public class QuestionOne {

    static final int WALL = 1;
    static final int EMPTY = 0;
    static final int ROBOT = 9;

    static class Point {
        int r, c;
        Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
        public String toString() {
            return "(" + r + "," + c + ")";
        }
    }

    public static void main(String[] args) {
        Random rand = new Random();

        int rows = rand.nextInt(3) + 6; // גובה רנדומלי 6–8
        int cols = rand.nextInt(3) + 5; // רוחב רנדומלי 5–7
        System.out.println("📐 נבנה לוח בגודל: " + rows + "x" + cols);

        int[][] board = new int[rows][cols];

        // מחסומים אקראיים
        for (int i = 0; i < rows * cols / 5; i++) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            board[r][c] = WALL;
            System.out.println("🧱 הוסף מכשול בתא: (" + r + "," + c + ")");
        }

        // רובוטים אקראיים
        int k = rand.nextInt(3) + 2; // מספר רובוטים 2–4
        List<Point> robots = new ArrayList<>();
        while (robots.size() < k) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            if (board[r][c] == EMPTY) {
                board[r][c] = ROBOT;
                robots.add(new Point(r, c));
                System.out.println("🤖 הוסף רובוט בתא: (" + r + "," + c + ")");
            }
        }

        // הדפסת הלוח
        System.out.println("\n🧩 הלוח ההתחלתי:");
        printBoard(board);

        int[][] totalDistances = new int[rows][cols];
        boolean[][] accessible = new boolean[rows][cols];

        for (Point robot : robots) {
            System.out.println("\n🚀 מתחילים BFS מהרובוט ב־" + robot);
            int[][] dist = bfs(board, robot.r, robot.c);

            // הדפסת טבלת מרחקים
            System.out.println("📏 טבלת מרחקים מהרובוט ב־" + robot + ":");
            printMatrix(dist);

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (dist[r][c] != -1) {
                        if (totalDistances[r][c] != -1) {
                            totalDistances[r][c] += dist[r][c];
                            accessible[r][c] = true;
                        }
                    } else {
                        accessible[r][c] = false;
                        totalDistances[r][c] = -1;
                    }
                }
            }
        }

        // חיפוש התא הטוב ביותר
        int minSum = Integer.MAX_VALUE;
        Point best = null;

        System.out.println("\n📊 סכום מרחקים מכל הרובוטים לכל משבצת:");
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c] == EMPTY && accessible[r][c] && totalDistances[r][c] > 0) {
                    System.out.print(String.format("%3d", totalDistances[r][c]));
                    if (totalDistances[r][c] < minSum) {
                        minSum = totalDistances[r][c];
                        best = new Point(r, c);
                    }
                } else {
                    System.out.print("  X");
                }
            }
            System.out.println();
        }

        if (best != null) {
            System.out.println("\n✅ התא הכי טוב הוא " + best + " עם סכום צעדים: " + minSum);
        } else {
            System.out.println("\n❌ לא נמצא תא שכולם יכולים להגיע אליו.");
        }
    }

    public static int[][] bfs(int[][] board, int sr, int sc) {
        int[][] dist = new int[board.length][board[0].length];
        for (int[] row : dist) Arrays.fill(row, -1);

        Queue<Point> q = new LinkedList<>();
        q.add(new Point(sr, sc));
        dist[sr][sc] = 0;

        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        while (!q.isEmpty()) {
            Point p = q.poll();
            System.out.println("⬅️ מעבדים את " + p);
            for (int d = 0; d < 4; d++) {
                int nr = p.r + dr[d];
                int nc = p.c + dc[d];
                if (isInBounds(nr, nc, board) && board[nr][nc] != WALL && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[p.r][p.c] + 1;
                    q.add(new Point(nr, nc));
                    System.out.println("  ➕ מוסיפים לשכנים: (" + nr + "," + nc + ") עם מרחק " + dist[nr][nc]);
                }
            }
        }

        return dist;
    }

    public static boolean isInBounds(int r, int c, int[][] board) {
        return r >= 0 && r < board.length && c >= 0 && c < board[0].length;
    }

    public static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == WALL) System.out.print("⬛ ");
                else if (cell == ROBOT) System.out.print("🤖 ");
                else System.out.print("⬜ ");
            }
            System.out.println();
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val == -1 ? "  X" : String.format("%3d", val));
            }
            System.out.println();
        }
    }
}

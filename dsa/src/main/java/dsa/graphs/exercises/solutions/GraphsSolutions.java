package dsa.graphs.exercises.solutions;

import java.util.*;

/**
 * Reference solutions for GraphsExercises.
 */
public class GraphsSolutions {

    // Exercise 1 — Kahn's topo sort. If we expand all numCourses nodes, no cycle.
    static boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) adj.add(new ArrayList<>());
        int[] inDeg = new int[numCourses];
        for (int[] p : prerequisites) {
            adj.get(p[1]).add(p[0]);
            inDeg[p[0]]++;
        }
        Deque<Integer> q = new ArrayDeque<>();
        for (int v = 0; v < numCourses; v++) if (inDeg[v] == 0) q.offer(v);

        int processed = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            processed++;
            for (int v : adj.get(u)) if (--inDeg[v] == 0) q.offer(v);
        }
        return processed == numCourses;
    }

    // Exercise 2 — Union-Find. The edge whose endpoints already share a root closes a cycle.
    static int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        int[] parent = new int[n + 1];
        for (int i = 0; i <= n; i++) parent[i] = i;
        for (int[] e : edges) {
            int ra = find(parent, e[0]);
            int rb = find(parent, e[1]);
            if (ra == rb) return e;
            parent[ra] = rb;
        }
        throw new IllegalArgumentException("no redundant edge");
    }

    private static int find(int[] parent, int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    // Exercise 3 — BFS on word graph using wildcard buckets to avoid O(N^2) edge build.
    // Each word of length L has L wildcard patterns ("h*t", "*ot", etc.). Any two words
    // sharing a pattern differ by one letter. We BFS levels until we reach endWord.
    static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) return 0;

        Map<String, List<String>> patterns = new HashMap<>();
        for (String w : dict) {
            for (int i = 0; i < w.length(); i++) {
                String pat = w.substring(0, i) + "*" + w.substring(i + 1);
                patterns.computeIfAbsent(pat, k -> new ArrayList<>()).add(w);
            }
        }

        Deque<String> q = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        q.offer(beginWord);
        visited.add(beginWord);
        int steps = 1;

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                String w = q.poll();
                if (w.equals(endWord)) return steps;
                for (int j = 0; j < w.length(); j++) {
                    String pat = w.substring(0, j) + "*" + w.substring(j + 1);
                    for (String next : patterns.getOrDefault(pat, List.of())) {
                        if (visited.add(next)) q.offer(next);
                    }
                }
            }
            steps++;
        }
        return 0;
    }

    // Exercise 4 — Union-Find, count remaining roots.
    static int countComponents(int n, int[][] edges) {
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
        int components = n;
        for (int[] e : edges) {
            int ra = find(parent, e[0]);
            int rb = find(parent, e[1]);
            if (ra != rb) {
                parent[ra] = rb;
                components--;
            }
        }
        return components;
    }

    public static void main(String[] args) {
        System.out.println("canFinish = " + canFinish(2, new int[][]{{1, 0}}));
        // true

        System.out.println("redundant = "
            + Arrays.toString(findRedundantConnection(new int[][]{{1, 2}, {1, 3}, {2, 3}})));
        // [2, 3]

        System.out.println("ladderLength = "
            + ladderLength("hit", "cog", List.of("hot", "dot", "dog", "lot", "log", "cog")));
        // 5  (hit -> hot -> dot -> dog -> cog)

        System.out.println("countComponents = "
            + countComponents(5, new int[][]{{0, 1}, {1, 2}, {3, 4}}));
        // 2
    }
}

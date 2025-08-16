import java.util.*;

public class AVLLeaderboardSystem {
    private class AVLNode {
        String playerName;
        int score, height, size;
        AVLNode left, right;

        AVLNode(String name, int score) {
            this.playerName = name;
            this.score = score;
            this.height = 1;
            this.size = 1;
        }
    }

    private AVLNode root;
    private Map<String, Integer> playerScores = new HashMap<>();

    // === 公開介面 ===

    public void addOrUpdatePlayer(String playerName, int score) {
        if (playerScores.containsKey(playerName)) {
            root = remove(root, playerName, playerScores.get(playerName));
        }
        playerScores.put(playerName, score);
        root = insert(root, playerName, score);
    }

    public int getPlayerRank(String playerName) {
        if (!playerScores.containsKey(playerName)) return -1;
        return getRank(root, playerName, playerScores.get(playerName)) + 1; // 排名從 1 開始
    }

    public List<String> getTopKPlayers(int k) {
        List<String> result = new ArrayList<>();
        getTopK(root, k, result);
        return result;
    }

    // === AVL Tree 基本操作 ===

    private AVLNode insert(AVLNode node, String name, int score) {
        if (node == null) return new AVLNode(name, score);

        int cmp = compare(name, score, node);
        if (cmp < 0) node.left = insert(node.left, name, score);
        else node.right = insert(node.right, name, score);

        update(node);
        return balance(node);
    }

    private AVLNode remove(AVLNode node, String name, int score) {
        if (node == null) return null;

        int cmp = compare(name, score, node);
        if (cmp < 0) node.left = remove(node.left, name, score);
        else if (cmp > 0) node.right = remove(node.right, name, score);
        else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                AVLNode min = getMin(node.right);
                node.playerName = min.playerName;
                node.score = min.score;
                node.right = remove(node.right, min.playerName, min.score);
            }
        }
        if (node != null) {
            update(node);
            node = balance(node);
        }
        return node;
    }

    private int getRank(AVLNode node, String name, int score) {
        if (node == null) return 0;
        int cmp = compare(name, score, node);
        if (cmp < 0) {
            return getRank(node.left, name, score);
        } else if (cmp > 0) {
            int leftSize = (node.left != null) ? node.left.size : 0;
            return leftSize + 1 + getRank(node.right, name, score);
        } else {
            int leftSize = (node.left != null) ? node.left.size : 0;
            return leftSize;
        }
    }

    private void getTopK(AVLNode node, int k, List<String> result) {
        if (node == null || result.size() >= k) return;
        getTopK(node.right, k, result);
        if (result.size() < k) {
            result.add(node.playerName);
            getTopK(node.left, k, result);
        }
    }

    // === AVL Helper ===

    private void update(AVLNode node) {
        int lh = height(node.left);
        int rh = height(node.right);
        node.height = 1 + Math.max(lh, rh);
        node.size = 1 + size(node.left) + size(node.right);
    }

    private int height(AVLNode node) {
        return node == null ? 0 : node.height;
    }

    private int size(AVLNode node) {
        return node == null ? 0 : node.size;
    }

    private int balanceFactor(AVLNode node) {
        return height(node.left) - height(node.right);
    }

    private AVLNode balance(AVLNode node) {
        int bf = balanceFactor(node);
        if (bf > 1) {
            if (balanceFactor(node.left) < 0) node.left = rotateLeft(node.left);
            node = rotateRight(node);
        } else if (bf < -1) {
            if (balanceFactor(node.right) > 0) node.right = rotateRight(node.right);
            node = rotateLeft(node);
        }
        return node;
    }

    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;
        x.right = y;
        y.left = T2;
        update(y);
        update(x);
        return x;
    }

    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;
        y.left = x;
        x.right = T2;
        update(x);
        update(y);
        return y;
    }

    private AVLNode getMin(AVLNode node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // 分數高的比較小（因為是從高到低排序），名字為 tie-breaker
    private int compare(String name, int score, AVLNode node) {
        if (score != node.score) {
            return Integer.compare(node.score, score); // 高分在左（score DESC）
        }
        return name.compareTo(node.playerName); // 名字 ASC
    }

    // === 測試用 ===
    public static void main(String[] args) {
        AVLLeaderboardSystem leaderboard = new AVLLeaderboardSystem();

        leaderboard.addOrUpdatePlayer("Alice", 100);
        leaderboard.addOrUpdatePlayer("Bob", 200);
        leaderboard.addOrUpdatePlayer("Charlie", 150);

        System.out.println("Rank of Alice: " + leaderboard.getPlayerRank("Alice")); // 3
        System.out.println("Rank of Bob: " + leaderboard.getPlayerRank("Bob")); // 1
        System.out.println("Top 2 Players: " + leaderboard.getTopKPlayers(2)); // [Bob, Charlie]

        leaderboard.addOrUpdatePlayer("Alice", 250);
        System.out.println("Rank of Alice after update: " + leaderboard.getPlayerRank("Alice")); // 1
        System.out.println("Top 3 Players: " + leaderboard.getTopKPlayers(3)); // [Alice, Bob, Charlie]
    }
}

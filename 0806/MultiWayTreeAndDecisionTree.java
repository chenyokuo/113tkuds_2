import java.util.*;

public class MultiWayTreeAndDecisionTree {

    // 1️⃣ 多路樹節點
    static class MultiTreeNode {
        String value;
        List<MultiTreeNode> children;

        MultiTreeNode(String value) {
            this.value = value;
            this.children = new ArrayList<>();
        }

        void addChild(MultiTreeNode child) {
            children.add(child);
        }
    }

    // 2️⃣ 深度優先走訪
    public static void dfs(MultiTreeNode node) {
        if (node == null) return;
        System.out.println(node.value);
        for (MultiTreeNode child : node.children) {
            dfs(child);
        }
    }

    // 2️⃣ 廣度優先走訪
    public static void bfs(MultiTreeNode root) {
        if (root == null) return;
        Queue<MultiTreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            MultiTreeNode current = queue.poll();
            System.out.println(current.value);
            for (MultiTreeNode child : current.children) {
                queue.offer(child);
            }
        }
    }

    // 3️⃣ 模擬簡單決策樹（猜數字：大、小、正確）
    static class DecisionTreeNode {
        String question;
        DecisionTreeNode yes, no;

        DecisionTreeNode(String question) {
            this.question = question;
        }
    }

    public static void runDecisionTree(DecisionTreeNode root, Scanner scanner) {
        DecisionTreeNode current = root;
        while (current.yes != null || current.no != null) {
            System.out.println(current.question + " (yes/no): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals("yes")) current = current.yes;
            else current = current.no;
        }
        System.out.println("💡 答案是：" + current.question);
    }

    // 4️⃣ 計算高度
    public static int getHeight(MultiTreeNode node) {
        if (node == null) return 0;
        int maxHeight = 0;
        for (MultiTreeNode child : node.children) {
            maxHeight = Math.max(maxHeight, getHeight(child));
        }
        return maxHeight + 1;
    }

    // 4️⃣ 輸出每個節點的度數
    public static void printDegrees(MultiTreeNode node) {
        if (node == null) return;
        System.out.println("節點 \"" + node.value + "\" 的度數：" + node.children.size());
        for (MultiTreeNode child : node.children) {
            printDegrees(child);
        }
    }

    public static void main(String[] args) {
        // 測試多路樹
        MultiTreeNode root = new MultiTreeNode("A");
        MultiTreeNode b = new MultiTreeNode("B");
        MultiTreeNode c = new MultiTreeNode("C");
        MultiTreeNode d = new MultiTreeNode("D");
        MultiTreeNode e = new MultiTreeNode("E");
        MultiTreeNode f = new MultiTreeNode("F");

        root.addChild(b);
        root.addChild(c);
        b.addChild(d);
        b.addChild(e);
        c.addChild(f);

        System.out.println("=== DFS 走訪 ===");
        dfs(root);

        System.out.println("\n=== BFS 走訪 ===");
        bfs(root);

        System.out.println("\n=== 計算樹的高度 ===");
        System.out.println("高度：" + getHeight(root));

        System.out.println("\n=== 節點度數 ===");
        printDegrees(root);

        // 測試簡單決策樹
        System.out.println("\n=== 決策樹：猜數字遊戲 ===");
        DecisionTreeNode q1 = new DecisionTreeNode("你想的數字大於 50 嗎");
        q1.yes = new DecisionTreeNode("你想的數字大於 75 嗎");
        q1.no = new DecisionTreeNode("你想的數字小於 25 嗎");

        q1.yes.yes = new DecisionTreeNode("87");
        q1.yes.no = new DecisionTreeNode("63");
        q1.no.yes = new DecisionTreeNode("12");
        q1.no.no = new DecisionTreeNode("37");

        Scanner scanner = new Scanner(System.in);
        runDecisionTree(q1, scanner);
        scanner.close();
    }
}

import java.util.*;

public class RecursiveTreePreview {

    // 模擬資料夾／檔案結構
    static class FileNode {
        String name;
        boolean isFile;
        List<FileNode> children;

        FileNode(String name, boolean isFile) {
            this.name = name;
            this.isFile = isFile;
            this.children = new ArrayList<>();
        }

        void add(FileNode node) {
            children.add(node);
        }
    }

    // 1️⃣ 遞迴計算資料夾內總檔案數
    public static int countFiles(FileNode node) {
        if (node.isFile) return 1;
        int count = 0;
        for (FileNode child : node.children) {
            count += countFiles(child);
        }
        return count;
    }

    // 2️⃣ 遞迴列印多層選單（縮排表示）
    static class MenuItem {
        String name;
        List<MenuItem> subItems = new ArrayList<>();

        MenuItem(String name) {
            this.name = name;
        }

        void add(MenuItem item) {
            subItems.add(item);
        }
    }

    public static void printMenu(MenuItem item, int indent) {
        System.out.println("  ".repeat(indent) + "- " + item.name);
        for (MenuItem sub : item.subItems) {
            printMenu(sub, indent + 1);
        }
    }

    // 3️⃣ 遞迴展平巢狀陣列
    public static List<Integer> flatten(Object[] nested) {
        List<Integer> result = new ArrayList<>();
        flattenHelper(nested, result);
        return result;
    }

    private static void flattenHelper(Object[] arr, List<Integer> out) {
        for (Object obj : arr) {
            if (obj instanceof Integer) {
                out.add((Integer) obj);
            } else if (obj instanceof Object[]) {
                flattenHelper((Object[]) obj, out);
            }
        }
    }

    // 4️⃣ 遞迴計算巢狀清單最大深度
    public static int maxDepth(Object[] nested) {
        int max = 1;
        for (Object obj : nested) {
            if (obj instanceof Object[]) {
                max = Math.max(max, 1 + maxDepth((Object[]) obj));
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println("=== 1️⃣ 模擬檔案系統計數 ===");
        FileNode root = new FileNode("root", false);
        FileNode folderA = new FileNode("A", false);
        FileNode folderB = new FileNode("B", false);
        FileNode file1 = new FileNode("file1.txt", true);
        FileNode file2 = new FileNode("file2.txt", true);
        FileNode file3 = new FileNode("file3.txt", true);

        folderA.add(file1);
        folderA.add(file2);
        folderB.add(file3);
        root.add(folderA);
        root.add(folderB);

        System.out.println("檔案總數：" + countFiles(root));

        System.out.println("\n=== 2️⃣ 多層選單列印 ===");
        MenuItem main = new MenuItem("主選單");
        MenuItem sub1 = new MenuItem("產品");
        MenuItem sub2 = new MenuItem("關於我們");
        sub1.add(new MenuItem("電腦"));
        sub1.add(new MenuItem("手機"));
        main.add(sub1);
        main.add(sub2);
        printMenu(main, 0);

        System.out.println("\n=== 3️⃣ 巢狀陣列展平 ===");
        Object[] nestedArray = {1, new Object[]{2, 3}, new Object[]{4, new Object[]{5, 6}}, 7};
        List<Integer> flat = flatten(nestedArray);
        System.out.println("展平後：" + flat);

        System.out.println("\n=== 4️⃣ 巢狀清單最大深度 ===");
        System.out.println("最大深度：" + maxDepth(nestedArray));
    }
}

import java.util.*;

public class MultiLevelCacheSystem {
    enum Level {
        L1(2,1), L2(5,3), L3(10,10);
        int capacity;
        int cost;
        Level(int capacity, int cost) { this.capacity = capacity; this.cost = cost; }
    }

    class CacheEntry {
        int key;
        String value;
        int frequency;
        long timestamp;
        Level level;

        CacheEntry(int k, String v, Level lvl, long time) {
            key = k; value = v; frequency = 1;
            timestamp = time; level = lvl;
        }

        double score() {
            // 評分 = 頻率 / 成本, 時間戳在比較時才用作 tiebreak
            return (double)frequency / level.cost;
        }
    }

    // 三層快取資料結構
    Map<Integer, CacheEntry> map = new HashMap<>();

    // 每層的Heap，依 score 排序，頻率高、成本低優先
    // 若 score 一樣，使用 timestamp 遞增(LRU)
    Map<Level, PriorityQueue<CacheEntry>> heaps = new HashMap<>();

    public MultiLevelCacheSystem() {
        for(Level lvl : Level.values()) {
            heaps.put(lvl, new PriorityQueue<>(
                (a,b) -> {
                    int cmp = Double.compare(a.score(), b.score());
                    if (cmp != 0) return cmp; // score小的優先被移除
                    return Long.compare(a.timestamp, b.timestamp); // 老的優先被移除
                }
            ));
        }
    }

    public String get(int key) {
        if (!map.containsKey(key)) return null;

        CacheEntry entry = map.get(key);
        // 更新頻率和時間戳
        entry.frequency++;
        entry.timestamp = System.nanoTime();

        // 從該層heap移除再重新加入以更新排序
        PriorityQueue<CacheEntry> heap = heaps.get(entry.level);
        heap.remove(entry);
        heap.offer(entry);

        // 嘗試往上層遷移
        tryPromote(entry);

        return entry.value;
    }

    public void put(int key, String value) {
        long now = System.nanoTime();
        if (map.containsKey(key)) {
            CacheEntry entry = map.get(key);
            entry.value = value;
            entry.frequency++;
            entry.timestamp = now;

            // 更新heap位置
            PriorityQueue<CacheEntry> heap = heaps.get(entry.level);
            heap.remove(entry);
            heap.offer(entry);

            tryPromote(entry);
        } else {
            // 新資料放 L3
            CacheEntry newEntry = new CacheEntry(key, value, Level.L3, now);
            map.put(key, newEntry);
            PriorityQueue<CacheEntry> heap = heaps.get(Level.L3);
            heap.offer(newEntry);

            // 若L3溢位，淘汰或往下層移動(無下層，淘汰)
            evictIfNeeded(Level.L3);

            // 再嘗試調整整體資料
            adjustCache();
        }
    }

    // 嘗試提升 CacheEntry 層級
    private void tryPromote(CacheEntry entry) {
        if (entry.level == Level.L1) return; // 已最高層
        Level nextLevel = null;
        if (entry.level == Level.L3) nextLevel = Level.L2;
        else if (entry.level == Level.L2) nextLevel = Level.L1;

        double currentScore = entry.score();
        double nextLevelThreshold = 1.0 / nextLevel.cost; // 簡單假設門檻

        if (currentScore > nextLevelThreshold) {
            // 從舊層heap移除
            heaps.get(entry.level).remove(entry);
            // 更新層級
            entry.level = nextLevel;
            heaps.get(nextLevel).offer(entry);

            // 處理下一層容量
            evictIfNeeded(nextLevel);
        }
    }

    // 如果該層容量超出，移除最低優先級元素或降級
    private void evictIfNeeded(Level level) {
        PriorityQueue<CacheEntry> heap = heaps.get(level);
        while (heap.size() > level.capacity) {
            CacheEntry lowest = heap.poll();
            // 如果是L3，直接淘汰
            if (level == Level.L3) {
                map.remove(lowest.key);
            } else {
                // 從此層移除，降級到下一層
                lowest.level = nextLowerLevel(level);
                heaps.get(lowest.level).offer(lowest);
                evictIfNeeded(lowest.level);
            }
        }
    }

    private Level nextLowerLevel(Level level) {
        if (level == Level.L1) return Level.L2;
        if (level == Level.L2) return Level.L3;
        return null; // L3沒有更低層
    }

    // 調整整個快取結構，確保每層容量和頻率一致
    private void adjustCache() {
        // 這裡可以根據需求加入更複雜的調整策略，例如將低頻資料往下層移動等
        // 本範例簡化略過
    }

    // 測試用
    public void printCacheStatus() {
        System.out.println("Cache Status:");
        for (Level lvl : Level.values()) {
            System.out.print(lvl.name() + ": ");
            PriorityQueue<CacheEntry> heap = heaps.get(lvl);
            List<CacheEntry> list = new ArrayList<>(heap);
            // 按評分降序顯示
            list.sort((a,b) -> Double.compare(b.score(), a.score()));
            for (CacheEntry e : list) {
                System.out.print(String.format("[key=%d val=%s freq=%d lvl=%s] ", e.key, e.value, e.frequency, e.level.name()));
            }
            System.out.println();
        }
    }

    // =================== 測試案例 ===================
    public static void main(String[] args) {
        MultiLevelCacheSystem cache = new MultiLevelCacheSystem();

        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        cache.printCacheStatus();
        System.out.println();

        cache.get(1);
        cache.get(1);
        cache.get(2);
        cache.printCacheStatus();
        System.out.println();

        cache.put(4, "D");
        cache.put(5, "E");
        cache.put(6, "F");
        cache.printCacheStatus();
    }
}

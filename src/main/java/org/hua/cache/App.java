package org.hua.cache;

import java.util.Random;

public class App {
    public static void main(String[] args) {

        int capacity = 10000;
        MyCache<Integer, Integer> LRUCache = new MyCache<>(capacity, CacheReplacementPolicy.LRU);
        printStats(LRUCache, capacity);

        MyCache<Integer, Integer> MRUCache = new MyCache<>(capacity, CacheReplacementPolicy.MRU);
        printStats(MRUCache, capacity);

        MyCache<Integer, Integer> LFUCache = new MyCache<>(capacity, CacheReplacementPolicy.LFU);
        printStats(LFUCache, capacity);

    }

    private static void printStats(MyCache<Integer, Integer> cache, int capacity) {
        int count = 100000;
        int key;
        Random random = new Random();

        for (int i = 0; i < capacity; i++) {
            key = random.nextInt(capacity * 2);
            cache.put(i, key);
        }

        for (int i = 0; i < count; i++) {
            if(random.nextDouble() < 0.5) {
                if (random.nextDouble() < 0.8) {
                    key = random.nextInt(2000);
                    cache.get(key);

                } else {
                    key = 2000 + random.nextInt(6000);
                    cache.get(key);

                }

            } else {
                key = random.nextInt(capacity * 4);
                cache.get(key);
            }
        }

        System.out.println();
        System.out.println("Replacement policy: " + cache.getReplacementPolicy());
        System.out.println("Total operations: " + count);
        System.out.println("Cache Hits: " + cache.getHitCount());
        System.out.println("Cache Misses: " + cache.getMissCount());
        System.out.printf("Hit Rate: %.2f%%\n", (cache.getHitCount() / (double) count) * 100);
        System.out.printf("Miss Rate: %.2f%%\n", (cache.getMissCount() / (double) count) * 100);
        System.out.println();
    }
}
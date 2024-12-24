package org.hua.cache;

import java.util.Random;

public class App {
    public static void main(String[] args) {

        int capacity = 10000;
//        int count = 100000;
        LRUCache<Integer, Integer> LRUCache = new LRUCache<>(capacity, CacheReplacementPolicy.MRU);
        printStats(LRUCache, capacity);
//        Random random = new Random();




//        int key;
//        for (int i = 0; i < capacity; i++) {
//            key = random.nextInt(capacity * 2);
//            cache.put(i, key);
//        }
//
//        for (int i = 0; i < count; i++) {
//            if(random.nextDouble() < 0.5) {
//                if (random.nextDouble() < 0.8) {
//                    key = random.nextInt(2000);
//                    cache.get(key);
//
//                } else {
//                    key = 2000 + random.nextInt(6000);
//                    cache.get(key);
//
//                }
//
//            } else {
//                key = random.nextInt(capacity * 2);
//                cache.get(key);
//            }
//        }
//
//        System.out.println();
//        System.out.println("Total operations: " + count);
//        System.out.println("Cache Hits: " + cache.getHitCount());
//        System.out.println("Cache Misses: " + cache.getMissCount());
//        System.out.printf("Hit Rate: %.2f%%\n", (cache.getHitCount() / (double) count) * 100);
//        System.out.printf("Miss Rate: %.2f%%\n", (cache.getMissCount() / (double) count) * 100);
//        System.out.println();
    }

    private static void printStats(LRUCache<Integer, Integer> cache, int capacity) {
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
                key = random.nextInt(capacity * 2);
                cache.get(key);
            }
        }

        System.out.println();
        System.out.println("Total operations: " + count);
        System.out.println("Cache Hits: " + cache.getHitCount());
        System.out.println("Cache Misses: " + cache.getMissCount());
        System.out.printf("Hit Rate: %.2f%%\n", (cache.getHitCount() / (double) count) * 100);
        System.out.printf("Miss Rate: %.2f%%\n", (cache.getMissCount() / (double) count) * 100);
        System.out.println();
    }
}
package org.hua.cache;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LRUCacheTest {

//    @Test
//    public void testCache() {
//        testPutAndGet();
//        testRemove();;
//        testUpdateOrder();
//        testUpdateValue();
//        testRemoveCapacityOne();
//        testEmptyCache();
//        testCapacityZero();
//
//        stressTest();
//        secondStressTest();
//    }

    @Test
    public void LRUTestPutAndGet() {
        LRUCache<Integer, String> cache = new LRUCache<>(3, CacheReplacementPolicy.LRU);

        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        assertEquals("one", cache.get(1));
        assertEquals("two", cache.get(2));
        assertEquals("three", cache.get(3));
    }

    @Test
    public void LRUTestRemove() {
        LRUCache<Integer, String> cache = new LRUCache<>(3, CacheReplacementPolicy.LRU);

        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");
        cache.put(4, "Four");

        assertNull(cache.get(1));
        assertEquals("Two", cache.get(2));
        assertEquals("Three", cache.get(3));
        assertEquals("Four", cache.get(4));
    }

    @Test
    public void LRUTestUpdateOrder() {
        LRUCache<Integer, String> cache = new LRUCache<>(3, CacheReplacementPolicy.LRU);

        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");

        cache.get(1);

        cache.put(4, "Four");

        assertNull(cache.get(2));
        assertEquals("One", cache.get(1));
        assertEquals("Three", cache.get(3));
        assertEquals("Four", cache.get(4));
    }

    @Test
    public void LRUTestUpdateValue() {
        LRUCache<Integer, String> cache = new LRUCache<>(3, CacheReplacementPolicy.LRU);

        cache.put(1, "One");
        cache.put(2, "Two");

        cache.put(1, "UpdatedOne");

        assertEquals("UpdatedOne", cache.get(1));
        assertEquals("Two", cache.get(2));
    }

    @Test
    public void LRUTestRemoveCapacityOne() {
        LRUCache<Integer, String> cache = new LRUCache<>(1, CacheReplacementPolicy.LRU);

        cache.put(1, "One");
        assertEquals("One", cache.get(1));

        cache.put(2, "Two");
        assertNull(cache.get(1));
        assertEquals("Two", cache.get(2));
    }

    @Test
     public void LRUTestEmptyCache() {
        LRUCache<Integer, String> cache = new LRUCache<>(3, CacheReplacementPolicy.LRU);

        assertNull(cache.get(1));
    }

    @Test
     public void LRUTestCapacityZero() {
        LRUCache<Integer, String> cache = new LRUCache<>(0, CacheReplacementPolicy.LRU);

        cache.put(1, "One");
        assertNull(cache.get(1));
    }

    @Test
    public void LRUStressTest() {
        int capacity = 10000;
        int count = 100000;
        LRUCache<Integer, Integer> cache = new LRUCache<>(capacity, CacheReplacementPolicy.LRU);

        for (int i = 0; i < count; i++) {
            cache.put(i, i);
        }

        for (int i = 0; i < count - capacity; i++) {
            assertNull("key " + i + " should have been removed", cache.get(i));
        }

        for (int i = count - capacity; i < count; i++) {
            assertEquals("key " + i + " should still be in the cache", (Integer) i, cache.get(i));
        }
    }

    @Test
    public void LRUSecondStressTest() {
        int capacity = 100000;
        int count = 1000000;
        LRUCache<Integer, Integer> cache = new LRUCache<>(capacity, CacheReplacementPolicy.LRU);
        Random random = new Random();

        for (int i = 0; i < capacity; i++) {
            cache.put(i, i);
        }

        for (int i = 0; i < count; i++) {
            int key = random.nextInt(capacity * 2);
            if (random.nextBoolean()) {
                cache.put(key, key);

            } else {
                Integer value = cache.get(key);
                if (value != null) {
                    assertEquals((Integer) key, value);
                }
            }
        }
    }


    @Test
    public void MRUTestPutAndGet() {
        LRUCache<Integer, String> cache = new LRUCache<>(3, CacheReplacementPolicy.MRU);

        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        assertEquals("one", cache.get(1));
        assertEquals("two", cache.get(2));
        assertEquals("three", cache.get(3));
    }

    @Test
    public void MRUTestRemove() {
        LRUCache<Integer, String> cache = new LRUCache<>(3, CacheReplacementPolicy.MRU);

        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");
        cache.put(4, "Four");

        assertNull(cache.get(3));
        assertEquals("One", cache.get(1));
        assertEquals("Two", cache.get(2));
        assertEquals("Four", cache.get(4));
    }

    @Test
    public void MRUTestUpdateOrder() {
        LRUCache<Integer, String> cache = new LRUCache<>(3, CacheReplacementPolicy.MRU);

        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");

        cache.get(1);

        cache.put(4, "Four");

        assertNull(cache.get(1));
        assertEquals("Two", cache.get(2));
        assertEquals("Three", cache.get(3));
        assertEquals("Four", cache.get(4));
    }

    @Test
    public void MRUTestUpdateValue() {
        LRUCache<Integer, String> cache = new LRUCache<>(3, CacheReplacementPolicy.MRU);

        cache.put(1, "One");
        cache.put(2, "Two");

        cache.put(1, "UpdatedOne");

        assertEquals("UpdatedOne", cache.get(1));
        assertEquals("Two", cache.get(2));
    }

    @Test
    public void MRUTestRemoveCapacityOne() {
        LRUCache<Integer, String> cache = new LRUCache<>(1, CacheReplacementPolicy.MRU);

        cache.put(1, "One");
        assertEquals("One", cache.get(1));

        cache.put(2, "Two");
        assertNull(cache.get(1));
        assertEquals("Two", cache.get(2));
    }

    @Test
    public void MRUTestEmptyCache() {
        LRUCache<Integer, String> cache = new LRUCache<>(3, CacheReplacementPolicy.MRU);

        assertNull(cache.get(1));
    }

    @Test
    public void MRUTestCapacityZero() {
        LRUCache<Integer, String> cache = new LRUCache<>(0, CacheReplacementPolicy.MRU);

        cache.put(1, "One");
        assertNull(cache.get(1));
    }

    @Test
    public void MRUStressTest() {
        int capacity = 10000;
        int count = 100000;
        LRUCache<Integer, Integer> cache = new LRUCache<>(capacity, CacheReplacementPolicy.MRU);

        for (int i = 1; i <= count; i++) {
            cache.put(i, i);
        }

        for (int i = count - 1; i >= count - capacity; i--) {
            assertNull("key " + i + " should have been removed", cache.get(i));
        }

        for (int i = 1; i < capacity; i++) {
            assertEquals("key " + i + " should still be in the cache", (Integer) i, cache.get(i));
        }

        assertEquals("key " + count + " should still be in the cache", (Integer) count, cache.get(count));
    }

    @Test
    public void MRUSecondStressTest() {
        int capacity = 100000;
        int count = 1000000;
        LRUCache<Integer, Integer> cache = new LRUCache<>(capacity, CacheReplacementPolicy.MRU);
        Random random = new Random();

        for (int i = 0; i < capacity; i++) {
            cache.put(i, i);
        }

        for (int i = 0; i < count; i++) {
            int key = random.nextInt(capacity * 2);
            if (random.nextBoolean()) {
                cache.put(key, key);

            } else {
                Integer value = cache.get(key);
                if (value != null) {
                    assertEquals((Integer) key, value);
                }
            }
        }
    }


}
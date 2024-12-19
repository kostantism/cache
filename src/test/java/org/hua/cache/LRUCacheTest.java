package org.hua.cache;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LRUCacheTest {

    @Test
    public void testCache() {
        testPutAndGet();
        testRemove();;
        testUpdateOrder();
        testUpdateValue();
        testRemoveCapacityOne();
        testEmptyCache();
        testCapacityZero();

        stressTest();
    }

    private void testPutAndGet() {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        assertEquals("one", cache.get(1));
        assertEquals("two", cache.get(2));
        assertEquals("three", cache.get(3));
    }

    private  void testRemove() {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");
        cache.put(4, "Four");

        assertNull(cache.get(1));
        assertEquals("Two", cache.get(2));
        assertEquals("Three", cache.get(3));
        assertEquals("Four", cache.get(4));
    }

    private void testUpdateOrder() {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

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

    void testUpdateValue() {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        cache.put(1, "One");
        cache.put(2, "Two");

        cache.put(1, "UpdatedOne");

        assertEquals("UpdatedOne", cache.get(1));
        assertEquals("Two", cache.get(2));
    }

    void testRemoveCapacityOne() {
        LRUCache<Integer, String> cache = new LRUCache<>(1);

        cache.put(1, "One");
        assertEquals("One", cache.get(1));

        cache.put(2, "Two");
        assertNull(cache.get(1));
        assertEquals("Two", cache.get(2));
    }

    void testEmptyCache() {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        assertNull(cache.get(1));
    }

    void testCapacityZero() {
        LRUCache<Integer, String> cache = new LRUCache<>(0);

        cache.put(1, "One");
        assertNull(cache.get(1)); // No elements should be stored
    }

    public void stressTest() {
        int capacity = 10000;
        int count = 100000;
        LRUCache<Integer, Integer> cache = new LRUCache<>(capacity);

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

}
package org.hua.cache;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LRUCacheTest {

    @Test
    public void testCache() {
        testPutAndGet();
        testEvictionPolicy();
        testAccessUpdatesUsageOrder();
        testOverwriteValue();
        testEvictionOnCapacity();
        testEmptyCache();
        testEdgeCaseCapacityZero();

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

    private  void testEvictionPolicy() {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        // Add entries
        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");
        cache.put(4, "Four"); // Evicts key 1

        assertNull(cache.get(1)); // Key 1 should have been evicted
        assertEquals("Two", cache.get(2));
        assertEquals("Three", cache.get(3));
        assertEquals("Four", cache.get(4));
    }

    private void testAccessUpdatesUsageOrder() {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        // Add entries
        cache.put(1, "One");
        cache.put(2, "Two");
        cache.put(3, "Three");

        // Access key 1 to make it recently used
        cache.get(1);

        // Add another entry, evicting the least recently used (key 2)
        cache.put(4, "Four");

        assertNull(cache.get(2)); // Key 2 should have been evicted
        assertEquals("One", cache.get(1));
        assertEquals("Three", cache.get(3));
        assertEquals("Four", cache.get(4));
    }

    void testOverwriteValue() {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        // Add entries
        cache.put(1, "One");
        cache.put(2, "Two");

        // Overwrite key 1
        cache.put(1, "UpdatedOne");

        assertEquals("UpdatedOne", cache.get(1));
        assertEquals("Two", cache.get(2));
    }

    void testEvictionOnCapacity() {
        LRUCache<Integer, String> cache = new LRUCache<>(1); // Capacity of 1

        cache.put(1, "One");
        assertEquals("One", cache.get(1));

        cache.put(2, "Two"); // Evicts key 1
        assertNull(cache.get(1));
        assertEquals("Two", cache.get(2));
    }

    void testEmptyCache() {
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        assertNull(cache.get(1)); // Getting from an empty cache should return null
    }

    void testEdgeCaseCapacityZero() {
        LRUCache<Integer, String> cache = new LRUCache<>(0);

        cache.put(1, "One");
        assertNull(cache.get(1)); // No elements should be stored
    }

    public void stressTest() {
        int capacity = 10000; // Set a smaller capacity to test evictions
        int numOperations = 100000;
        LRUCache<Integer, Integer> cache = new LRUCache<>(capacity);

        // Put operations
        for (int i = 0; i < numOperations; i++) {
            cache.put(i, i);
        }

         //Assert that the cache contains only the most recent `capacity` keys
        for (int i = 0; i < numOperations - capacity; i++) {
            assertNull("Key " + i + " should have been evicted", cache.get(i));
        }

        for (int i = numOperations - capacity; i < numOperations; i++) {
            assertEquals("Key " + i + " should still be in the cache", (Integer) i, cache.get(i));
        }
    }

}
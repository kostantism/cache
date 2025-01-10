package org.hua.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MyCache<K, V> implements Cache<K, V>{

    private int capacity;
    private Map<K, Node<K, V>> hashMap;
    private DoubleLinkedList<K, V> DLList;

    private int hit;
    private int miss;

    private TreeMap<Integer, HashMap<K, Node<K, V>>> treeMap;

    private CacheReplacementPolicy replacementPolicy;

    public MyCache(int capacity, CacheReplacementPolicy replacementPolicy) {
        this.capacity = capacity;
        this.replacementPolicy = replacementPolicy;
        this.hashMap = new HashMap<>();
        this.DLList = new DoubleLinkedList<>();

        this.treeMap = new TreeMap<>();

        this.hit = 0;
        this.miss = 0;
    }

    public CacheReplacementPolicy getReplacementPolicy() {
        return replacementPolicy;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = hashMap.get(key);
        if(node == null){
            miss++;
            return null;
        }
        
        hit++;

        if(replacementPolicy == CacheReplacementPolicy.LFU) {
            updateFrequency(node);
        } else {
            DLList.moveNodeToTail(node);
        }

        return hashMap.get(key).value;
    }

    @Override
    public void put(K key, V value) {
        if(capacity == 0){
            return;
        }

        Node<K, V> currentNode = hashMap.get(key);
        if(currentNode != null) {
            currentNode.value = value;
//            DLList.moveNodeToTail(currentNode);

            if (replacementPolicy == CacheReplacementPolicy.LFU) {
                updateFrequency(currentNode);
            } else {
                DLList.moveNodeToTail(currentNode);
            }

//            currentNode.frequency++;///////////
        }

        if(hashMap.size() == capacity) {
            if(replacementPolicy == CacheReplacementPolicy.LRU) {
                K headKey = DLList.getHeadKey();
                DLList.removeNodeFromHead();
                hashMap.remove(headKey);

            } else if(replacementPolicy == CacheReplacementPolicy.MRU) {
                K tailKey = DLList.getTailKey();
                DLList.removeNodeFromTail();
                hashMap.remove(tailKey);

            } else if(replacementPolicy == CacheReplacementPolicy.LFU) {
                removeLFUKey();
            }
        }

        Node<K, V> newNode = new Node<>(key, value);
        if (replacementPolicy == CacheReplacementPolicy.LFU) {
            addLFUNode(newNode);
        } else {
            DLList.addNewNode(newNode);
        }
//        DLList.addNewNode(newNode);
        hashMap.put(key, newNode);

    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> previous, next;

        int frequency;

        Node(K key, V value){
            this.key = key;
            this. value = value;
            this.next = null;
            this.previous = null;

            this.frequency = 1;
        }
    }

    private static class DoubleLinkedList<K, V> {
        private Node<K, V> head, tail;

        public DoubleLinkedList() {
            head = new Node<>(null, null);
            tail = new Node<>(null, null);
            head.next = tail;
            tail.previous = head;
        }

        public void addNewNode(Node<K, V> node) {
            node.previous = tail.previous;
            node.next = tail;
            tail.previous.next = node;
            tail.previous = node;
        }

        public void moveNodeToTail(Node<K, V> node) {
            if(tail == node){
                return;
            }

            if(node == head){
                head = tail.previous;
                head.next = null;
            } else {
                node.previous.next = node.next;
                node.next.previous = node.previous;
            }

            node.previous = tail.previous;
            node.next = tail;
            tail.previous.next = node;
            tail.previous = node;
        }

        public void removeNodeFromHead() {
            if(head.next == tail) {
                return;
            }

            Node<K, V> nodeToRemove = head.next;
            head.next = nodeToRemove.next;
            nodeToRemove.next.previous = head;

            nodeToRemove.previous = null;
            nodeToRemove.next = null;

        }

        public void removeNodeFromTail() {
            if (tail.previous == head) {
                return;
            }

            Node<K, V> nodeToRemove = tail.previous;
            tail.previous = nodeToRemove.previous;
            nodeToRemove.previous.next = tail;

            nodeToRemove.previous = null;
            nodeToRemove.next = null;
        }

        private K getHeadKey() {
            if (head.next != null) {
                return head.next.key;
            }
            return null;
        }

        private K getTailKey() {
            if (tail.previous != head) {
                return tail.previous.key;
            }
            return null;
        }
    }

    private void updateFrequency(Node<K, V> node) {
        int currentFrequency = node.frequency;

        HashMap<K, Node<K, V>> currentFrequencyMap = treeMap.get(currentFrequency);
        currentFrequencyMap.remove(node.key);
        if (currentFrequencyMap.isEmpty()) {
            treeMap.remove(currentFrequency);
        }

        node.frequency++;
        if (!treeMap.containsKey(node.frequency)) {
            treeMap.put(node.frequency, new HashMap<>());
        }
        treeMap.get(node.frequency).put(node.key, node);

    }

    private void addLFUNode(Node<K, V> node) {
        node.frequency = 1;
        if (!treeMap.containsKey(1)) {
            treeMap.put(1, new HashMap<>());
        }
        treeMap.get(1).put(node.key, node);
    }

    private void removeLFUKey() {
//        int minFrequency = treeMap.firstKey();
        HashMap<K, Node<K, V>> leastFrequentMap = treeMap.get(treeMap.firstKey());

        K keyToRemove = null;
        for (K key : leastFrequentMap.keySet()) {
            keyToRemove = key;
            break;
        }

        leastFrequentMap.remove(keyToRemove);
        if (leastFrequentMap.isEmpty()) {
            treeMap.remove(treeMap.firstKey());
        }
        hashMap.remove(keyToRemove);
    }



}

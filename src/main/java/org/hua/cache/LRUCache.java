package org.hua.cache;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> implements Cache<K, V>{

    private int capacity;
    private Map<K, Node<K, V>> hashMap;
    private DoubleLinkedList<K, V> DLList;

    private int hit;
    private int miss;

    private CacheReplacementPolicy replacementPolicy;

    public LRUCache(int capacity, CacheReplacementPolicy replacementPolicy) {
        this.capacity = capacity;
        this.replacementPolicy = replacementPolicy;
        this.hashMap = new HashMap<>();
        this.DLList = new DoubleLinkedList<>();

        this.hit = 0;
        this.miss = 0;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = hashMap.get(key);
        if(node == null){
            miss++;
            return null;
        }
        hit++;
        DLList.moveNodeToTail(node);
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
            DLList.moveNodeToTail(currentNode);
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
            }
        }

        Node<K, V> newNode = new Node<>(key, value);
        DLList.addNewNode(newNode);
        hashMap.put(key, newNode);

    }

    @Override
    public int getHitCount() {
        return hit;
    }

    @Override
    public int getMissCount() {
        return miss;
    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> previous, next;

        Node(K key, V value){
            this.key = key;
            this. value = value;
            this.next = null;
            this.previous = null;
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
                return; // Αν η λίστα είναι κενή.
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

}

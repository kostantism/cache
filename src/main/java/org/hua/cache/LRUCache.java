package org.hua.cache;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> implements Cache<K, V>{

    private int capacity;
    private Map<K, Node<K, V>> hashMap;
    private DoubleLinkedList<K, V> DLList;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.hashMap = new HashMap<>();
        this.DLList = new DoubleLinkedList<>();
    }

    @Override
    public V get(K key) {
        Node<K, V> node = hashMap.get(key);
        if(node == null){
            return null;
        }
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
            K headKey = DLList.getHeadKey();
            DLList.removeNodeFromHead();
            hashMap.remove(headKey);
        }

        Node<K, V> newNode = new Node<>(key, value);
        DLList.addNewNode(newNode);
        hashMap.put(key, newNode);

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
                node.previous.next = node.next;//αφαιρει
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

        private K getHeadKey() {
            if (head.next != null) {
                return head.next.key;
            }
            return null;
        }
    }
}

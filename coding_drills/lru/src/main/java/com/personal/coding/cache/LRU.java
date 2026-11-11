package com.personal.coding.cache;
import  com.personal.coding.models.DoublyLinkedListNode;

import java.util.HashMap;
public class LRU {
    DoublyLinkedListNode head=new DoublyLinkedListNode();
    DoublyLinkedListNode tail=new DoublyLinkedListNode();
    HashMap<String,DoublyLinkedListNode> cache=new HashMap();
    int capactiy;
    public LRU(int capacity) {
        head.next=tail;
        tail.prev=head;
        this.capactiy=capacity;
    }

    public void put(String key,int value) {

        if (cache.containsKey(key)) {
            DoublyLinkedListNode n = cache.get(key);
            n.val=value;
            DoublyLinkedListNode prev = n.prev;
            DoublyLinkedListNode next = n.next;
            prev.next=next;
            next.prev=prev;
            n.next = head.next;
            head.next.prev=n;
            head.next=n;
            n.prev=head;
            return;
        }

        if (cache.size() >= capactiy) {
            DoublyLinkedListNode toRemove = tail.prev;
            toRemove.prev.next=tail;
            tail.prev=toRemove.prev;
            cache.remove(toRemove.key);
        }
        DoublyLinkedListNode newNode = new DoublyLinkedListNode();
        newNode.val=value;
        newNode.key=key;
        cache.put(key,newNode);
        newNode.next=head.next;
        head.next.prev=newNode;
        head.next=newNode;
        newNode.prev=head;
    }

    public Integer get(String key) {

        if (cache.containsKey(key)) {
            DoublyLinkedListNode n = cache.get(key);
            DoublyLinkedListNode prev = n.prev;
            DoublyLinkedListNode next = n.next;
            prev.next=next;
            next.prev=prev;
            n.next = head.next;
            head.next.prev=n;
            head.next=n;
            n.prev=head;
            return n.val;
        }
        return null;
    }
}

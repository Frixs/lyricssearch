package main.frixs.lyricssearch.model;

import main.frixs.lyricssearch.service.Log;
import main.frixs.lyricssearch.service.LogType;

/**
 * @author Frixs
 * Implemented as linked list
 */
public class Queue<T> {
    /** first element of the list */
    private Node first;
    /** last elemnt of the list */
    private Node last;
    /** size of the list */
    private int size;

    /**
     * No-parameter constructor
     */
    public Queue() {
        this.size = 0;
    }

    /**
     * Add new item to the list
     * @param item  added item
     */
    public void add(T item) {
        Node node = new Node(item);
        if (this.isEmpty()) {
            this.first  = node;
            this.last   = node;
        } else {
            this.last.next = node;
            this.last      = node;
        }

        this.size++;
    }

    /**
     * Get item on the current index
     * @param index     index of the list
     * @return          item instance
     */
    public T get(int index) {
        if (index >= this.getSize()) {
            Log.getInstance().log(LogType.WARNING, getClass().getName() +": Index is out of bounds!");
            throw new IndexOutOfBoundsException("Index is "+ index +" out of bounds! Size: "+ getSize());
        } else if (index < 0) {
            Log.getInstance().log(LogType.WARNING, getClass().getName() +": Index is in negative numbers.");
            throw new IndexOutOfBoundsException("Index "+ index +" is in negative numbers.");
        }

        Node currentNode = first;
        for (int j = 0; j < index; j++) {
            currentNode = currentNode.next;
        }

        return (T) currentNode.value;
    }

    /**
     * Remove item on the current index
     * @param index     index of the list
     */
    public void remove(int index) {
        if (index >= this.getSize()) {
            Log.getInstance().log(LogType.WARNING, getClass().getName() +": Index is out of bounds!");
            throw new IndexOutOfBoundsException("Index is "+ index +" out of bounds! Size: "+ getSize());
        } else if (index < 0) {
            Log.getInstance().log(LogType.WARNING, getClass().getName() +": Index is in negative numbers.");
            throw new IndexOutOfBoundsException("Index "+ index +" is in negative numbers.");
        }

        if (index == 0) {
            first = first.next;
        } else {
            Node currentNode = first;

            // get item before deleted item
            for (int j = 0; j < index - 1; j++) {
                currentNode = currentNode.next;
            }

            // skip deleted item and connect it
            currentNode.next = currentNode.next.next;
            // if it is item on the end of the list, set it
            if (index == getSize() - 1) {
                last = currentNode;
            }
        }

        this.size--;
    }

    /**
     * Get size of the list
     * @return size of the list
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Get bool of list empty status
     * @return  boolean, true if list is empty
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Common toString method
     * @return whole queue in string
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node currentNode = first;
        for (int i = 0; i < this.size; i++) {
            builder.append(currentNode.value);
            builder.append(" ");
            currentNode = currentNode.next;
        }
        return builder.toString();
    }

    /**
     * Inner class representing 1 node of linked list
     */
    private class Node<T> {
        private T value;
        private Node next;

        private Node(T value) {
            this.value = value;
        }
    }
}

package com.company.homework02;

import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Emily Hang
 * @version 1.0
 * @userid ehang3
 * @GTID 903590279
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     * <p>
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index can not be less than 0 or greater than size of list.");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null.");
        }
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else if (index <= size / 2) {
            DoublyLinkedListNode<T> current = head;
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data);
            for (int x = 0; x <= size / 2; x++) {
                if (x + 1 == index) {
                    newNode.setNext(current.getNext());
                    newNode.setPrevious(current);
                    current.getNext().setPrevious(newNode);
                    current.setNext(newNode);
                }
                current = current.getNext();
            }
            size++;
        } else {
            DoublyLinkedListNode<T> current = tail;
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data);
            for (int x = size; x > size / 2; x--) {
                if (x - 1 == index) {
                    newNode.setPrevious(current.getPrevious());
                    current.getPrevious().setNext(newNode);
                    current.setPrevious(newNode);
                    newNode.setNext(current);
                }
                current = current.getPrevious();
            }
            size++;
        }

    }

    /**
     * Adds the element to the front of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
        if (head != null) {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data);
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data);
            head = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null.");
        }
        if (head != null) {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data);
            newNode.setPrevious(tail);
            tail.setNext(newNode);
            tail = newNode;
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<T>(data);
            head = newNode;
            tail = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     * <p>
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index can not be less than 0 or greater than or equal to size.");
        }
        DoublyLinkedListNode<T> removedNode = new DoublyLinkedListNode<T>(null);
        DoublyLinkedListNode<T> newNode;
        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else if (index <= size / 2) {
            newNode = head;
            for (int x = 0; x <= size / 2; x++) {
                if (x + 1 == index) {
                    removedNode = newNode.getNext();
                    newNode.getNext().getNext().setPrevious(newNode);
                    newNode.setNext(newNode.getNext().getNext());
                }
                newNode = newNode.getNext();
            }
            size--;
        } else {
            for (int x = size - 1; x > size / 2; x--) {
                newNode = tail;
                if (x - 1 == index) {
                    removedNode = newNode.getPrevious();
                    newNode.setPrevious(newNode.getPrevious().getPrevious());
                    newNode.getPrevious().getPrevious().setNext(newNode);
                }
                newNode = newNode.getNext();
            }
            size--;
        }
        return (T) removedNode.getData();
    }

    /**
     * Removes and returns the first element of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (head == null) {
            throw new NoSuchElementException("Nothing can be removed: List is empty.");
        }
        T data = head.getData();
        if (size == 1) {
            head = null;
            tail = null;
        } else if (size == 2) {
            head = head.getNext();
            tail = head;
        } else {
            head.getNext().setPrevious(null);
            head = head.getNext();
        }
        size--;
        return (T) data;
    }

    /**
     * Removes and returns the last element of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (head == null) {
            throw new NoSuchElementException("Nothing can be removed: List is empty.");
        }
        T data = tail.getData();
        if (size == 1) {
            head = null;
            tail = null;
        } else if (size == 2) {
            tail = tail.getPrevious();
            head = tail;
        } else {
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
        }
        size--;
        return data;
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     * <p>
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index can not be zero nor greater than or equal to size.");
        }
        DoublyLinkedListNode<T> newNode;
        if (index == 0) {
            return (T) head.getData();
        } else if (index == size - 1) {
            return (T) tail.getData();
        } else if (index <= size / 2) {
            newNode = head;
            for (int x = 0; x <= size / 2; x++) {
                if (x == index) {
                    return (T) newNode.getData();
                }
                newNode = newNode.getNext();
            }
        } else {
            newNode = tail;
            for (int x = size - 1; x > size / 2; x--) {
                if (x == index) {
                    return (T) newNode.getData();
                }
                newNode = newNode.getPrevious();
            }
        }
        return (T) newNode.getData();
    }

    /**
     * Returns whether or not the list is empty.
     * <p>
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Clears the list.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     * <p>
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data can not be null.");
        }
        if (size == 0) {
            throw new NoSuchElementException("Data is not found in list.");
        }
        if (tail.getData().equals(data)) {
            return removeFromBack();
        }
        DoublyLinkedListNode<T> newNode = tail;
        for (int x = size; x > 0; x--) {
            if (newNode.getData().equals(data)) {
                break;
            }
            newNode = newNode.getPrevious();
        }
        if (newNode != null) {
            if (newNode == head) {
                return removeFromFront();
            } else if(newNode == tail) {
                return removeFromBack();
            } else {
                newNode.getPrevious().setNext(newNode.getNext());
                newNode.getNext().setPrevious(newNode.getPrevious());
                size--;
                return newNode.getData();
            }
        }
        throw new NoSuchElementException("Data is not found in list.");
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     * <p>
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] listArray = new Object[size];
        DoublyLinkedListNode<T> current = head;
        for (int x = 0; x < size; x++) {
            listArray[x] = current.getData();
            current = current.getNext();
        }
        return (Object[]) listArray;
    }

    /**
     * Returns the head node of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}

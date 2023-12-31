
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
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
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     * <p>
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     * <p>
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     * <p>
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        size = data.size();
        backingArray = (T[]) new Comparable[size * 2 + 1];
        for (int x = 0; x < size; x++) {
            if (data.get(x) == null) {
                throw new IllegalArgumentException("No element of data can be null.");
            }
            backingArray[x + 1] = data.get(x);
        }
        int cur = size / 2;
        while (cur > 0) {
            downheap(cur);
            cur--;
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        size++;
        if (size >= backingArray.length) {
            T[] backingArray2 = (T[]) new Comparable[backingArray.length * 2];
            for (int x = 1; x < backingArray.length; x++) {
                backingArray2[x] = backingArray[x];
            }
            backingArray = backingArray2;
        }
        backingArray[size] = data;
        upheap(backingArray, size);
    }

    /**
     * method that performs upheap operation on the backing array.
     *
     * @param arr backing array
     * @param index the index where the upheap operation starts
     */
    private void upheap(T[] arr, int index) {
        while (index / 2 > 0) {
            if (arr[index].compareTo(arr[index / 2]) < 0) {
                T data = arr[index / 2];
                arr[index / 2] = arr[index];
                arr[index] = data;
            }
            index /= 2;
        }
    }

    /**
     * method that performs downheap operation on the backing array.
     *
     * @param index the index where the downheap operation starts
     */
    private void downheap(int index) {
        while (index <= size) {
            if (2 * index + 1 <= size && backingArray[2 * index].compareTo(backingArray[2 * index + 1]) > 0) {
                if (backingArray[2 * index + 1].compareTo(backingArray[index]) < 0) {
                    T data = backingArray[index];
                    backingArray[index] = backingArray[2 * index + 1];
                    backingArray[2 * index + 1] = data;
                    index = index * 2 + 1;
                } else {
                    break;
                }
            } else if (2 * index <= size && backingArray[2 * index].compareTo(backingArray[index]) < 0) {
                T data = backingArray[index];
                backingArray[index] = backingArray[2 * index];
                backingArray[2 * index] = data;
                index = index * 2;
            } else {
                break;
            }
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after adding.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Cannot remove when heap is empty.");
        }
        T data = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        downheap(1);
        return data;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Array is empty.");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     * <p>
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}

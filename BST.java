import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize an empty BST.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        size = 0;
        for (T t : data) {
            if (t == null) {
                throw new IllegalArgumentException("No element of data can be null.");
            }
            add(t);
        }
    }

    /**
     * Adds data recursively to tree.
     *
     * @param data the data to add
     * @param node the node you are currently at
     */
    private void addRecurse(T data, BSTNode<T> node) {

        if (data.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BSTNode<>(data));
                size++;
            } else {
                addRecurse(data, node.getLeft());
            }
        } else if (data.compareTo(node.getData()) > 0) {
            if (node.getRight() == null) {
                node.setRight(new BSTNode<>(data));
                size++;
            } else {
                addRecurse(data, node.getRight());
            }
        }
    }

    /**
     * Adds the data to the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * The data becomes a leaf in the tree.
     * <p>
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (root == null) {
            root = new BSTNode<>(data);
            size++;
        }
        addRecurse(data, root);
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = rRemove(root, data, dummy);
        return dummy.getData();
    }

    /**
     * helps remove the data within the given parameter and returns a node containing removed data.
     *
     * @param curr the current node that is being compared to the data that is being removed
     * @param data the data that is looking to be removed
     * @param dummy node containing the data that was removed
     * @return node containing removed data
     */
    private BSTNode<T> rRemove(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Data does not exist in this tree.");
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(rRemove(curr.getLeft(), data, dummy));
            return curr;
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(rRemove(curr.getRight(), data, dummy));
            return curr;
        } else {
            dummy.setData(curr.getData());
            size--;
            if ((curr.getLeft() == null) && (curr.getRight() == null)) {
                return null;
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getRight() != null && curr.getLeft() == null) {
                return curr.getRight();
            } else {
                BSTNode<T> dummy2 = new BSTNode<>(null);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        return curr;
    }

    /**
     * method that removes the successor of the root of the tree that is having data removed.
     * @param curr the current node that is being looked at to see if it is the successor
     * @param dummy node containing the successor node's data
     * @return dummy node that contains the successor data
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        }
        curr.setLeft(removeSuccessor(curr.getLeft(), dummy));
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        return helpGet(root, data);
    }

    /**
     * private helper method that gets the data from the tree matching parameter.
     *
     * @param node root of tree/each subtree
     * @param data data that is being looked for within tree
     * @return value of the data that was searched for within the tree
     */
    private T helpGet(BSTNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("The data you are looking for does not exist in this tree.");
        }
        if (node.getData().compareTo((T) data) > 0) {
            return helpGet(node.getLeft(), data);
        } else if (node.getData().compareTo((T) data) < 0) {
            return helpGet(node.getRight(), data);
        }
        return (T) node.getData();
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        return containsHelper(data, root);
    }

    /**
     * private method that determines whether the given data is within the tree.
     *
     * @param data data that is being looked for
     * @param node root of each tree/subtree
     * @return true if data is within tree; false otherwise
     */
    private boolean containsHelper(T data, BSTNode<T> node) {
        if (node == null) {
            return false;
        }
        if (data.compareTo(node.getData()) > 0) {
            return containsHelper(data, node.getRight());
        } else if (data.compareTo(node.getData()) < 0) {
            return containsHelper(data, node.getLeft());
        }
        return true;
    }

    /**
     * Generate a pre-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<T>();
        preorderHelper(root, list);
        return list;
    }

    /**
     * helps to create a list of data in the BST through a preorder traversal.
     *
     * @param node node that is part of the BST
     * @param list list that will contain BST data in preorder
     */
    private void preorderHelper(BSTNode<T> node, List<T> list) {
        if (node != null) {
            list.add(node.getData());
            preorderHelper(node.getLeft(), list);
            preorderHelper(node.getRight(), list);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        inorderHelper(root, list);
        return list;
    }

    /**
     * helper method to create an in-order traversal of the BST.
     *
     * @param node node that is within the BST
     * @param list list containing the data of the BST in inorder
     */
    private void inorderHelper(BSTNode<T> node, List list) {
        if (node != null) {
            inorderHelper(node.getLeft(), list);
            list.add(node.getData());
            inorderHelper(node.getRight(), list);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        postorderHelper(root, list);
        return list;
    }

    /**
     * helps to create a list of data in the BST in a postorder traversal.
     *
     * @param node node that is part of the BST
     * @param list list that will contain BST data in postorder
     */
    private void postorderHelper(BSTNode<T> node, List list) {
        if (node != null) {
            postorderHelper(node.getLeft(), list);
            postorderHelper(node.getRight(), list);
            list.add(node.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     * <p>
     * This does not need to be done recursively.
     * <p>
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     * <p>
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new ArrayList<>();
        LinkedList<BSTNode<T>> q = new LinkedList<>();
        q.addFirst(root);
        while (!q.isEmpty()) {
            BSTNode<T> curr = q.getFirst();
            list.add((T) q.removeFirst().getData());
            if (curr != null) {
                if (curr.getLeft() != null) {
                    q.add(curr.getLeft());
                }
                if (curr.getRight() != null) {
                    q.add(curr.getRight());
                }
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     * <p>
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /**
     * helper method to find height of tree
     *
     * @param node nodes within the tree (the root of each subtree)
     * @return int integer representing height of tree
     */
    private int height(BSTNode<T> node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }

    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     * <p>
     * This must be done recursively.
     * <p>
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     * <p>
     * EXAMPLE: Given the BST below composed of Integers:
     * <p>
     * 50
     * /    \
     * 25      75
     * /  \
     * 12   37
     * /  \    \
     * 10  15    40
     * /
     * 13
     * <p>
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     * <p>
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */
    public List<T> kLargest(int k) {
        if (k > size) {
            throw new IllegalArgumentException("Cannot find more elements than in tree.");
        }
        List<T> list = new ArrayList<>();
        kLargestHelper(k, list, root);
        return list;
    }

    /**
     * helps determine the largest k values and store it in a list.
     *
     * @param k    how many values to find in the tree
     * @param list list that will contain the largest k values
     * @param node root of tree/subtree
     * @return int representing how many more values are necessary to find
     */
    private int kLargestHelper(int k, List<T> list, BSTNode<T> node) {
        if (k <= 0) {
            return 0;
        }
        if (node == null) {
            return k;
        }
        int remaining = kLargestHelper(k, list, node.getRight());
        if (remaining > 0) {
            list.add(0, node.getData());
            return kLargestHelper(remaining - 1, list, node.getLeft());
        }
        return 0;
    }

    /**
     * Returns the root of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}

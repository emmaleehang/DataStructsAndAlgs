import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Emily Hang
 * @version 1.0
 * @userid ehang3
 * @GTID 903590279
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
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
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     * <p>
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     * <p>
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @param data the data to be added
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        root = addRecurse(data, root);
    }

    /**
     * add data recursively
     *
     * @param data data that is being added
     * @param node node the current node you are at
     * @return node for pointer reinforcement
     */
    private AVLNode<T> addRecurse(T data, AVLNode<T> node) {
        if (node == null) {
            size++;
            return new AVLNode<T>(data);
        }
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addRecurse(data, node.getLeft()));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addRecurse(data, node.getRight()));
        }
        return update(node);
    }

    /**
     * method that updates the height and balance factors of each node
     *
     * @param node node that is being updated
     * @return new node that will be in the node that is passed in's position
     */
    private AVLNode<T> update(AVLNode<T> node) {
        int rightHeight = height(node.getRight());
        int leftHeight = height(node.getLeft());
        node.setHeight(1 + Math.max(leftHeight, rightHeight));
        node.setBalanceFactor(leftHeight - rightHeight);
        if (node.getBalanceFactor() == -2) {
            AVLNode<T> right = node.getRight();
            if (right.getBalanceFactor() == 1) {
                node.setRight(rightRotation(right));
            }
            return leftRotation(node);

        } else if (node.getBalanceFactor() == 2) {
            AVLNode<T> left = node.getLeft();
            if (left.getBalanceFactor() == -1) {
                node.setLeft(leftRotation(left));
            }
            return rightRotation(node);
        }
        return node;
    }

    /**
     * determines the height of a node
     *
     * @param node node that is getting height determined
     * @return height of node
     */
    private int height(AVLNode<T> node) {
        if (node == null) {
            return -1;
        }
        return node.getHeight();
    }

    /**
     * performs a right AVL rotation
     *
     * @param node that has a balance factor not equal to +/- 1 or 0
     * @return new root of subtree
     */
    private AVLNode<T> rightRotation(AVLNode<T> node) {
        AVLNode<T> left = node.getLeft();
        node.setLeft(left.getRight());
        left.setRight(node);
        update(node);
        update(left);
        return left;
    }

    /**
     * performs a left AVL rotation
     *
     * @param node that has a balance factor not equal to +/- 1 or 0
     * @return new root of subtree
     */
    private AVLNode<T> leftRotation(AVLNode<T> node) {
        AVLNode<T> right = node.getRight();
        node.setRight(right.getLeft());
        right.setLeft(node);
        update(node);
        update(right);
        return right;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     * <p>
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     * <p>
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     * @throws IllegalArgumentException         if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        root = rRemove(root, data, dummy);
        return dummy.getData();
    }


    /**
     * helps remove the data within the given parameter and returns a node containing removed data.
     *
     * @param curr  the current node that is being compared to the data that is being removed
     * @param data  the data that is looking to be removed
     * @param dummy node containing the data that was removed
     * @return node containing removed data
     */
    private AVLNode<T> rRemove(AVLNode<T> curr, T data, AVLNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Data does not exist in this tree.");
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(rRemove(curr.getLeft(), data, dummy));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(rRemove(curr.getRight(), data, dummy));
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
                AVLNode<T> dummy2 = new AVLNode<>(null);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        return update(curr);
    }

    /**
     * method that removes the successor of the root of the tree that is having data removed.
     *
     * @param curr  the current node that is being looked at to see if it is the successor
     * @param dummy node containing the successor node's data
     * @return dummy node that contains the successor data
     */
    private AVLNode<T> removeSuccessor(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        }
        curr.setLeft(removeSuccessor(curr.getLeft(), dummy));
        return update(curr);
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     * @throws IllegalArgumentException         if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
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
    private T helpGet(AVLNode<T> node, T data) {
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
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     * @throws IllegalArgumentException if the data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        return containsHelper(data, root);
    }

    /**
     * private method that determines whether the given data is within the tree.
     *
     * @param data data that is being looked for
     * @param node root of each tree/subtree
     * @return true if data is within tree; false otherwise
     */
    private boolean containsHelper(T data, AVLNode<T> node) {
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
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     * <p>
     * Your list should not duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     * <p>
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     * <p>
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     * <p>
     * Example Tree:
     * 10
     * /          \
     * 5            15
     * /   \        /    \
     * 2     7     13      20
     * / \   / \     \    / \
     * 1   4 6   8   14  17  25
     * /           \          \
     * 0             9         30
     * <p>
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> list = new ArrayList<T>();
        deepestBranchesHelper(root, list);
        return list;
    }

    /**
     * method that helps make a list of data of the branches of an AVL tree with greatest height
     *
     * @param node node that is currently being looked at
     * @param list list that deepest branches' data is being added to
     */
    private void deepestBranchesHelper(AVLNode<T> node, List<T> list) {
        if (node == null) {
            return;
        }
        list.add(node.getData());
        int leftHeight = -1;
        int rightHeight = -1;

        if (node.getLeft() != null) {
            leftHeight = node.getLeft().getHeight();
        }
        if (node.getRight() != null) {
            rightHeight = node.getRight().getHeight();
        }
        if (leftHeight > rightHeight) {
            deepestBranchesHelper(node.getLeft(), list);
        } else if (leftHeight < rightHeight) {
            deepestBranchesHelper(node.getRight(), list);
        } else {
            deepestBranchesHelper(node.getLeft(), list);
            deepestBranchesHelper(node.getRight(), list);
        }
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     * <p>
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     * <p>
     * Example Tree:
     * 10
     * /        \
     * 5          15
     * /   \       /    \
     * 2     7    13    20
     * / \   / \     \  / \
     * 1   4 6   8   14 17  25
     * /           \          \
     * 0             9         30
     * <p>
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     *              or if data1 > data2
     * @return a sorted list of data that is > data1 and < data2
     * @throws java.lang.IllegalArgumentException if data1 or data2 are null
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null || data2.compareTo(data1) < 0) {
            throw new IllegalArgumentException("Both data 1 and data 2 cannot be null.");
        }
        List<T> list = new ArrayList<T>();
        if (data1.equals(data2)) {
            return list;
        }
        sortedInBetweenHelper(data1, data2, list, root);
        return list;
    }

    /**
     * helps to create a list of data that is in between the bounds of data1 and data2
     *
     * @param data1 smaller data bound
     * @param data2 greater data bound
     * @param list  list that data within threshold is being added to
     * @param node  node that is currently being checked
     */
    private void sortedInBetweenHelper(T data1, T data2, List<T> list, AVLNode<T> node) {
        if (node == null) {
            return;
        }

        if (node.getData().compareTo(data1) <= 0) {
            sortedInBetweenHelper(data1, data2, list, node.getRight());
        } else if (node.getData().compareTo(data2) >= 0) {
            sortedInBetweenHelper(data1, data2, list, node.getLeft());
        } else {
            sortedInBetweenHelper(data1, data2, list, node.getLeft());
            list.add(node.getData());
            sortedInBetweenHelper(data1, data2, list, node.getRight());
        }
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
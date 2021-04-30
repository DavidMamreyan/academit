package ru.academits.mamreyan.my_tree;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public class MyTree<T> {
    private MyTreeNode<T> root;

    public MyTree() {
    }

    public MyTree(MyTreeNode<T> root) {
        if (root == null) {
            return;
        }

        this.root = root;
    }

    public MyTree(T data) {
        if (Objects.equals(data, null)) {
            return;
        }

        root = new MyTreeNode<>(data);
    }

    public boolean add(T data) {
        if (Objects.equals(data, null)) {
            return false;
        }

        if (root == null) {
            root = new MyTreeNode<>(data);

            return true;
        }

        MyTreeNode<T> current = root;
        MyTreeNode<T> newNode = new MyTreeNode<>(data);

        while (true) {
            int comparisonResult = compare(current, newNode);

            if (comparisonResult == 0) {
                return false;
            } else if (comparisonResult > 0) {
                if (current.getLeft() == null) {
                    current.setLeft(newNode);

                    return true;
                }

                current = current.getLeft();
            } else {
                if (current.getRight() == null) {
                    current.setRight(newNode);

                    return true;
                }

                current = current.getRight();
            }
        }
    }

    public boolean contains(T data) {
        if (root == null) {
            return false;
        }

        MyTreeNode<T> current = root;
        MyTreeNode<T> node = new MyTreeNode<>(data);

        while (true) {
            int comparisonResult = compare(current, node);

            if (comparisonResult == 0) {
                return true;
            } else if (comparisonResult > 0) {
                if (current.getLeft() == null) {
                    return false;
                }

                current = current.getLeft();
            } else {
                if (current.getRight() == null) {
                    return false;
                }

                current = current.getRight();
            }
        }
    }

    public MyTreeNode<T> get(T data) {
        if (root == null) {
            return null;
        }

        MyTreeNode<T> current = root;
        MyTreeNode<T> node = new MyTreeNode<>(data);

        while (true) {
            int comparisonResult = compare(current, node);

            if (comparisonResult == 0) {
                return current;
            } else if (comparisonResult > 0) {
                if (current.getLeft() == null) {
                    return null;
                }

                current = current.getLeft();
            } else {
                if (current.getRight() == null) {
                    return null;
                }

                current = current.getRight();
            }
        }
    }

    public boolean remove(T data) {
        if (root == null) {
            return false;
        }

        MyTreeNode<T> current = root;
        MyTreeNode<T> parent = null;
        MyTreeNode<T> node = new MyTreeNode<>(data);

        while (true) { // находим элемент и запоминаем его родителя
            int comparisonResult = compare(current, node);

            if (comparisonResult == 0) {
                break;
            } else if (comparisonResult > 0) {
                if (current.getLeft() == null) {
                    return false;
                }

                parent = current;
                current = current.getLeft();
            } else {
                if (current.getRight() == null) {
                    return false;
                }

                parent = current;
                current = current.getRight();
            }
        }

        if (current.hasLeft() && current.hasRight()) { // если есть два сына
            MyTreeNode<T> soughtNode = current;
            MyTreeNode<T> soughtNodeParent = parent;

            parent = current; // ищем самого левого сына правого поддерева
            current = current.getRight();

            while (current.hasLeft()) {
                parent = current;
                current = current.getLeft();
            }

            if (current.hasRight()) { // удаляем самого левого сына
                parent.setLeft(current.getRight());
            } else {
                parent.setLeft(null);
            }

            current.setLeft(soughtNode.getLeft()); // заменяем узел удаленным левым сыном
            current.setRight(soughtNode.getRight());

            if (soughtNodeParent == null) { // если узел - корень
                root = current;
            } else {
                if (soughtNodeParent.getRight() == soughtNode) { // если узел - правый сын
                    soughtNodeParent.setRight(current);
                } else { // если узел - левый сын
                    soughtNodeParent.setLeft(current);
                }
            }
        } else if (current.hasLeft()) { // если есть левый сын
            if (parent == null) { // если узел - корень
                root = current.getLeft();
            } else {
                if (parent.getRight() == current) { // если узел - правый сын
                    parent.setRight(current.getLeft());
                } else { // если узел - левый сын
                    parent.setLeft(current.getLeft());
                }
            }
        } else if (current.hasRight()) { // если есть правый сын
            if (parent == null) { // если узел - корень
                root = current.getRight();
            } else {
                if (parent.getRight() == current) { // если узел - правый сын
                    parent.setRight(current.getRight());
                } else {  // если узел - левый сын
                    parent.setLeft(current.getRight());
                }
            }
        } else { // если лист
            if (parent == null) { // если узел - корень
                root = null;
            } else {
                if (parent.getRight() == current) { // если узел - правый сын
                    parent.setRight(null);
                } else { // если узел - левый сын
                    parent.setLeft(null);
                }
            }
        }

        return true;
    }

    private <R> void traverseDepthFirstRecursive(Function<MyTreeNode<T>, R> f, MyTreeNode<T> node) {
        f.apply(node);

        if (node.hasLeft()) {
            traverseDepthFirstRecursive(f, node.getLeft());
        }

        if (node.hasRight()) {
            traverseDepthFirstRecursive(f, node.getRight());
        }
    }

    public <R> void traverseDepthFirstRecursive(Function<MyTreeNode<T>, R> f) {
        if (root == null) {
            return;
        }

        f.apply(root);

        if (root.hasLeft()) {
            traverseDepthFirstRecursive(f, root.getLeft());
        }

        if (root.hasRight()) {
            traverseDepthFirstRecursive(f, root.getRight());
        }
    }

    public <R> void traverseDepthFirst(Function<MyTreeNode<T>, R> f) {
        if (root == null) {
            return;
        }

        ArrayDeque<MyTreeNode<T>> deque = new ArrayDeque<>();
        deque.addFirst(root);

        while (!deque.isEmpty()) {
            MyTreeNode<T> node = deque.poll();

            f.apply(node);

            if (node.hasRight()) {
                deque.addFirst(node.getRight());
            }

            if (node.hasLeft()) {
                deque.addFirst(node.getLeft());
            }
        }
    }

    public <R> void traverseBreadthFirst(Function<MyTreeNode<T>, R> f) {
        if (root == null) {
            return;
        }

        ArrayDeque<MyTreeNode<T>> deque = new ArrayDeque<>();
        deque.addFirst(root);

        while (!deque.isEmpty()) {
            MyTreeNode<T> node = deque.poll();

            f.apply(node);

            if (node.hasLeft()) {
                deque.addLast(node.getLeft());
            }

            if (node.hasRight()) {
                deque.addLast(node.getRight());
            }
        }
    }

    public int getSize() {
        final int[] size = {0};

        traverseBreadthFirst(node -> size[0]++);

        return size[0];
    }

    // пересобирает дерево в сбалансированном виде
    // сначала записывает все элементы дерева в массив и сортирует его по возрастанию
    // очищает дерево и использует рекурсивную функцию, чтобы пересобрать его из массива
    public void balance() {
        //noinspection unchecked
        T[] array = (T[]) new Object[getSize()];
        final int[] i = {0};

        traverseDepthFirst((node) -> {
            array[i[0]] = node.getData();
            i[0]++;
            return array;
        });

        Arrays.sort(array, (data1, data2) -> {
            //noinspection unchecked
            return ((Comparable<? super T>) data1).compareTo(data2);
        });

        clear();

        balanceHelper(array, 0, array.length - 1);
    }

    // рекурсивная функция делит массив и каждый подмассив, полученный таким делением, пополам, вычленяя элемент посередине и записывая его в дерево
    // таким образом дерево собирается в сбалансированном виде, с количеством элементов в двух поддеревьях любого узла не отличающемся больше, чем на 1
    private void balanceHelper(T[] array, int left, int right) {
        int center = left + (right - left + 1) / 2;

        add(array[center]);

        if (left <= center - 1) {
            balanceHelper(array, left, center - 1);
        }

        if (right >= center + 1) {
            balanceHelper(array, center + 1, right);
        }
    }

    public void clear() {
        root = null;
    }

    private int compare(MyTreeNode<T> node1, MyTreeNode<T> node2) {
        //noinspection unchecked
        return ((Comparable<? super T>) node1.getData()).compareTo(node2.getData());
    }

    @Override
    public String toString() {
        if (root == null) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        traverseBreadthFirst(node -> stringBuilder.append(node.getData()).append(", "));

        int length = stringBuilder.length();

        return stringBuilder.replace(length - 2, length, "]").toString();
    }

    public String toStringDepthFirst() {
        if (root == null) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        traverseDepthFirst(node -> stringBuilder.append(node.getData()).append(", "));

        int length = stringBuilder.length();

        return stringBuilder.replace(length - 2, length, "]").toString();
    }

    public String toStringDepthFirstRecursive() {
        if (root == null) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        traverseDepthFirstRecursive(node -> stringBuilder.append(node.getData()).append(", "));

        int length = stringBuilder.length();

        return stringBuilder.replace(length - 2, length, "]").toString();
    }
}
package ru.academits.mamreyan.my_tree;

public class MyTreeNode<T> {
    private MyTreeNode<T> left;
    private MyTreeNode<T> right;
    private T data;

    public MyTreeNode(T data) {
        this.data = data;
    }

    public MyTreeNode(MyTreeNode<T> left, MyTreeNode<T> right) {
        this.left = left;
        this.right = right;
    }

    public MyTreeNode(MyTreeNode<T> left, MyTreeNode<T> right, T data) {
        this.left = left;
        this.right = right;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public MyTreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(MyTreeNode<T> left) {
        this.left = left;
    }

    public MyTreeNode<T> getRight() {
        return right;
    }

    public void setRight(MyTreeNode<T> right) {
        this.right = right;
    }

    public boolean hasLeft() {
        return left != null;
    }

    public boolean hasRight() {
        return right != null;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
package ru.academits.mamreyan.my_tree_main;

import ru.academits.mamreyan.my_tree.MyTree;
import ru.academits.mamreyan.my_tree.MyTreeNode;

public class Main {
    public static void main(String[] args) {
        MyTreeNode<Integer> left = new MyTreeNode<>(1);
        MyTreeNode<Integer> right = new MyTreeNode<>(3);
        MyTreeNode<Integer> root = new MyTreeNode<>(left, right, 2);
        MyTree<Integer> tree = new MyTree<>(root);

        System.out.println(tree);

        System.out.println(tree.compare(right, left));


    }
}
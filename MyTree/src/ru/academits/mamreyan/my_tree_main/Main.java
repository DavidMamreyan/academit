package ru.academits.mamreyan.my_tree_main;

import ru.academits.mamreyan.my_tree.MyTree;
import ru.academits.mamreyan.my_tree.MyTreeNode;

public class Main {
    public static void main(String[] args) {
        MyTree<Integer> tree = new MyTree<>();
        System.out.println(tree);
        System.out.println(tree.toStringDepthFirst());
        System.out.println(tree.toStringDepthFirstRecursive());

        tree = new MyTree<>(1);
        System.out.println(tree);

        MyTreeNode<Integer> left = new MyTreeNode<>(50);
        MyTreeNode<Integer> right = new MyTreeNode<>(150);
        MyTreeNode<Integer> root = new MyTreeNode<>(left, right, 100);
        tree = new MyTree<>(root);

        System.out.println(tree);

        System.out.println(tree.contains(100));
        System.out.println(tree.add(100));
        System.out.println(tree.contains(1000));
        System.out.println(tree.remove(1000));
        System.out.println(tree.get(1000));

        System.out.println(tree);

        tree.add(20);
        tree.add(80);
        tree.add(120);
        tree.add(180);
        tree.add(10);
        tree.add(9);
        tree.add(8);
        tree.add(7);
        tree.add(6);
        tree.add(5);
        tree.add(4);
        tree.add(11);
        tree.add(12);
        tree.add(13);
        tree.add(14);
        tree.add(15);
        tree.add(70);
        tree.add(110);
        tree.add(170);
        tree.add(30);
        tree.add(33);
        tree.add(90);
        tree.add(130);
        tree.add(190);
        tree.add(192);
        tree.add(140);
        tree.add(141);
        tree.add(142);

        System.out.println(tree.getSize());

        System.out.println(tree);
        System.out.println(tree.toStringDepthFirst());
        System.out.println(tree.toStringDepthFirstRecursive());

        tree.balance();

        System.out.println(tree);
        System.out.println(tree.toStringDepthFirst());
        System.out.println(tree.toStringDepthFirstRecursive());

    }
}
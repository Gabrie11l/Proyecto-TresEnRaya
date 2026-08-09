/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto.tda;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jared
 */
public class Node<T> {
    private T data;
    private List<Node<T>> children;

    public Node(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public void addChild(Node<T> child) {
        if (child != null) {
            this.children.add(child);
        }
    }

    public void addChild(T childData) {
        this.children.add(new Node<>(childData));
    }

    public boolean isLeaf() {
        return this.children.isEmpty();
    }
}
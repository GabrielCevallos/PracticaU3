package com.example.controller.tda.list;

import java.io.Serializable;
/* DATOS GENERICOS
T = TIPO 
E = ELEMENTOS EN UNA COLECCION DE DATOS 
HASHMAP = DICCIONARIO(CLAVE, VALOR)
K = KEY
V = VALUE  */
public class Node<E> implements Serializable{
    private E info;
    private Node<E> next;

    //CONSTRUCTOR
    public Node(E info) {
        this.info = info;
        this.next = null;
    }

    //CONSTRUCTOR NODO NEXT
    public Node(E info, Node<E> next) {
        this.info = info;
        this.next = next;
    }

    //GETTERS Y SETTERS
    public E getInfo() {
        return this.info;
    }

    public void setInfo(E info) {
        this.info = info;
    }

    public Node<E> getNext() {
        return this.next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

}
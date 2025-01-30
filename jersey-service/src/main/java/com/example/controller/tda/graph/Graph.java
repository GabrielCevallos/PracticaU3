package com.example.controller.tda.graph;

import com.example.controller.tda.list.LinkedList;

public abstract class Graph {
    public abstract Integer nroVertices();
    public abstract Integer nroEdges();
    public abstract Boolean isEdge(Integer v1, Integer v2) throws Exception;
    public abstract Float weightEdge(Integer v1, Integer v2) throws Exception;
    public abstract void addEdge(Integer v1, Integer v2) throws Exception;
    public abstract void addEdge(Integer v1, Integer v2, Float weight) throws Exception;
    public abstract LinkedList<Adyacencia> adyacencias(Integer v1);

    @Override
    public String toString() {
        String grafo = "";
        try {
            for (int i = 1; i <= this.nroVertices(); i++) {
                grafo += "V" + i + "\n";
                LinkedList<Adyacencia> lista = adyacencias(i);
                if (!lista.isEmpty()) {
                    Adyacencia[] ady = lista.toArray();
                    for(int j = 0; j < ady.length; j++) {
                        Adyacencia a = ady[j];
                        grafo += "ady " + "V" + a.getDestination() + "weight: " + a.getWeight() + "\n";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grafo;
    }
}

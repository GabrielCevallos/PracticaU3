package com.example.controller.tda.graph;

import com.example.controller.exception.LabelException;

public class GraphLabelNoDirect<E> extends GraphLabelDirect<E> {
    public GraphLabelNoDirect(Integer nroVertices, Class<E> clazz) {
        super(nroVertices, clazz);
    }

    public void insertEdgeLabel(E v1, E v2, Float weight) throws Exception {
        if(isLabeledGraph()) {
            addEdge(getVerticeLabel(v1), getVerticeLabel(v2), weight);
            addEdge(getVerticeLabel(v2), getVerticeLabel(v1), weight);
            setNroEdges(nroEdges() + 1);
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void addEdge(Integer v1, Integer v2) throws Exception {
        addEdge(v1, v2, Float.NaN);
    }
}

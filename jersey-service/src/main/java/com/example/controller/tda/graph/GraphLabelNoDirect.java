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
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }
}

package com.example.controller.tda.graph;

import com.example.models.Gimnasio;

public class TestTiempo {
    public static void main(String[] args) throws Exception {
        Integer nroDatos = 30; //10, 20, 30
        GraphLabelDirect<Gimnasio> grafo = new GraphLabelDirect<>(nroDatos, Gimnasio.class);
        for (int i = 1; i <= grafo.nroVertices(); i++) {
            grafo.labelsVertices(i, new Gimnasio());
        }
        grafo.edgeAllVertices();
        Long inicio = System.currentTimeMillis();
        grafo.bellmanF(7); //o cualquier otro numero dentro del rango de nroDatos
        //grafo.floydW();
        Long fin = System.currentTimeMillis();

        System.out.println("Tiempo de Ejecución del Algoritmo Floyd Warshall: " + (fin - inicio) + "ms");
    }
}

package com.example.controller.tda.graph;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.HashMap;

import com.example.controller.exception.LabelException;
import com.example.controller.tda.list.LinkedList;

@SuppressWarnings("unchecked")
public class GraphLabelDirect<E> extends GraphDirect {
    protected E labels [];
    protected HashMap<E, Integer> dictVertices;
    private Class<E> clazz;

    public GraphLabelDirect(Integer nroVertices, Class<E> clazz) {
        super(nroVertices);
        this.clazz = clazz;
        labels = (E[])Array.newInstance(clazz, nroVertices + 1);
        dictVertices = new HashMap<>();
    }

    public Boolean isEdgeLabel(E v1, E v2) throws Exception {
        if(isLabeledGraph()) {
            return isEdge(getVerticeLabel(v1), getVerticeLabel(v2));
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void insertEdgeLabel(E v1, E v2, Float weight) throws Exception {
        if(isLabeledGraph()) {
            addEdge(getVerticeLabel(v1), getVerticeLabel(v2), weight);
            addEdge(getVerticeLabel(v2), getVerticeLabel(v1), weight);
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void insertEdgeLabel(E v1, E v2) throws Exception {
        if(isLabeledGraph()) {
            insertEdgeLabel(v1, v2, Float.NaN);
            /* System.out.println(getVerticeLabel(v1) + "-----" + getVerticeLabel(v2));
            addEdge(getVerticeLabel(v1), getVerticeLabel(v2), Float.NaN); */
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public LinkedList<Adyacencia> adyacenciasLabel(E v) throws Exception {
        if(isLabeledGraph()) {
            return adyacencias(getVerticeLabel(v));
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void labelsVertices(Integer v, E data) {
        labels[v] = data;
        dictVertices.put(data, v);
    }

    public Boolean isLabeledGraph() {
        Boolean band = true;
        for (int i = 1; i < labels.length; i++) {
            if (labels[i] == null) {
                band = false;
                break;
            }
        }
        return band;
    }

    public Integer getVerticeLabel(E label) {
        return dictVertices.get(label);
    }

    public E getLabelL(Integer v1) {
        return labels[v1];
    }

    @Override
    public String toString() {
        String grafo = "";
        try {
            for (int i = 1; i <= this.nroVertices(); i++) {
                grafo += "V" + i + "[" + getLabelL(i).toString() + "]" + "\n";
                LinkedList<Adyacencia> lista = adyacencias(i);
                if (!lista.isEmpty()) {
                    Adyacencia[] ady = lista.toArray();
                    for(int j = 0; j < ady.length; j++) {
                        Adyacencia a = ady[j];
                        grafo += "ady " + "V" + a.getDestination() + "weight: " + a.getWeight() + "[" + getLabelL(a.getDestination()).toString() + "]" + "\n";
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return grafo;
    }

    public String drawGraph() {
        String grafo = "var nodes = new vis.DataSet([\n";
        
        try {
            for (int i = 1; i <= this.nroVertices(); i++) {
                grafo += "{ id: " + i + ", label: " + '"' + getLabelL(i).toString() + '"' + "}," + "\n";
                /* grafo += "V" + i + " [" +getLabelL(i).toString() + " ]" + "\n";
                LinkedList<Adyacencia> lista = adyacencias(i);
                if (!lista.isEmpty()) {
                    Adyacencia[] ady = lista.toArray();
                    for(int j = 0; j < ady.length; j++) {
                        Adyacencia a = ady[j];
                        grafo += "V" + i + " -> " + "V" + a.getDestination() + " [label = " + a.getWeight() + "]" + "\n";
                    }
                } */
            }
            grafo += "]);" + "\n";

            grafo += "var edges = new vis.DataSet([" + "\n";
            for (int i = 1; i <= this.nroVertices(); i++) {
                LinkedList<Adyacencia> lista = adyacencias(i);
                if (!lista.isEmpty()) {
                    Adyacencia[] ady = lista.toArray();
                    for(int j = 0; j < ady.length; j++) {
                        Adyacencia a = ady[j];
                        grafo += "{ from: " + i + ", to: " + a.getDestination() + "}," + "\n";
                        //grafo += "V" + i + " -> " + "V" + a.getDestination() + " [label = " + a.getWeight() + "]" + "\n";
                    }
                } 
            }
            grafo += "]);" + "\n";

            grafo += "var container = document.getElementById(\"mynetwork\");" + "\n";
            grafo += "var data = {" + "\n";
            grafo += "nodes: nodes," + "\n";
            grafo += "edges: edges," + "\n";
            grafo += "}," + "\n";
            grafo += "var options = {};" + "\n";
            grafo += "var network = new vis.Network(container, data, options);" + "\n";
            FileWriter file = new FileWriter("resources" + File.separatorChar + "graph" + File.separatorChar + "graph.js");
            file.write(grafo);
            file.flush();
            file.close();
        } catch (Exception e) { 
            e.getMessage();
            e.printStackTrace();
        }
        return grafo;
    }

    private Double calcularDistanciaPuntosGeograficos(Double latitud1, Double longitud1, Double latitud2, Double longitud2) {
        final Double earthRadius = 6378.10;
        
        Double distancia = 2 * earthRadius * Math.asin(Math.sqrt(Math.pow(Math.sin((latitud2 - latitud1) / 2), 2) + Math.cos(latitud1) * Math.cos(latitud2) * Math.pow(Math.sin((longitud2 - longitud1) / 2), 2)));
        return distancia;

    }
}

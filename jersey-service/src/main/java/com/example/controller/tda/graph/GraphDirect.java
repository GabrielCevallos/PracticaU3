package com.example.controller.tda.graph;

import com.example.controller.exception.OverFlowException;
import com.example.controller.tda.list.LinkedList;

@SuppressWarnings("unchecked")
public class GraphDirect extends Graph {
    private Integer nroVertices;
    private Integer nroEdges;
    private LinkedList<Adyacencia> listAdyacencias[];

    public GraphDirect(Integer nroVertices) {
        this.nroVertices = nroVertices;
        this.nroEdges = 0;
        this.listAdyacencias = new LinkedList[nroVertices + 1];
        for (int i = 1; i <= nroVertices; i++) {
            listAdyacencias[i] = new LinkedList<>();
        }
    }

    public Integer nroEdges() {
        return this.nroEdges;
    }

    public Integer nroVertices() {
        return this.nroVertices;
    }
    
    public Boolean isEdge(Integer v1, Integer v2) throws Exception {
        Boolean band = false;
        if(v1.intValue() <= nroVertices && v2.intValue() <= nroVertices) {
            LinkedList<Adyacencia> lista = listAdyacencias[v1];
            if(!lista.isEmpty()) {
                Adyacencia[] matrix = lista.toArray();
                for(int i = 0; i < matrix.length; i++) {
                    Adyacencia aux = matrix[i];
                    if(aux.getDestination().intValue() == v2.intValue()) {
                        band = true;
                        break;
                    }
                }
            }
        } else {
            throw new OverFlowException("Los vértices están fuera de rango");
        }
        return band;
    }

    public Float weightEdge(Integer v1, Integer v2) throws Exception {
        Float weight = Float.NaN;
        if(isEdge(v1, v2)) {
            LinkedList<Adyacencia> lista = listAdyacencias[v1];
            if(!lista.isEmpty()) {
                Adyacencia [] matrix = lista.toArray();
                for(int i = 0; i < matrix.length; i++) {
                    Adyacencia aux = matrix[i];
                    if(aux.getDestination().intValue() == v2.intValue()) {
                        weight = aux.getWeight();
                        break;
                    }
                }
            }
        }
        return weight;
    }

    public void addEdge(Integer v1, Integer v2, Float weight) throws Exception {
        if(v1.intValue() <= nroVertices && v2.intValue() <= nroVertices) {
            if(!isEdge(v1, v2)) {
                nroEdges++;
                Adyacencia aux = new Adyacencia();
                aux.setWeight(weight);
                aux.setDestination(v2);
                listAdyacencias[v1].add(aux);
            }
        } else {
            throw new OverFlowException("Los vértices están fuera de rango");
        }
    }

    public void addEdge(Integer v1, Integer v2) throws Exception {
        this.addEdge(v1, v2, Float.NaN);
    }

    public LinkedList<Adyacencia> adyacencias(Integer v1) {
        return listAdyacencias[v1];
    }

    public LinkedList<Adyacencia>[] getListAdyacencias() {
        return this.listAdyacencias;
    }

    /* public void setListAdyacencias[](LinkedList<Adyacencia> listAdyacencias[]) {
        this.listAdyacencias[] = listAdyacencias[];
    } */
   
    public void setNroEdges(Integer nroEdges) {
        this.nroEdges = nroEdges;
    }
}

package com.example.controller.tda.graph;

public class GraphNoDirect extends GraphDirect {
    public GraphNoDirect(Integer nroVertices) {
        super(nroVertices);
    }

    //v1 v2  v3  v4
    //[1 Ady v1, 2 Ady v2, 3 Ady v3, 4 Ady v4]
    //[Listas Enlazadas]
    
    public void addEdge(Integer v1, Integer v2, Float weight) throws Exception {
        if(v1.intValue() <= nroVertices() && v2.intValue() <= nroVertices()) {
            if(!isEdge(v1, v2)) {
                //nroEdges++;
                setNroEdges(nroEdges() + 1);

                Adyacencia aux = new Adyacencia();
                aux.setWeight(weight);
                aux.setDestination(v2);
                getListAdyacencias()[v1].add(aux);

                Adyacencia auxD = new Adyacencia();
                auxD.setWeight(weight);
                auxD.setDestination(v1);
                getListAdyacencias()[v2].add(auxD);
            }
        } else {
            throw new Exception("Los vértices están fuera de rango");
        }
    }

}

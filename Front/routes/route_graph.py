from .route import *
import folium # type: ignore
router_graph = Blueprint('router_graph', __name__)

import requests
import folium
from flask import render_template
from flask import Blueprint

router_graph = Blueprint('router_graph', __name__)


import requests
import folium
from flask import render_template
from flask import Blueprint
from folium.plugins import MarkerCluster

router_graph = Blueprint('router_graph', __name__)

@router_graph.route('/graph')
def graph(msg=''):
    r = requests.get('http://localhost:8081/api/gym/list')
    gyms = r.json()["data"]

    for i, gym in enumerate(gyms, start=1):
        gym['numero'] = i

    ubicacion = (gyms[0]['latitud'], gyms[0]['longitud'])
    mapa = folium.Map(location=ubicacion, zoom_start=15)

    marker_cluster = MarkerCluster().add_to(mapa)

    for gym in gyms:
        popup_html = f"""
        <b>{gym['nombre']}</b><br>
        {gym['descripcion']}<br>
        Latitud: {gym['latitud']}, Longitud: {gym['longitud']}<br>Rating: {gym['nroEstrellas']}
        """
        folium.Marker(
            location=(gym['latitud'], gym['longitud']),
            popup=folium.Popup(popup_html, max_width=300),
            tooltip=gym['nombre']  # Texto flotante al pasar el mouse
        ).add_to(marker_cluster)

    mapa.save("templates/fragmento/graph/mapa.html")
    return render_template('fragmento/graph/mapa.html', gyms=gyms)

from .route import *
import folium # type: ignore
router_graph = Blueprint('router_graph', __name__)

@router_graph.route('/graph')
def graph(msg=''):
    r = requests.get('http://localhost:8081/api/gym/list')
    print(r.json())
    gyms = r.json()["data"]
    i = 1
    for gym in gyms:
        gym['numero'] = i
        i+=1


    ubicacion = (gyms[0]['latitud'], gyms[0]['longitud'])
    print(ubicacion)
    mapa = folium.Map(location=ubicacion, zoom_start=15)
    folium.Marker(location=ubicacion, popup=gyms[0]).add_to(mapa)
    mapa.save("templates/fragmento/graph/mapa.html")
    return render_template('fragmento/graph/mapa.html', gyms = gyms)

""" 
@router_graph.route('/gym/register')
def view_register_graph():
    return render_template('fragmento/graph/registro.html')


@router_graph.route('/gym/save', methods=['POST'])
def save_graph():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {
        "nombre": form['nom'],
        "descripcion": form['desc'],
        "latitud": form['lat'],
        "longitud": form['long'],
        "nroEstrellas": form['nroStars'],
    }

    r = requests.post("http://localhost:8081/api/gym/save", data=json.dumps(dataF), headers=headers)
    print(r.json())
    dat = r.json()
    if r.status_cde == 200:
        flash("¡Se ha guardado correctamente!", category='info')
        return redirect("/gym/list")
    else:
        flash(str(dat["data"]), category='error')
        return redirect("/gym/list")
    

@router_graph.route('/gym/edit/<id>')
def view_edit_graph(id):
    r = requests.get("http://localhost:8081/api/gym/get/"+id)
    print(r.json())
    data = r.json()["data"]
    if(r.status_code == 200):
        return render_template('fragmento/graph/editar.html', gym = data)
    else:
        flash(data, category='error')
        return redirect("/gym/list")


@router_graph.route('/gym/update', methods=['POST'])
def update_graph():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {
        "id": form['idGym'],
        "nombre": form['nom'],
        "descripcion": form['desc'],
        "latitud": form['lat'],
        "longitud": form['long'],
        "nroEstrellas": form['nroStars'],
    }

    r = requests.post("http://localhost:8081/api/gym/update", data=json.dumps(dataF), headers=headers)
    print(r.json())
    dat = r.json()
    if r.status_code == 200:
        flash("¡Se ha actualizado correctamente!", category='info')
        return redirect("/gym/list")
    else:
        flash(str(dat["data"]), category='error')
        return redirect("/gym/list")
    

@router_graph.route('/gym/delete/<id>')
def view_delete_graph(id):
    return render_template('fragmento/graph/eliminar.html', id=id)


@router_graph.route('/gym/remove', methods=['POST'])
def delete_graph():
    form = request.form
    r = requests.post("http://localhost:8081/api/gym/delete/"+form['idGym'])
    print(r.json())
    if r.status_code == 200:
        flash("¡Se ha eliminado correctamente!", category='info')
        return redirect("/gym/list")
    else:
        flash("¡No se ha podido eliminar!", category='error')
        return redirect("/gym/list") """
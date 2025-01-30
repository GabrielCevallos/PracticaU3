from .route import *
router_gym = Blueprint('router_gym', __name__)

@router_gym.route('/gym/list')
def list_gym(msg=''):
    r = requests.get('http://localhost:8081/api/gym/list')
    print(r.json())
    gyms = r.json()["data"]
    i = 1
    for gym in gyms:
        gym['numero'] = i
        i+=1
    return render_template('fragmento/gym/lista.html', gyms = gyms)


@router_gym.route('/gym/register')
def view_register_gym():
    return render_template('fragmento/gym/registro.html')


@router_gym.route('/gym/save', methods=['POST'])
def save_gym():
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
    if r.status_code == 200:
        flash("¡Se ha guardado correctamente!", category='info')
        return redirect("/gym/list")
    else:
        flash(str(dat["data"]), category='error')
        return redirect("/gym/list")
    

@router_gym.route('/gym/edit/<id>')
def view_edit_gym(id):
    r = requests.get("http://localhost:8081/api/gym/get/"+id)
    print(r.json())
    data = r.json()["data"]
    if(r.status_code == 200):
        return render_template('fragmento/gym/editar.html', gym = data)
    else:
        flash(data, category='error')
        return redirect("/gym/list")


@router_gym.route('/gym/update', methods=['POST'])
def update_gym():
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
    

@router_gym.route('/gym/delete/<id>')
def view_delete_gym(id):
    return render_template('fragmento/gym/eliminar.html', id=id)


@router_gym.route('/gym/remove', methods=['POST'])
def delete_gym():
    form = request.form
    r = requests.post("http://localhost:8081/api/gym/delete/"+form['idGym'])
    print(r.json())
    if r.status_code == 200:
        flash("¡Se ha eliminado correctamente!", category='info')
        return redirect("/gym/list")
    else:
        flash("¡No se ha podido eliminar!", category='error')
        return redirect("/gym/list")
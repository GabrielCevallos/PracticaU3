from app import create_app
#from flask import session

app = create_app()
if __name__ == "__main__":
    app.secret_key = 'feirnnr2024'  #secret key for session
    app.config['SESSION_TYPE'] = 'filesystem'   #session type
    app.run(debug=True, host='0.0.0.0', port='5050')     #debug = developer mode    #cambiar puerto pq ya esta en uso
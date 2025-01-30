from flask import Flask 

def create_app():
    app = Flask(__name__, instance_relative_config=False)
    
    with app.app_context():
        from routes.route import router
        from routes.route_gym import router_gym
        from routes.route_graph import router_graph
        app.register_blueprint(router)
        app.register_blueprint(router_gym)
        app.register_blueprint(router_graph)
    return app
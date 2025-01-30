from flask import Blueprint, abort, render_template, redirect, flash, request, url_for
import json
import requests
router = Blueprint('router', __name__)

@router.route('/')
def home():
    return render_template('fragmento/inicial/inicio.html') 
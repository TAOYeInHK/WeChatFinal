__author__ = 'ty'
from login import app, lm
from model import User
from flask import request, render_template, jsonify, abort, make_response
from flask_login import login_required
from controller import LoginController
import json


@lm.user_loader
def load_user(account):
    return User.query.filter(User.account == account).first()

@app.errorhandler(401)
def illegal():
    return make_response(jsonify({'error': 'Wrong account or password'}), 401)

@app.route('/', methods=['GET'])
def test():
    return render_template("login.html")

@app.route('/', methods=['POST'])
def login():
    account = request.form['account']
    password = request.form['password']
    control = LoginController(account, password)
    if control.authenticate():
        userInfo = {}
        userInfo["account"] = account
        userInfo["password"] = password
        return jsonify(userInfo)
    else:
        abort(401)

@app.route('/welcome', methods=['GET'])
@login_required
def welcome():
    pass


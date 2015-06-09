__author__ = 'ty'
from flask import Flask
from flask_login import LoginManager
from flask_sqlalchemy import SQLAlchemy


app = Flask(__name__)


lm = LoginManager()
lm.setup_app(app)
app.config.from_object('login.config.BaseConfig')
db = SQLAlchemy(app)

import login.view

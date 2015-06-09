__author__ = 'ty'
from passlib.apps import custom_app_context as pwd_context

from flask_login import UserMixin
from login import db


class User(db.Model, UserMixin):
    __tablename__ = 'user'
    user_id = db.Column(db.Integer, primary_key=True)
    account = db.Column(db.String(20))
    password = db.Column(db.String(100))

    def __init__(self, account, password):
        self.account = account
        self.password = pwd_context.encrypt(password)

    def is_anonymous(self):
        return False

    def get_id(self):
        return self.user_id

    def is_authenticated(self):
        return True

    def is_active(self):
        return True


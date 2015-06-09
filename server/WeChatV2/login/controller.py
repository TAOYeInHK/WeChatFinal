__author__ = 'ty'
from passlib.apps import custom_app_context as pwd_context
from login.model import User
from flask_login import login_user
from login import db


class LoginController(object):
    def __init__(self, account, password):
        self.account = account
        self.password = password

    def authenticate(self):
        user = User.query.filter(User.account == self.account).first()
        if user is not None and pwd_context.verify(self.password, user.password):
            login_user(user)
            return True
        else:
            return False

def AddUser(account, password):
    user = User(account, password)
    db.session.add(user)
    db.session.commit()



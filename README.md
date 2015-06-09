# WeChatFinal
The client folder contains a simple android app which consists two activities. The first activity is a login page where user
can input account and password. After authentication, the app will enter a welcome page where user's information will show.

The server folder contains a flask server which handle the login operation. If the authentication successes, a json string which
contains account and password will be returned to the android client. Otherwise, it will return a 401 error message.

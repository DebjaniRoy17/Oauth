# Oauth
This application demonstrates how OAuth can be used for Grant Type = authorization_code
1) "Oauth" is the resource application.
2) "Oauth-client" is the client application.

Oauth will display the list of Employees only when it gets approval from the Oauth-client application.
For the approval to happen, a code is shared (since the grant type is authorization_code) between the Resource server and the client server.

When the user clicks on "Get Employee Info" the user is redirected to a login page. This login page validates the user with the help of user id and password. 
If the validated, then the code is shared to the resource server and employee list is displayed.

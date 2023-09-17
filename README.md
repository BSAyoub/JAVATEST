# JAVATEST
Create 2 REST services: one that allows to register a user and the other one that displays the details of an registered user.


# tester l’enregistrement d’un utilisateur sans renseigner le mot de passe

Méthode	Post
Url	http://localhost:8080/users/register

# récupérer les détails d’un utilisateur adulte et il habite en France 

Méthode	Get
Url	http://localhost:8080/users/xxxxx

# récupérer les détails d’un utilisateur qui n’existe pas
Méthode	Get
Url	http://localhost:8080/users/xxxxxnotexist

# récupérer les détails de tous les utilisateurs
Méthode	Get
Url	http://localhost:8080/users/users

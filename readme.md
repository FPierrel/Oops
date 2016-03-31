# Manuel d'installation du projet Oops:

## Installation et configuration du serveur d'application
### Installation du serveur glassfish
```sh
$ wget http://download.java.net/glassfish/4.1.1/release/glassfish-4.1.1.zip
$ unzip glassfish-4.1.1.zip ~/glassfish
$ cd ~/glassfish/glassfish4/bin
$ ./asadmin start-domain domain1
#### Activation de l'administration à distance : ###
$ ./asadmin change-admin-password
$ ./asadmin enable-secure-admin
$ ./asadmin restart-domain domain1
```
### Démarrage de la base de données
```sh
$ cd ~/glassfish/glassfish4/bin
$ ./asadmin start-database
```

### Création de la base de données
```sh
    $ cd ~/glassfish/glassfish4/javadb/bin
    $ ./ij
    ij> connect 'jdbc:derby://localhost:1527/OOPS;create=true;user=OOPS;password=OOPS;';
```
### Configuration des ressources JDBC de glassfish
- Se rendre sur la page d'administration (http://localhost:4848)  \
- Sur la page Resources cliquer sur le bouton Add Resources \
- Charger le fichier Oops-ear/src/main/setup/glassfish-resources.xml

### Paramétrage de glassfish pour l'authentification des utilisateurs
Se rendre sur la page Configuration->server-config->Security->Realms \
Ajouter un realms: \
- Name: oops_realm
- Class Name: com.sun.enterprise.security.auth.realm.jdbc.JDBCRealm
- JAAS Context : jdbcRealm
- JNDI: jdbc/oops
- User Table: UTILISATEUR
- User Name Column: LOGIN
- Password Column: MOTDEPASSE
- Group Table: UTILISATEUR
- Group Name Column: GROUPE
- Password Encryption Algorithm: SHA-256
- Database User: OOPS
- Database Password: OOPS
- Digest Algorithm: SHA-256
- Charset: UTF-8

## Build de l'application
Le serveur d'application est maintenant correctement configuré pour deployer l'application
Pour générer les packages:  
```sh
$ mvn clean install -DskipTests=true
```
## Deploiement de l'application 
Le deploiement peut s'effectuer soit à partir d'un IDE soit avec maven et le plugin Cargo. Pour déployer l'application avec maven il faut modifier le fichier pom.xml pour enregistrer les paramètres du serveur: \
Dans la partie <profils>, créer ou modifier un profil pour enregistrer les parametres.

Exemple de commande pour deployer l'application avec les paramètres du profil local:
```sh
$ mvn -pl Oops-web package cargo:redeploy -DskipTests=true -P local
```
## Lancement des tests
Pour pouvoir lancer tous les tests l'application doit être deployée (pour les tests selenium) et il est nécessaire d'installer phantomjs, il s'agit d'une application permettant de simuler un navigateur web. La procédure d'installation se situe sur cette page: http://phantomjs.org/build.html

Commande pour lancer les tests : 
```sh
$ mvn test -DglassfishHost=http://localhost:8080/Oops-web
```    
Le paramétre -DglassfishHost sert à indiquer l'adresse où l'application est déployer, cette adresse est utilisée pour les tests selenium.

## Analyse du code
Si un serveur SonarQube est installer en local il est possible de lancer une analyse avec la commande. L'adresse et le port du serveur SonarQube sont configurés dans le fichier /conf/settings.xml
```sh
$ mvn clean install sonar:sonar -DskipTests=true
``



# Groupe03

Projet Java - Groupe03

# ClapetteChaussette

ClapetteChaussette est un logiciel de gestion de quizz en temps réel. Un professeur peut créer des quizz comportant trois types de questions — QCM, question libre et sondage — et piloter une session live depuis son interface : lancement des questions, passage à la suivante et affichage des résultats. Les participants rejoignent la session depuis leur propre machine et répondent en direct.

## Démonstration

[Voir la vidéo de démonstration](https://www.youtube.com/watch?v=PLACEHOLDER)

## Compte de démonstration

| Champ        | Valeur    |
| ------------ | --------- |
| Utilisateur  | `pluquet` |
| Mot de passe | `pluquet` |

Un quizz de démonstration est disponible directement après connexion.

## Configuration

### Base de données

Les identifiants de connexion à la base de données sont à renseigner dans le fichier `.env` à la racine du projet :

```env
DATABASENAME=
DATABASEUSER=
DATABASEPASSWORD=
DATABASEHOST=
DATABASEPORT=
```

### Serveur

L'adresse IP et le port du serveur sont à configurer dans le fichier `config.txt` :

```
serverHost=
serverPort=
```

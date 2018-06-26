# Programme TV Utils
# *Develop by Hagbuck, Drainman & Grimnir*


# Sommaire

<!--ts-->
   * [Programme TV Utils](#programme-tv-utils)
   * [Présentation](#présentation)
      * [Introduction](#introduction)
      * [Technologie](#technologie)
      * [Fonctions principales](#fonctions-principales)
   * [Utilisation](#utilisation)
      * [Utiliser le JAR](#utiliser-le-jar)
      * [Altérnative](#altérnative)
   * [Guide des commandes](#guide-des-commandes)

<!-- Added by: kurai, at: 2018-06-26T22:48+02:00 -->

<!--te-->

# Présentation

## Introduction

Cette application est un projet d'étude réalisé dans le cadre de notre formation à Polytech Paris Sud (Année 2017-2018).
Il s'appuie sur un fichier XML en provenance de l'API de Télérama pour proposer diverses fontionnalité de navigation dans le catalogue des chaînes et émissions contenues.

## Technologie

Le fichier d'entrée est un fichier XML et l'application est réalisée en Java afin de le parser et de proposer un interface console à l'utilisateur.

## Fonctions principales
* Consulter la liste des chaı̂nes.
* Consulter la liste des jours disposant de programmes télé.
* Consulter la programmation d’une chaı̂ne pour un jour donné.
* Consulter la fiche d’une émission (avec un affichage adapté à son type).
* Consulter la liste des émissions qui seront en cours de diffusion à un moment donné.
* Consulter la liste des films concernant un réalisateur ou un acteur.
* Consulter la liste des acteurs et réalisateurs.
* Afficher le nombre d'émission par type.
* Fonction de recherche par mot clé.

# Utilisation

## Utiliser le JAR
* **OS Support :** Windows, Linux.
* **Requierments :** Java.
* Naviguez dans le projet dans le répertoire "**[PATH_APPLICATION]/ressource/**"
* Entrez "**java -jar runMe.jar**"

## Solution Altérnative
* Importer le projet dans Eclipse.
* Ouvrez le projet et localiser dans le package "main" la classe "**main.java**".
* "**Run**" le main.

# Guide des commandes
* **'chan'** ~ *Donne la liste des chaînes connues par le système.*
* **'chan - [NAME]'** ~ *Affiche la programmation de la chaîne [NAME]*
* **'chan - [NAME] - dd/MM/YYYY'**~ *Donne la programmation de la chaîne [NAME] à la date donnée.*
* **'chan - [NAME] - [DATE] - [ID]'** ~ *Affiche la fiche de l'émission [ID] appartenant à la chaine [NAME] pour la date [DATE].*
* **'rightnow'** ~ *Donne les émissions en cours actuellement. (Heure du PC)*
* **'rightnow - dd/MM/YYYY:hh:mm'** ~ *Donne les émissions en cours à la date [DATE].*
* **'when'** ~ *Donne les jours ayant une programmation et connue par le système.*
* **'emission_by_type'** ~ *Donne les types d'émissions triées par type.*
* **'actor'** ~ *Donne la liste des acteurs connus par le système.*
* **'actor - [NAME]'** ~ *Donne la liste des émissions dans lesquelles joue l'acteur [NAME].*
* **'actor - [ID] - [NAME] '** ~ *Affiche la fiche de l'émission [ID] de l'acteur [NAME].*
* **'director'** ~ *Donne la liste des directeurs connus par le système.*
* **'director - [NAME]'** ~ *Donne la liste des émissions dirigées par le directeur [NAME].*
* **'director - [ID] - [NAME]'** ~ *Affiche la fiche de l'émission [ID] du directeur [NAME].*
* **'search - [WORDS]'** ~ *Recherche la correspondance entre les mots [WORDS] et les émissions connues.*
* **'test'** ~ *Affichage de test de l'application.*
* **'exit'** ~ *Quitte l'application.*
* **'deathApp'** ~ *Supprime l'application et toutes ses données. **NE PAS UTILISER !!***

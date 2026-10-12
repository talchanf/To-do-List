# To-do list (Java)

Application de bureau pour gérer une liste de tâches, avec une interface graphique Swing. Les tâches sont conservées d'une session à l'autre.

## Fonctionnalités

- Ajouter une tâche (bouton **Ajouter** ou touche Entrée)
- Marquer la tâche sélectionnée comme faite ou non faite (`[x]` / `[ ]`)
- Supprimer une tâche
- Sauvegarde automatique dans un fichier `taches.txt`, rechargé au prochain lancement

## Technologies

Java, Swing (`JFrame`, `JList`, `DefaultListModel`), lecture et écriture de fichiers avec `java.nio`.

## Compiler et lancer

```
javac TodoList.java
java TodoList
```

Le fichier `taches.txt` est créé dans le dossier d'où tu lances le programme.

## Ce que j'ai travaillé

- Séparer les données (le modèle de liste) de leur affichage.
- Créer l'interface sur le thread graphique de Swing.
- Sauvegarder et recharger l'état de l'application.

## Idées d'amélioration

- Modifier le texte d'une tâche existante.
- Trier ou filtrer les tâches (faites, à faire).

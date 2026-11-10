# CobblemonGo Launcher

> Un lanceur de jeu qui permet de jouer à Minecraft avec son modpack Cobblemon en **un seul clic**, sans aucune connaissance technique.

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white)
![Compose Desktop](https://img.shields.io/badge/Compose_Desktop-4285F4?logo=jetpackcompose&logoColor=white)
![Java 25](https://img.shields.io/badge/Java-25-ED8B00?logo=openjdk&logoColor=white)

---

## 1. Contexte

### Minecraft en quelques mots
**Minecraft** est un jeu vidéo « bac à sable » : le joueur évolue dans un monde en cubes, qu'il explore, construit et transforme. C'est le jeu le plus vendu de l'histoire, et l'un des plus modifiés par sa communauté.

### Qu'est-ce qu'un mod, un modpack, un serveur ?
| Terme | Explication simple |
|---|---|
| **Mod** | Une extension créée par la communauté qui ajoute du contenu au jeu (nouvelles créatures, objets, règles…). |
| **Modpack** | Un « pack » de plusieurs mods choisis pour fonctionner ensemble. |
| **Cobblemon** | Un mod très populaire qui ajoute des créatures de type Pokémon dans Minecraft. |
| **Serveur** | Un ordinateur toujours allumé sur lequel plusieurs joueurs se retrouvent dans le même monde. |
| **Launcher** | Le programme qui prépare et démarre le jeu. C'est le sujet de ce projet. |

### Le problème que ce projet résout
Pour jouer à Minecraft avec des mods, un joueur débutant doit aujourd'hui :

1. Installer la bonne version de Java ;
2. Installer un chargeur de mods (*mod loader*) compatible ;
3. Télécharger à la main des dizaines de fichiers de mods, en vérifiant leurs versions ;
4. Les placer dans les bons dossiers ;
5. Se connecter au serveur avec la bonne adresse.

Une seule erreur (mauvaise version, fichier manquant…) et le jeu ne démarre pas, sans message compréhensible. Pour un public non initié, c'est un vrai obstacle.

---

## 2. Le but du projet

**CobblemonGo Launcher** automatise toute cette préparation. L'utilisateur :

1. Ouvre l'application ;
2. Clique sur **Jouer** ;
3. Le launcher s'occupe du reste : il vérifie l'installation, télécharge ce qui manque ou ce qui est obsolète, puis lance le jeu.

L'objectif est qu'une personne qui n'a **jamais** installé un mod puisse rejoindre l'aventure sans aide extérieure.

### Public visé
- Les joueurs débutants, qui n'ont aucune connaissance technique ;
- Les joueurs d'un même serveur, qui doivent tous avoir exactement la même configuration.

---

## 3. Technologies utilisées

| Technologie | Rôle dans le projet |
|---|---|
| **Kotlin** | Langage principal de l'application. |
| **Compose Desktop** (Jetpack Compose / Compose Multiplatform) | Création de l'interface graphique de bureau, de façon déclarative. |
| **Java 25** | Version du JDK sur laquelle tourne l'application. |
| **Gradle** | Compilation et gestion des dépendances du projet. |
| **Claude Code** (Anthropic) | Outil d'IA utilisé pour réaliser le prototype de design (voir ci-dessous). |
| **Git / GitHub** | Gestion de versions et hébergement du code. |

> Le code source est organisé sous le package `dev.iln.cobblemongo.launcher`.

### Choix d'utiliser des bibliothèques
Le projet s'appuie sur des **bibliothèques existantes** (open source) plutôt que de tout recoder à la main. C'est un choix volontaire : éviter de « réinventer la roue » pour des problèmes déjà bien résolus (lancement du jeu ou le téléchargement des fichiers requis pour le jeu), gagner du temps et consacrer l'effort à ce qui fait la spécificité du launcher : son fonctionnement et son expérience utilisateur. Cela reflète aussi une pratique courante en développement logiciel.

---

## 4. Fonctionnement général

```
Utilisateur ──► Clic sur « Jouer »
                    │
                    ▼
     Requête HTTP vers l'API (récupération des informations)
                    │
                    ▼
   Comparaison avec les fichiers locaux de l'utilisateur
                    │
        ┌───────────┴───────────┐
        ▼                       ▼
  Tout est à jour        Fichiers manquants
        │                  ou obsolètes
        │                       │
        │                 Téléchargement
        │                       │
        └───────────┬───────────┘
                    ▼
            Lancement de Minecraft
```

---

## 5. Notions informatiques utilisées

| Notion | Utilisation dans le projet |
|---|---|
| **Requêtes HTTP / API** | Le launcher interroge une API distante pour récupérer les informations dont il a besoin. Il communique avec elle par des requêtes HTTP et exploite les données reçues. |
| **Gestion des fichiers locaux** | Lecture, écriture et organisation des fichiers sur l'ordinateur de l'utilisateur : le launcher vérifie ce qui est déjà présent et gère les dossiers nécessaires au jeu. |
| **Mise en mémoire** | Les données récupérées ou calculées sont conservées en mémoire pour éviter de les redemander inutilement et pour que l'interface reste réactive. |

---

## Auteur

**Ilian Ratmia** — Étudiant en informatique

---

## Avertissement

Minecraft est une marque de Mojang Studios / Microsoft.
Cobblemon est un projet communautaire indépendant. Ce projet n'est affilié à aucune de ces entités.

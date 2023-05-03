# Devis_Batiment_JavaFX

Projet de programme Java sur le calcul d'un devis de revêtements d'un bâtiment. STH1, Semestre 2, INSA Strasbourg.

Version 1.2.0 (version avec interface graphique)

Pour actualiser le projet: Team/Remote/Pull to Uptstream
Pour sauvegarder les modifications sur le GitHub : Team/commit puis Team/Remote/Push to Upstream 


## Problèmes

- Implémentation/ Changements pour le système de création de bâtiment avec le choix parmi immeuble et maison
- ne trouve pas la méthode appliquerrevetement() dans les autres classes suite à l'implémentation de la méthode dans la classe Revetement.(syntaxe privilégié : Revetement.Appliquerrevetement())
- Classe Bâtiment à finir
- Classe Devis à continuer (comprendre comment écrire dans un fichier xls)

## Choses à ajouter

- [ ] Créer une fenêtre de création de niveau : choix de la hauteur sous plafond, bouton annuler, bouton ok.
- [ ]	Double clique sur un niveau : fenêtre pour modifier la hauteur sous plafond
- [ ]	Ajouter un canvas à chaque tab (niveau) + grille de dessin
- [ ]	Méthode pour dessiner un point
- [ ]	Méthode pour placer le point sur des valeurs « intelligentes »
-	...
- [ ]	Garde-fou sur la création de pièces en dehors de la surface du niveau inférieur

- [x] Afficher les liste des objets appartenant à un niveau
- [x] Implémenter une compatibilité pour les revêtements : filtre en fonction de la surface sur laquelle on souhaite appliquer?
- [ ] Classe Enregistrement
- [ ] Classe d'Importation
- [ ] Méthode SurfaceExterieur
- [ ] Système de suppression auto OU Règles de suppression (ex: coin non supprimable tant qu'il constitue à un mur)
- [ ] Contrôles de saisie
    - [x] Empêcher deux murs de se traverser/ couper
    - [x] Limiter le nombre d'ouvertures dans une surface
- [ ] Implementer l'écriture d'un fichier Excel via GemBox.Spreadsheet:

https://www.gemboxsoftware.com/spreadsheet-java/examples/java-export-to-excel-template/403

https://github.com/GemBoxLtd/GemBox.Spreadsheet.Java.Examples/blob/master/src/common-uses/template-use/src/main/java/Program.java

## Auteurs


- [@Evan Trenchant](https://github.com/EvanTrenchant)
- [@Etienne Rey](https://github.com/erey01)
- [@Rudy Virquin](https://github.com/Lypris)

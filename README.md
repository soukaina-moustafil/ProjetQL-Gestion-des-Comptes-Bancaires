# Auteur :  ABOUBAKAR AHAMADA								 
# Date : 08/05/2020											 
# Lieu : Casablanca											 

# Projet :
Une application web de gestion des comptes bancaires en Java/ JEE et Spring.	

# Démo 
- Pour voir une démo sur l'évolution de l'application en image, visitez la page wiki :
 https://github.com/AboubakarAhamada/Gestion-des-comptes-bancaires/wiki 
						 
# Spécificités fonctionnelles :
- Chaque compte appaertient à un client.
- Un compte bancaire peut être un compte courant ou un compte epargne.
- L'application doit permettre de :

	0. Crée un compte
	1. Consulter le compte d'un client
	2. Effectuer un versement
	3. Effectuer un retrait et dans ce cas le motant à retirer ne doit pas depasser le solde du compte. 
	4. Effectuer un virement d'un compte vers un autre compte. Il faut encore verifier le montant pour le virement
	5. Consulter les opérations d'un compte 

# Specificité techniques : 
- Pour la consultation des opération, les opérations doivent
 s'afficher dans des pages. On doit utiliser la pagination, c'est à dire qu'on ne veut pas afficher
 toutes les opérationd sur la meme page. 
- L'application doit être sécurisée. Seuls les personnes identifiées qui sont autorisées à 
effectuer les opérations ci-dessus mentionnées.
- L'application doit gérer les erreurs liées à l'utilisateur
- L'application doit être responsive pour différentes terminaux

# Architeure de l'application 
- L'application est basée sur une architecutre en couche à savoir :
 1. Une couche DAO pour l'accès aux données. Ici on crée les interfaces ClientRepository,
 CompteRepository et OperationRepository qui heritent de l'interface JpaRepository.
 2. Une couche Entities où on crée nos entités (classes) que seront mappées avec les tables dans la base de données
 3.Une couche metier où on crée une interface IbanqueMetier qui définit les opérations ci-dessous mentionnées et 
 une classe IbanqueMetierImpl qui implemente cette classe
 4. Une couche web (présentation) où on gère tout ce qui est web (View)
 
 # Technologies utilisées :
 - Java
 - Spring Boot, Spring Data JPA, Spring Security
 - View (UI) : Thymeleaf, JavaScript, Bootstrap3
 - SGBD : MySQL
 
 # Outis de développement: 
 - IDE : Eclipse 
 - Gestion des dépendences : Maven

# Comment utiliser cette application ?
Pour utiliser cette application, il suffit de modifier le fichier application.properties pour changer 
le nom de la base de donnée, le username et mettre les votres.




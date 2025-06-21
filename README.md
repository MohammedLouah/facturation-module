📋 Module de Facturation - Spring Boot & H2 (File Mode)

Un système complet de gestion de factures avec clients, calculs automatiques et export JSON.

✨ Fonctionnalités
    🧑‍ Gestion clients (CRUD)
    🧾 Gestion factures avec calculs auto (HT/TVA/TTC)
    📤 Export JSON des factures
    🛡️ Validation des données métier
    📊 Base H2 persistante (fichier local)
    🔒 Authentification basique (Spring Security en mémoire)
    🔍 Recherche de factures par client ou date
    🧪 Test unitaire sur la logique métie avec la base de données H2 en Memory Mode

## 🛠️ Technologies
- Java 17+
- Spring Boot 3.x
- H2 Database en mode fichier
- Maven
- Spring Security
- Lombok
- MapStruct

🚀 Installation
git clone https://github.com/votre-utilisateur/facturation-module.git
cd facturation-module

2. Configurer H2 en mode fichier
   Editez application.properties :
# H2 Configuration (File Mode)
spring.datasource.url=jdbc:h2:file:./data/facturationdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Activer la console H2
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Schema Management
spring.jpa.hibernate.ddl-auto=update

3. Lancer l'application
   mvn spring-boot:run

📚 Endpoints API
Clients
GET    /api/client        # Liste tous les clients
POST   /api/client        # Crée un client
GET    /api/clients/{id}  # Détail d'un client

Factures
GET    /api/facture               # Liste toutes les factures
POST   /api/facture               # Crée une facture
GET    /api/facture/{id}/export   # Export JSON d'une facture
GET    /api/facture/search?clientId=*&date=*    # recherche de factures par client ou date

🔐 Authentification basique
Username: admin
Password: password123

## 🧪 Tests API avec Postman
Une collection Postman est incluse dans le dossier `/postman` :

👉 (./postman/facturation-collection.postman_collection.json)

Vous pouvez l'importer directement dans Postman pour tester tous les endpoints (client, facture, recherche, export).
# Portfolio Dokxm

Application Java Spring portfolio.

## 🚀 Prérequis

- [Java 17+](https://adoptium.net/)
- [Apache Maven](https://maven.apache.org/install.html)

Vérifiez l'installation avec :

```bash
java -version
mvn -version
```

## ⚙️ Installation & Déploiement

Le projet contient un script `deploy.sh` qui gère la compilation et le lancement.

1. Exécutez une première fois le script en mode **BUILD** :

```bash
./deploy.sh
```

➡️ Tapez ensuite :

```
BUILD
```

Le script va compiler et générer l’application.

---

2. Relancez le script en mode **RUN** :

```bash
./deploy.sh
```

➡️ Tapez ensuite :

```
RUN
```

Le projet sera démarré et disponible sur [http://localhost:8080](http://localhost:8080).

## 📂 Structure du projet

- `src/main/java` — code source principal (Spring Boot, contrôleurs, services, etc.)
- `src/main/resources` — ressources (application.properties, templates, etc.)
- `deploy.sh` — script de build/run
- `pom.xml` — configuration Maven

## 📄 Licence

Ce projet est publié sous licence MIT.
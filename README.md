# EasyTable
## Documentazione 
Per consultare ulteriore documetazione dettagliata, consultare il pdf.

## Un sito che permette di prenotare un tavolo online, in modo semplice e veloce.
## Caratteristiche principali:
- Creare una prenotazione (utente)
- Modificare una prenotazione (utente)
- Eliminare una prenotazione (utente)
- Visionare tutte le prenotazioni presenti (admin)
- Modificare una prenotazione (admin)
- Eliminare una prenotazione (admin)
## Requisiti:
- Java 17+
- Maven (incluso nel progetto come wrapper: `./mvnw`)
- Spring Boot
- Browser moderno (per la parte frontend in JS, HTML, CSS)
## Installazione:
Clona la repository ed entra nella cartella del progetto:
```bash
git clone https://github.com/AlexGiunta-source/EasyTable.git
cd EasyTable
```
## Compila ed esegui l’applicazione:
```bash
./mvnw spring-boot:run
```
## Apri il browser e vai all’indirizzo:
```
http://localhost:8080
```
## Utilizzo:
Una volta aperta la pagina del browser, devi: 
- **Registrarti** come nuovo utente
- Effettuare il **login** con le credenziali usate nella registrazione

Funzionalità per **UTENTE** (ROLE_USER):
- Prenotare un tavolo
- Rimuovere una prenotazione
- Modificare una prenotazione
- Visionare le prenotazioni effettuate collegate all'utente che ha effettuato l'accesso in quel momento
Funzionalità per **ADMIN** (ROLE_ADMIN):
- Visionare tutte le prenotazioni presenti di ogni **UTENTE**
- Eliminare le prenotazioni di qualsiasi **UTENTE**
- Modificare le prenotazioni qualsiasi **UTENTE**
## Licenza:
Questo progetto è distribuito sotto licenza MIT.
## Contatti:
Autore: Alex Giunta <br> 
GitHub: @AlexGiunta-source <br> 
Email: alexgiunta7@gmail.com <br> 

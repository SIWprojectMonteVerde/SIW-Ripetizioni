## 🛠️ Funzionalità del Sistema – SiwAnnunci

### 👥 Ruoli Utente

- **Utente Occasionale**  
  - Può visualizzare tutti gli annunci disponibili.  
  - Può filtrare gli annunci per materia o insegnante.  
  - Non può creare prenotazioni o gestire annunci.

- **Studente Registrato**  
  - Può visualizzare tutti gli annunci disponibili.  
  - Può creare, visualizzare ed eliminare le proprie prenotazioni.  
  - Può filtrare gli annunci per materia o insegnante.

- **Insegnante Registrato**  
  - Può visualizzare tutti gli annunci del sistema.  
  - Può creare, modificare ed eliminare i propri annunci.  
  - Può gestire le proprie disponibilità.  
  - Può visualizzare le prenotazioni ricevute.

---

### 📋 Dettaglio delle Informazioni – SiwAnnunci

#### 📢 Annuncio
- Materia insegnata  
- Descrizione del servizio  
- Prezzo orario  
- Insegnante associato  
- Disponibilità orarie  

---

#### 👨‍🏫 Insegnante
- Nome  
- Cognome  
- Email di contatto  
- Annunci pubblicati  

---

#### 🧑‍🎓 Studente
- Nome  
- Cognome  
- Email di contatto  
- Prenotazioni effettuate  

---

#### 📅 Disponibilità
- Giorni della settimana  
- Orari disponibili  
- Annuncio di riferimento  

---

#### 📝 Prenotazione
- Orario di inizio  
- Orario di fine  
- Studente che ha prenotato  
- Annuncio prenotato   

---

## 📌 Casi d'Uso Implementati – SiwAnnunci

### 📢 Visualizzazione Annunci  
**Attore:** Utente Generico  
**Descrizione:** Gli utenti possono visualizzare tutti gli annunci disponibili nel sistema.  
**Pre-condizioni:** Nessuna  
**Azioni:**
1. L'utente accede alla sezione degli annunci.
2. Il sistema mostra tutti gli annunci sotto forma di lista.

**Estensioni:**
- **Visualizza annunci per materia:**
  - L'utente inserisce il nome della materia.
  - Il sistema mostra tutti gli annunci relativi a quella materia.
- **Visualizza annunci per insegnante:**
  - L'utente inserisce il nome dell'insegnante.
  - Il sistema mostra tutti gli annunci relativi a quell'insegnante.

---

### 📝 Creazione di un nuovo annuncio  
**Attore:** Insegnante Registrato  
**Descrizione:** L'insegnante può creare un nuovo annuncio nel sistema.  
**Pre-condizioni:**
- L'insegnante è autenticato nel sistema.
- Il profilo dell'insegnante è completo (nome, cognome, materie abilitate).

**Post-condizioni:**
- Un nuovo annuncio è registrato e associato all'insegnante.
- La disponibilità viene creata e associata all'annuncio.
- L'annuncio è visibile agli studenti.

**Azioni:**
1. L'insegnante accede alla sezione "I miei annunci".
2. Il sistema mostra tutti gli annunci dell'insegnante.
3. L'insegnante clicca su "Crea nuovo annuncio".
4. Il sistema presenta un form con i campi:
   - Materia 
   - Descrizione
   - Disponibilità oraria
   - Prezzo orario 
5. L'insegnante compila il form e clicca su "Pubblica".
6. Il sistema valida i dati e salva l'annuncio.
7. Il sistema reindirizza con messaggio di conferma.

---

### ✏️ Modifica di un annuncio  
**Attore:** Insegnante Registrato  
**Descrizione:** L'insegnante può modificare i dati di un annuncio esistente.  
**Pre-condizioni:**
- L'insegnante è autenticato nel sistema.
- Esiste almeno un annuncio dell'insegnante da modificare.

**Post-condizioni:**
- I dati dell'annuncio sono aggiornati nel sistema.
- La disponibilità associata è modificata di conseguenza.
- L'annuncio aggiornato è visibile agli studenti.

**Azioni:**
1. L'insegnante accede alla sezione "I miei annunci".
2. Il sistema mostra tutti gli annunci dell'insegnante.
3. L'insegnante clicca su "Modifica" accanto all'annuncio desiderato.
4. Il sistema apre un form pre-compilato con i valori correnti.
5. L'insegnante aggiorna i campi e clicca su "Salva".
6. Il sistema valida e salva le modifiche.
7. Il sistema reindirizza con messaggio di conferma.

---

### 🗑️ Eliminazione di annunci  
**Attore:** Insegnante Registrato  
**Descrizione:** L'insegnante può eliminare uno o più annunci dal sistema.  
**Pre-condizioni:**
- L'insegnante è autenticato nel sistema.
- Non ci sono prenotazioni per quell'annuncio

**Post-condizioni:**
- Gli annunci selezionati sono rimossi definitivamente.
- Gli annunci eliminati non sono più visibili.
- L'elenco degli annunci viene aggiornato.

**Azioni:**
1. L'insegnante accede alla sezione "I miei annunci".
2. Il sistema mostra tutti gli annunci dell'insegnante.
3. L'insegnante clicca su "Cancella annunci".
4. L'insegnante seleziona uno o più annunci da eliminare.
5. Il sistema mostra un messaggio di conferma con la lista.
6. L'insegnante accetta la conferma.
7. Il sistema elimina gli annunci.

**Estensioni:**
- **Annullamento operazione:**
  - L'insegnante non accetta la conferma.
  - Il sistema non elimina nulla.


---

### 📅 Creazione prenotazione  
**Attore:** Studente Registrato  
**Descrizione:** Lo studente può effettuare una prenotazione per un annuncio.  
**Pre-condizioni:** L'utente è autenticato nel sistema.  
**Azioni:**
1. L'utente seleziona un annuncio di interesse.
2. L'utente seleziona una disponibilità dell'annuncio.
3. Il sistema crea una nuova prenotazione.

---

### 👁️‍🗨️ Visualizzazione elenco prenotazioni  
**Attore:** Studente Registrato  
**Descrizione:** Lo studente può visualizzare tutte le proprie prenotazioni.  
**Pre-condizioni:** L'utente è autenticato nel sistema.  
**Azioni:**
1. L'utente accede alla sezione prenotazioni.
2. Il sistema mostra tutte le prenotazioni effettuate dall'utente.

---

### 🗑️ Eliminazione prenotazione  
**Attore:** Studente Registrato  
**Descrizione:** Lo studente può eliminare una prenotazione esistente.  
**Azioni:**
1. L'utente accede all'elenco delle prenotazioni.
2. Il sistema mostra l'elenco delle prenotazioni.
3. L'utente seleziona una prenotazione da cancellare.
4. Il sistema mostra un messaggio di conferma.
5. L'utente conferma la scelta.
6. Il sistema cancella la prenotazione.
7. Il sistema invia una notifica agli altri utenti coinvolti.

**Estensioni:**
- **Annullamento operazione:**
  - L'utente non conferma l'eliminazione.
  - Si torna all'elenco delle prenotazioni.

---

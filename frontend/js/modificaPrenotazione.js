/** Elementi principali del DOM modificaPrenotazione
 * @type {HTMLInputElement} id - Campo input per l'ID della prenotazione da modificare.
 * @type {HTMLInputElement} numeroPersone - Campo input per il numero di persone della prenotazione.
 * @type {HTMLInputElement} prenotazioneGiorno - Campo input per il giorno della prenotazione.
 * @type {HTMLInputElement} prenotazioneOra - Campo input per l'orario della prenotazione.
 * @type {HTMLFormElement} formModifcaPrenotazione - Form per modificare la prenotazione.
 * @type {HTMLInputElement} idEliminaPrenotazione - Campo input per l'ID della prenotazione da eliminare.
 * @type {HTMLButtonElement} logout - Bottone per effettuare il logout.
 * @type {HTMLFormElement} eliminaForm - Form per eliminare una prenotazione.
 * @type {HTMLFormElement} formPrenotazione - Form principale per la prenotazione.
 */
let id = document.getElementById('idPrenotazione');
let numeroPersone = document.getElementById('formNumeroPersone');
let prenotazioneGiorno = document.getElementById('formGiorno');
let prenotazioneOra = document.getElementById('formOrario');
let formModifcaPrenotazione = document.getElementById('pulsanteForm');
let idEliminaPrenotazione = document.getElementById('idEliminaPrenotazione');
let logout = document.getElementById('logout');

let eliminaForm = document.getElementById('eliminaForm');
let formPrenotazione = document.getElementById('formPrenotazione');

/** Event listener per il logout.
 * Chiama la funzione funzioneLogout() al click sul bottone.
 */
logout.addEventListener('click', () => {
    funzioneLogout();
})
/** Event listener per la sottomissione del form di modifica prenotazione.
 * Previene il comportamento di default e chiama la funzione modificaPrenotazione().
 * @param {Event} event
 */
formPrenotazione.addEventListener('submit', (event) => {
    event.preventDefault();
    modificaPrenotazione();
})
/** Event listener per la sottomissione del form di eliminazione prenotazione.
 * Previene il comportamento di default e chiama la funzione EliminaPrenotazione().
 * @param {Event} event
 */
eliminaForm.addEventListener('submit', (event) => {
    event.preventDefault();
    EliminaPrenotazione();
})
/** Modifica una prenotazione dell'utente.
 * Controlla che tutti i campi siano compilati correttamente e che il numero di persone non superi 10.
 * Effettua una richiesta di tipo PUT e mostra notifiche di errore o successo tramite Toastify.
 */
const modificaPrenotazione = () => {
    
    if(id.value === '' || numeroPersone.value === '' || prenotazioneGiorno.value === '' ||  prenotazioneOra.value === ''){
         Toastify({
                text: `Attenzione! uno o più campi sono vuoti, riprova.`,
                duration: 1000,
                position: 'center',
                gravity: 'top',
                style:{
                    background: 'red',
                    boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.4)"
                }
            }).showToast();
            return;
    }else if(numeroPersone.value > 10){
        Toastify({
                    text: `Attenzione! per più di 10 persone contattarci.`,
                    position: 'center',
                    duration: 1500,
                    gravity: 'top',
                    style: {
                        background: 'red',
                        boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.4)"
                    }
                }).showToast();
                return;
    }else{
         const prenotazioneModifica = {
        id: id.value,
        numeroPersone: numeroPersone.value,
        prenotazioneGiornoOra: `${prenotazioneGiorno.value} ${prenotazioneOra.value}`
    }

    fetch(`http://localhost:8080/api/modificaPrenotazione/${id.value}`, {
        method: 'PUT',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(prenotazioneModifica)
    })
    .then(response => response.json())
    .then((data) => {
        console.log(data);
        if(data.statusCode === 'OK'){
            Toastify({
                text: `${data.message}`,
                duration: 1000,
                position: 'center',
                gravity: 'top',
                style:{
                    background: 'green',
                    boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.4)"
                }
            }).showToast();
        }else{
            console.log(data)
             Toastify({
                text: `${data.message}`,
                duration: 1000,
                position: 'center',
                gravity: 'top',
                style:{
                    background: 'red',
                    boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.4)"
                }
            }).showToast();
        }
    }).catch((errore) => {
        console.log(`errore: ${errore.message}`)
    })
    }
}
/** Elimina una prenotazione dell'utente.
 * Controlla che l'ID non sia vuoto e invia una richiesta di tipo DELETE.
 * Mostra notifiche di errore o successo tramite Toastify.
 */
const EliminaPrenotazione = () => {
    if(idEliminaPrenotazione.value === ''){
        Toastify({
                text: `Attenzione! uno o più campi sono vuoti, riprova.`,
                duration: 1000,
                position: 'center',
                gravity: 'top',
                style:{
                    background: 'red',
                    boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.4)"
                }
            }).showToast();
    }else{
        fetch(`http://localhost:8080/api/eliminaPrenotazioneUtente/${idEliminaPrenotazione.value}`, {
            method: 'DELETE',
            credentials: 'include'

        }).then(response => response.json())
        .then((data)=> {
            console.log(data);
            if(data.statusCode === 'OK'){
                 Toastify({
                text: `${data.message}`,
                duration: 1000,
                position: 'center',
                gravity: 'top',
                style:{
                    background: 'red',
                    boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.4)"
                }
            }).showToast();
            }else{
                 Toastify({
                text: `${data.message}`,
                duration: 1000,
                position: 'center',
                gravity: 'top',
                style:{
                    background: 'red',
                    boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.4)"
                }
            }).showToast();
            }
        }).catch((errore) => {
            console.log(`errore: ${errore.message}`)
        })
    }
}
/** Effettua il logout dell'utente.
 * Invia una richiesta di tipo POST e reindirizza alla pagina di login.
 * Mostra notifica di conferma tramite Toastify.
 */
const funzioneLogout = () => {
    fetch('http://localhost:8080/auth/logout', {
        method: 'POST',
        credentials: 'include',
        redirect: 'manual',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    }).then(() => {
        Toastify({
            text: `Logout effettuato con successo`,
            gravity: 'top',
            duration: 1000,
            position: 'center',
            style: {
                background: 'red',
                boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.4)"
            }
        }).showToast();
        setTimeout(() => {
            window.location.href = 'login.html'
        }, 1500);
    }).catch((errore) => {
        console.log(`${errore.message}`)
    })
}
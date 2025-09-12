/** Elementi principali del DOM
 */
let formNome = document.getElementById('formNome'); 
let formCognome = document.getElementById('formCognome');
let formNumeroTelefono = document.getElementById('formNumeroTelefono')
let logout = document.getElementById('logout');
let giorno = document.getElementById('formGiorno');
let orario = document.getElementById('formOrario'); 
let formPrenotazione = document.getElementById('formPrenotazione');
let numeroPersone = document.getElementById('formNumeroPersone'); 
let dashBoardPrenotazioni = document.getElementById('contenutoDashBoard');
let cardPrenotazione = document.getElementById('cardPrenotazione');

/** event listener per il logout
 */
logout.addEventListener('click', () => {
    funzioneLogout();
})
/** Event listener per la sottomissione del form di prenotazione.
 * Previene il comportamento di default e chiama la funzione salvaPrenotazione().
 * @param {Event} event 
 */
formPrenotazione.addEventListener('submit', (event) => {
    event.preventDefault();
    salvaPrenotazione();
})
/** event listener per il caricamento del DOM.
 *  effettua una chiamata di tipo GET e genera dinamicamente le card delle prenotazioni
 */
document.addEventListener('DOMContentLoaded', () => {
    mostraListaPrenotazioni();
})
/** Effettua il logout dell'utente.
 * Invia una richiesta di tipo POST e reindirizza alla pagina di login.
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
/** Salva una nuova prenotazione.
 * Controlla che tutti i campi siano compilati correttamente e che il numero di persone sia inferiore a 10.
 * Invia una richiesta di tipo POST e mostra notifiche di errore o successo tramite Toastify.
 */
const salvaPrenotazione = () => {
     if(giorno.value === '' || orario.value === ''  || numeroPersone.value === '' || formNome.value === '' || formCognome.value === '' || formNumeroTelefono.value === ''){
         Toastify({
                    text: `Attenzione! uno o più campi sono vuoti, riprova.`,
                    position: 'center',
                    duration: 1000,
                    gravity: 'top',
                    style: {
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
         const nuovaPrenotazione = {
            nome: formNome.value,
            cognome: formCognome.value,
            numeroTelefono: formNumeroTelefono.value, 
        prenotazioneGiornoOra: `${giorno.value} ${orario.value}`,
        numeroPersone: numeroPersone.valueAsNumber 
    }
    fetch('http://localhost:8080/api/salvaPrenotazione', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(nuovaPrenotazione)
    }).then(response => response.json())
    .then((data)=> {
        console.log(data); 
        console.log(data.errors);
        if(data.statusCode === 'OK'){
            console.log
             Toastify({
                    text: `${data.message}`,
                    position: 'center',
                    duration: 1000,
                    gravity: 'top',
                    style: {
                        background: 'green',
                        boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.4)"
                    }
                }).showToast();
                setTimeout(() => {
                    window.location.reload(); 
                }, 1200);
        }else{
            console.log(data)
            Toastify({
                    text: `${data.message}`,
                    position: 'center',
                    duration: 1000,
                    gravity: 'top',
                    style: {
                        background: 'red',
                        boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.4)"
                    }
                }).showToast();
        }
    }).catch((errore) => {
        console.log(`${errore.message}`)
    })
    }
   
    
}
/** Mostra la lista delle prenotazioni dell'utente.
 * Effettua una chiamata GET al server e genera dinamicamente le card delle prenotazioni.
 */
const mostraListaPrenotazioni =  () => {
    fetch('http://localhost:8080/api/mostraListaPrenotazioniUtente', {
        method: 'GET',
        credentials: 'include',
        headers: {
            'Content-Type':'application/json'
        }
    }).then(response => response.json())
    .then((data) => {
        console.log(data);
        if(data.statusCode ==='OK'){
             data.data.forEach((prenotazione) => {
        let idPrenotazione = document.createElement('p')
        let nome = document.createElement('p')
        let cognome = document.createElement('p')
        let numeroTelefono = document.createElement('p')
          let prenotazioneNumeroPersone = document.createElement('p');
          let prenotazioneDataOra = document.createElement('p');
          let divCard = document.createElement('div');

          divCard.className = 'cardPersonalizza';
            idPrenotazione.innerHTML = `Id della prenotazione<i class="fa-solid fa-info"></i>: ${prenotazione.id}`
          prenotazioneNumeroPersone.innerHTML = ` Numero persone della prenotazione<i class="fa-solid fa-user-check"></i>: ${prenotazione.numeroPersone}`
          prenotazioneDataOra.innerHTML = `Giorno e ora della prenotazione<i class="fa-solid fa-calendar-days"></i><i class="fa-solid fa-clock"></i>: ${prenotazione.prenotazioneGiornoOra}`
          divCard.append( idPrenotazione, prenotazioneNumeroPersone, prenotazioneDataOra )
          dashBoardPrenotazioni.appendChild(divCard)
        })
        }
    }).catch((errore)=> {
        console.log(`${errore.message}`)
    })
}
/** Elementi del dom principali
 */
let contenutoDashboard = document.getElementById('contenutoDashBoard');
let logout = document.getElementById('logout');
let idEliminaPrenotazione = document.getElementById('idEliminaPrenotazione');
let eliminaForm = document.getElementById('eliminaForm') ; 
let idPrenotazione = document.getElementById('idPrenotazione');
let numeroPersone = document.getElementById('formNumeroPersone'); 
let prenotazioneGiorno = document.getElementById('formGiorno')
let prenotazioneOra = document.getElementById('formOrario')
let formPrenotazione = document.getElementById('formPrenotazione'); 

/** event listener per il form di modificaPrenotazione.
 * previene il comportamento di default e chiama la funzione funzioneModificaPrenotazioneAdmin.
 */
formPrenotazione.addEventListener('submit', (event) => {
    event.preventDefault();
    funzioneModificaPrenotazioneAdmin();
})
/** event listener per il form eliminaPrenotazione
 * previene il comportamento di default e chiama la funzione funzioneEliminaPrenotazione
 */
eliminaForm.addEventListener('submit', (event) => {
    event.preventDefault();
    funzioneEliminaPrenotazione();
})
/** event listener per il logout
 */
logout.addEventListener('click', () => {
    funzioneLogout();
})
/** event listener per il caricamento del DOM.
 *  effettua una chiamata di tipo GET e genera dinamicamente le card delle prenotazioni
 */
document.addEventListener('DOMContentLoaded', () => {
    mostraListaPrenotazioniAdmin();
})

/**Mostra la lista di prenotazioni per l'admin
 * 
 * effettua una chiamata GET e genera dinamicamente le card delle prenotazioni
 */
const mostraListaPrenotazioniAdmin = () => {

    fetch('http://localhost:8080/api/mostraListaPrenotazioniAdmin', {

        method: 'GET',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => response.json())
        .then((data) => {
            if (data.statusCode === 'OK') {
                console.log(data);
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
                    nome.innerHTML = `Nome<i class="fa-solid fa-circle-user"></i>: ${prenotazione.nome}`
                    cognome.innerHTML = `Cognome<i class="fa-solid fa-circle-user"></i>: ${prenotazione.cognome}`
                    numeroTelefono.innerHTML = `Numero di telefono<i class="fa-solid fa-mobile-screen-button"></i>: ${prenotazione.numeroTelefono}`
                    prenotazioneNumeroPersone.innerHTML = ` Numero persone della prenotazione<i class="fa-solid fa-user-check"></i>: ${prenotazione.numeroPersone}`
                    prenotazioneDataOra.innerHTML = `Giorno e ora della prenotazione<i class="fa-solid fa-calendar-days"></i><i class="fa-solid fa-clock"></i>: ${prenotazione.prenotazioneGiornoOra}`
                    divCard.append(idPrenotazione, nome, cognome, numeroTelefono, prenotazioneNumeroPersone, prenotazioneDataOra)
                    contenutoDashboard.appendChild(divCard)
                })
            } else {
                Toastify({
                    text: `Ops, sembra che tu non disponga dei permessi per accedere a questa pagina, adesso verrai reindirizzato alla home`,
                    gravity: 'top',
                    duration: 1100,
                    position: 'center',
                    style: {
                        background: 'red',
                        boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.4)"
                    }
                }).showToast();
                setTimeout(() => {
                    window.location.href = 'easyTableHome.html'
                }, 1200);
            }
        }).catch((errore) => {
            console.log(`errore: ${errore.message}`)
        })
}
/** Effettua il logout dell'utente
 * invia una richiesta di tipo POST e reindirizza alla pagina di login
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
/** Elimina una prenotazione in base all'id fornito
 *  invia una richiesta di tipo DELETE
 *  mostra notifiche di errore o di successo tramite Toastify 
 */
const funzioneEliminaPrenotazione = () => {

    if (idEliminaPrenotazione === '') {
        Toastify({
            text: `Attenzione, il campo dell'id non può essere vuoto, riprova`,
            gravity: 'top',
            duration: 1000,
            position: 'center',
            style: {
                background: 'red',
                boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.4)"
            }
        }).showToast();
        return 
    }else{
        fetch(`http://localhost:8080/api/eliminaPrenotazioneAdmin/${idEliminaPrenotazione.value}`, {
        method: 'DELETE',
        credentials: 'include'
    }).then((response => response.json()))
        .then((data) => {
            if (data.statusCode === 'OK') {
                Toastify({
                    text: `${data.message}`,
                    gravity: 'top',
                    duration: 1000,
                    position: 'center',
                    style: {
                        background: 'red',
                        boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.4)"
                    }
                }).showToast();
                setTimeout(() => {
                    window.location.reload();
                }, 1100);
            } else {
                Toastify({
                    text: `${data.message}`,
                    gravity: 'top',
                    duration: 1000,
                    position: 'center',
                    style: {
                        background: 'red',
                        boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.4)"
                    }
                }).showToast();
            }
        }).catch((errore) => {
            console.log(`Errore: ${data.message}`)
        })
    }
}
/** Modifica la prenotazione dell'utente in base all'id fornito
 * invia una richiesta di tipo PUT 
 * mostra notifiche di errore o di successo tramite Toastify 
 */
const funzioneModificaPrenotazioneAdmin = () => {

     if(idPrenotazione.value === '' || numeroPersone.value === '' || prenotazioneGiorno.value === '' ||  prenotazioneOra.value === ''){
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
        id: idPrenotazione.value,
        numeroPersone: numeroPersone.value,
        prenotazioneGiornoOra: `${prenotazioneGiorno.value} ${prenotazioneOra.value}`
    }
    fetch(`http://localhost:8080/api/modificaPrenotazioneAdmin/${idPrenotazione.value}`, {
        method: 'PUT', 
        credentials: 'include',
        headers:{
            'Content-Type':'application/json'
        },
        body: JSON.stringify(prenotazioneModifica)
    }).then(response => response.json())
    .then((data)=> {
        console.log(data)
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
            setTimeout(() => {
                window.location.reload();
            }, 1050);
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
        console.log(`Errore: ${errore.message}`)
    })
    
}
}
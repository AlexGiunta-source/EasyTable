/** Elementi principali del DOM per la registrazione utente.
 * @type {HTMLFormElement} formRegistrazione - Form per registrare un nuovo utente.
 * @type {HTMLInputElement} formNome - Campo input per il nome dell'utente.
 * @type {HTMLInputElement} formCognome - Campo input per il cognome dell'utente.
 * @type {HTMLInputElement} formEmail - Campo input per l'email dell'utente.
 * @type {HTMLInputElement} formPassword - Campo input per la password dell'utente.
 */
let formRegistrazione = document.getElementById('form-registrazione');
let formNome = document.getElementById('formNome');
let formCognome = document.getElementById('formCognome');
let formEmail = document.getElementById('formEmail');
let formPassword = document.getElementById('formPassword')

/** Event listener per la sottomissione del form di registrazione.
 * Previene il comportamento di default del form, controlla che tutti i campi siano compilati
 * e invia una richiesta di tipo POST per registrare un nuovo utente.
 * Mostra notifiche di errore o successo tramite Toastify.
 * @param {Event} event - Evento generato dalla sottomissione del form.
 */
formRegistrazione.addEventListener('submit', (event) => {
    event.preventDefault();
    const nuovoUtente = {
        nome: formNome.value,
        cognome: formCognome.value,
        email: formEmail.value,
        password: formPassword.value
    }
    if(!formNome.value || !formCognome.value || !formEmail.value || !formPassword.value) {
        Toastify({
            text: `Attenzione! tutti i campi devono essere compilati, riprova.`,
            position: 'center',
            duration: 2000,
            gravity: 'top',
            style: {
                background: 'red',
                boxShadow: "0px 4px 15px rgba(0, 0, 0, 0.4)"
            }
        }).showToast();
    }
    fetch('http://localhost:8080/auth/register', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(nuovoUtente)
    }).then(response => response.json())
        .then((data) => {
            if (data.statusCode === 'OK') {
                console.log(data)
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
                    window.location.href = 'login.html'
                }, 2000);
            } else {
                console.log(data)
                console.log(data.status)
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
            console.log(`Errore: ${errore.message}`);
        })
})
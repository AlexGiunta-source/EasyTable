/**  Elementi principali del DOM per la login.
 * @type {HTMLFormElement} formLogin - Form per effettuare il login.
 * @type {HTMLInputElement} formEmail - Campo input per l'email dell'utente.
 * @type {HTMLInputElement} formPassword - Campo input per la password dell'utente.
 */
let formLogin = document.getElementById('form-login');
let formEmail = document.getElementById('formEmail');
let formPassword = document.getElementById('formPassword')

/** Event listener per la sottomissione del form di login.
 * Previene il comportamento di default del form, controlla che tutti i campi siano compilati
 * e invia una richiesta di tipo POST con le credenziali dell'utente.
 * Mostra notifiche di errore o successo tramite Toastify.
 * @param {Event} event - Evento generato dalla sottomissione del form.
 */
formLogin.addEventListener('submit', (event) => {
    event.preventDefault();
    const accessoUtente = {
        email: formEmail.value,
        password: formPassword.value
    }
    if (!formEmail.value || !formPassword.value) {
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
    fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(accessoUtente)
    }).then(response => response.json())
        .then((data) => {
            console.log(data)
            console.log(data.status)
            if (data.statusCode === 'OK') {
                console.log('AAAAAAAA')
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
                    window.location.href = 'EasyTableHome.html'
                }, 2000);
            } else {
                console.log('a')
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
            console.log(` Errore: ${errore.message}`)
        })
})
/** Elementi principali del DOM
 * 
 */
let logout = document.getElementById('logout');
/** event listener per il logout
 */
logout.addEventListener('click', () => {
    funzioneLogout();
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
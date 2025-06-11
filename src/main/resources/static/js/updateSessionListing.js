function addAvailability() {
    updateSessionAndRedirect('/teacher/formNewListing/addAvailability');
}

function removeAvailability(index) {
    updateSessionAndRedirect('/teacher/formNewListing/removeAvailability/' + index);
}

function updateSessionAndRedirect(url) {
    const form = document.getElementById('formAnnuncio');
    const formData = new FormData(form);

    // RIMUOVI il campo availability problematico dal FormData
    formData.delete('availability');

    // Aggiorna la sessione
    fetch('/teacher/updateSessionListing', {
        method: 'POST',
        body: formData
    }).then(response => {
        if (response.ok) {
            // Dopo aver aggiornato la sessione, vai all'endpoint richiesto
            window.location.href = url;
        }
    }).catch(error => {
        console.error('Errore:', error);
        // In caso di errore, vai comunque all'endpoint
        window.location.href = url;
    });
}

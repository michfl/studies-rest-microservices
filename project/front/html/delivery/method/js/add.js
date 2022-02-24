import {getParameterByName} from '../../../js/dom_utils.js';
import {getBackendUrl} from '../../../js/configuration.js';

window.addEventListener('load', () => {
    const infoForm = document.getElementById('infoForm');

    infoForm.addEventListener('submit', event => createDeliveryMethod(event));
});

function createDeliveryMethod(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 201) {
            //alert("Delivery method created");
            window.location.replace("list.html");
        }
    };
    xhttp.open("POST", getBackendUrl() + '/api/delivery/methods', true);

    const request = {
        'carrier': document.getElementById('carrier').value,
        'time': parseInt(document.getElementById('time').value),
        'rating': parseFloat(document.getElementById('rating').value)
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request));
}

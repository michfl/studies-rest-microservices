import {getParameterByName} from '../../../js/dom_utils.js';
import {getBackendUrl} from '../../../js/configuration.js';

window.addEventListener('load', () => {
    const infoForm = document.getElementById('infoForm');

    infoForm.addEventListener('submit', event => updateInfoAction(event));

    fetchAndDisplayDeliveryMethod();
});

function fetchAndDisplayDeliveryMethod() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let response = JSON.parse(this.responseText);
            for (const [key, value] of Object.entries(response)) {
                let input = document.getElementById(key);
                if (input) {
                    input.value = value;
                }
            }
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/delivery/methods/' + getParameterByName('carrier'), true);
    xhttp.send();
}

function updateInfoAction(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            fetchAndDisplayDeliveryMethod();
            //--alert("Delivery method updated");
            window.location.replace("list.html");
        }
    };
    xhttp.open("PUT", getBackendUrl() + '/api/delivery/methods/' + getParameterByName('carrier'), true);

    const request = {
        'time': parseInt(document.getElementById('time').value),
        'rating': parseFloat(document.getElementById('rating').value)
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request));
}

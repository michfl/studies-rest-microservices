import {getParameterByName} from '../../js/dom_utils.js';
import {getBackendUrl} from '../../js/configuration.js';

window.addEventListener('load', () => {
    const infoForm = document.getElementById('infoForm');

    infoForm.addEventListener('submit', event => updateInfoAction(event));

    fetchAndDisplayOrder();
});

function fetchAndDisplayOrder() {
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
    xhttp.open("GET", getBackendUrl() + '/api/delivery/methods/' + getParameterByName('carrier') + '/orders/'
        + getParameterByName('order'), true);
    xhttp.send();
}

function updateInfoAction(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            fetchAndDisplayOrder();
            //alert("Order updated");
            window.location.replace("../../delivery/method/view.html?carrier=" + getParameterByName('carrier'));
        }
    };
    xhttp.open("PUT", getBackendUrl() + '/api/delivery/methods/' + getParameterByName('carrier') + '/orders/'
        + getParameterByName('order'), true);

    const request = {
        'client': document.getElementById('client').value,
        'payment': parseFloat(document.getElementById('payment').value)
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request));
}

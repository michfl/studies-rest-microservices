import {getParameterByName} from '../../js/dom_utils.js';
import {getBackendUrl} from '../../js/configuration.js';

window.addEventListener('load', () => {
    const infoForm = document.getElementById('infoForm');

    infoForm.addEventListener('submit', event => createOrder(event));
});

function createOrder(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 201) {
            //alert("Order created");
            window.location.replace("../../delivery/method/view.html?carrier=" + getParameterByName('carrier'));
        }
    };
    xhttp.open("POST", getBackendUrl() + '/api/orders', true);

    const request = {
        'client': document.getElementById('client').value,
        'payment': parseFloat(document.getElementById('payment').value),
        'deliveryMethod': getParameterByName('carrier')
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request));
}

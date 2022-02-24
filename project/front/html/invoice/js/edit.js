import {getParameterByName} from '../../js/dom_utils.js';
import {getBackendUrl} from '../../js/configuration.js';

window.addEventListener('load', () => {
    const infoForm = document.getElementById('infoForm');

    infoForm.addEventListener('submit', event => updateInfoAction(event));

    fetchAndDisplayInvoice();
});

function fetchAndDisplayInvoice() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            let response = JSON.parse(this.responseText);
            document.getElementById('issuer').value = response.issuer;
            document.getElementById('issue_date').value = response.issueDate;
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/invoices/' + getParameterByName('id'), true);
    xhttp.send();
}

function updateInfoAction(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            fetchAndDisplayInvoice();
            //alert("Order updated");
            window.location.replace("../order/view.html?id=" + getParameterByName('orderId'));
        }
    };
    xhttp.open("PUT", getBackendUrl() + '/api/invoices/' + getParameterByName('id'), true);

    const request = {
        'issuer': document.getElementById('issuer').value,
        'issueDate': document.getElementById('issue_date').value
    };

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request));
}

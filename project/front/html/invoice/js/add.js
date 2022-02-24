import {getParameterByName} from '../../js/dom_utils.js';
import {getBackendUrl} from '../../js/configuration.js';

window.addEventListener('load', () => {
    const infoForm = document.getElementById('infoForm');

    infoForm.addEventListener('submit', event => createInvoice(event));
});

function createInvoice(event) {
    event.preventDefault();

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 201) {
            //alert("Invoice created");
            window.location.replace("../order/view.html?id=" + getParameterByName('orderId'));
        }
    };
    xhttp.open("POST", getBackendUrl() + '/api/invoices', true);

    let request = new FormData();
    request.append('orderId', parseInt(getParameterByName('orderId')));
    request.append('issuer', document.getElementById('issuer').value);
    request.append('issueDate', document.getElementById('issue_date').value);
    request.append('file', document.getElementById('form_file').files[0]);

    xhttp.send(request);
}

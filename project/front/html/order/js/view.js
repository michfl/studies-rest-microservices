import {
    getParameterByName,
    setTextNode
} from '../../js/dom_utils.js';
import {getBackendUrl} from '../../js/configuration.js';

window.addEventListener('load', () => {
    fetchAndDisplayOrder();
    fetchAndDisplayInvoice();
    document.getElementById("delete_invoice").addEventListener('click', deleteInvoice)
});

function fetchAndDisplayOrder() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayOrder(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/orders/' + getParameterByName('id'), true);
    xhttp.send();
}

function fetchAndDisplayInvoice() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayInvoice(JSON.parse(this.responseText))
            document.getElementById('link_invoice').innerHTML = "Edit invoice";
            document.getElementById('link_invoice').href = "../invoice/edit.html?id=" + JSON.parse(this.responseText).id + '&orderId=' + getParameterByName('id');
        } 
        if (this.readyState === 4 && this.status === 404) {
            document.getElementById('invoice_card').remove();
            document.getElementById('link_invoice').href = "../invoice/add.html?orderId=" + getParameterByName('id');
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/orders/' + getParameterByName('id') + '/invoice', true);
    xhttp.send();
}

function displayOrder(order) {
    setTextNode('id', order.id);
    setTextNode('client', order.client);
    setTextNode('payment', order.payment);
    setTextNode('delivery_method', order.deliveryMethod);
}

function displayInvoice(invoice) {
    setTextNode('invoice_id', invoice.id);
    setTextNode('issuer', invoice.issuer);
    setTextNode('issue_date', invoice.issueDate);
    document.getElementById('download').href = getBackendUrl() + '/api/orders/' + getParameterByName('id') + '/invoice/download';
}

function deleteInvoice() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            window.location.reload(true);
        }
    };
    xhttp.open("DELETE", getBackendUrl() + '/api/invoices/' + document.getElementById('invoice_id').innerHTML, true);
    xhttp.send();
}

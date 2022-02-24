import {clearElementChildren, createLinkCell, createLinkCellClass, createButtonCell, createTextCell} from '../../../js/dom_utils.js';
import {getBackendUrl} from '../../../js/configuration.js';

window.addEventListener('load', () => {
    fetchAndDisplayInvoices();
});

function fetchAndDisplayInvoices() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayInvoices(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/invoices', true);
    xhttp.send();
}

function displayInvoices(invoices) {
    let tableBody = document.getElementById('tableBody');
    clearElementChildren(tableBody);
    invoices.invoices.forEach(invoice => {
        tableBody.appendChild(createTableRow(invoice));
    })
}

function createTableRow(invoice) {
    let tr = document.createElement('tr');
    tr.appendChild(createTextCell(invoice.id));
    tr.appendChild(createTextCell(invoice.originalFileName));
    tr.appendChild(createLinkCellClass('view', '../order/view.html?id=' + invoice.orderId, "link-secondary"));
    tr.appendChild(createLinkCell('download', getBackendUrl() + '/api/invoices/' + invoice.id + '/download'));
    tr.appendChild(createButtonCell('delete', () => deleteInvoice(invoice.id)));
    return tr;
}

function deleteInvoice(id) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            fetchAndDisplayInvoices();
        }
    };
    xhttp.open("DELETE", getBackendUrl() + '/api/invoices/' + id, true);
    xhttp.send();
}

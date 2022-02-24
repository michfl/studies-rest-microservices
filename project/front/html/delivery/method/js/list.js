import {clearElementChildren, createLinkCellClass, createButtonCell, createTextCell} from '../../../js/dom_utils.js';
import {getBackendUrl} from '../../../js/configuration.js';

window.addEventListener('load', () => {
    fetchAndDisplayDeliveryMethods();
});

function fetchAndDisplayDeliveryMethods() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayDeliveryMethods(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/delivery/methods', true);
    xhttp.send();
}

function displayDeliveryMethods(deliveries) {
    let tableBody = document.getElementById('tableBody');
    clearElementChildren(tableBody);
    deliveries.deliveryMethods.forEach(delivery => {
        tableBody.appendChild(createTableRow(delivery.carrier));
    })
}

function createTableRow(delivery) {
    let tr = document.createElement('tr');
    tr.appendChild(createTextCell(delivery));
    tr.appendChild(createLinkCellClass('view', 'view.html?carrier=' + delivery, "link-secondary"));
    tr.appendChild(createLinkCellClass('edit', 'edit.html?carrier=' + delivery, "link-warning"));
    tr.appendChild(createButtonCell('delete', () => deleteDeliveryMethod(delivery)));
    return tr;
}

function deleteDeliveryMethod(delivery) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            fetchAndDisplayDeliveryMethods();
        }
    };
    xhttp.open("DELETE", getBackendUrl() + '/api/delivery/methods/' + delivery, true);
    xhttp.send();
}

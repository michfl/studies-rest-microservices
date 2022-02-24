import {
    getParameterByName,
    clearElementChildren,
    createLinkCellClass,
    createButtonCell,
    createTextCell,
    setTextNode
} from '../../../js/dom_utils.js';
import {getBackendUrl} from '../../../js/configuration.js';

window.addEventListener('load', () => {
    fetchAndDisplayDeliveryMethod();
    fetchAndDisplayOrders();

    let addLink = document.getElementById("addOrder").href;
    document.getElementById("addOrder").href = addLink + '?carrier=' + getParameterByName('carrier');
});

function fetchAndDisplayOrders() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayOrders(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/delivery/methods/' + getParameterByName('carrier') + '/orders', true);
    xhttp.send();
}

function displayOrders(orders) {
    let tableBody = document.getElementById('tableBody');
    clearElementChildren(tableBody);
    orders.orders.forEach(order => {
        tableBody.appendChild(createTableRow(order));
    })
}

function createTableRow(order) {
    let tr = document.createElement('tr');
    tr.appendChild(createTextCell(order.id));
    tr.appendChild(createTextCell(order.payment));
    tr.appendChild(createLinkCellClass('view', '../../order/view.html?id=' + order.id, "link-secondary"));
    tr.appendChild(createLinkCellClass('edit', '../../order/edit.html?carrier='
        + getParameterByName('carrier') + '&order=' + order.id, "link-warning"));
    tr.appendChild(createButtonCell('delete', () => deleteOrder(order.id)));
    return tr;
}

function deleteOrder(order) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            fetchAndDisplayOrders();
        }
    };
    xhttp.open("DELETE", getBackendUrl() + '/api/delivery/methods/' + getParameterByName('carrier')
        + '/orders/' + order, true);
    xhttp.send();
}

function fetchAndDisplayDeliveryMethod() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayDeliveryMethod(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/delivery/methods/' + getParameterByName('carrier'), true);
    xhttp.send();
}

function displayDeliveryMethod(delivery) {
    setTextNode('carrier', delivery.carrier);
    setTextNode('time', delivery.time);
    setTextNode('rating', delivery.rating);
}

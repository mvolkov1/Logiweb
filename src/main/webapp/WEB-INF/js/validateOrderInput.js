/**
 * Created by mvolkov on 22.06.2016.
 */

function confirmDeleteItem() {
    return confirm("Delete item?");
}

function confirmDeleteCargo() {
    return confirm("Delete cargo?");
}

function validateUid() {
    var uid = document.getElementById("uid").value;
    if (!uid) {
        alert('Please enter UID');
        return false;
    }
    if (uid.length < 8) {
        alert('UID should contain 8 alphanumeric characters!');
        return false;
    }
}

function validateItem() {
    validateUid();
    var city = document.getElementById("city").value;
    if (!city) {
        alert('Please select city');
        return false;
    }
}

function validateCargo() {
    validateUid();
    var cargoUid = document.getElementById("cargoUid").value;
    if (!cargoUid) {
        alert('Please enter UID for the cargo');
        return false;
    }

    var mass = document.getElementById("cargoMass").value;
    if (!mass) {
        alert('Please enter the mass of the cargo');
        return false;
    }

    var item1 = document.getElementById("cargoItem1").value;
    var item2 = document.getElementById("cargoItem2").value;
    if (!(parseInt(item2) > parseInt(item1)))
    {
        alert('The item number of the start city should be less than item number of the end city!');
        return false;
    }
}


function checkUid() {
    var length = document.getElementById("uid").value.length;
    if (length < 8) {
        alert('UID should contain 8 alphanumeric characters!');
        return false;
    }
}

function onAddOrderItem() {

    if (!document.getElementById("newCity").value) {
        alert('Select city!');
        return false;
    }
}

function onAddCargo() {


    if (!document.getElementById("cargoId").value) {
        alert('Enter cargo UID!');
        return false;
    }

    if (!document.getElementById("cargoTitle").value) {
        alert('Enter cargo title!');
        return false;
    }
    var mass = document.getElementById("cargoMass").value;
    if (!mass) {
        alert('Enter cargo mass!');
        return false;
    }
    if (isNaN(mass)) {
        alert('Please enter numeric value for the mass');
        return false;
    }

    if (!document.getElementById("cargoCity1").value) {
        alert('Enter start city! ');
        return false;
    }

    if (!document.getElementById("cargoCity2").value) {
        alert('Enter end city! ');
        return false;
    }
}

function onSetVehicle() {


    if (!document.getElementById("orderVehicle").value) {
        alert('Select vehicle!');
        return false;
    }

}

function onAddDriver() {

    if (!document.getElementById("driverUid").value) {
        alert('Select driver!');
        return false;
    }

}



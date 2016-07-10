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

function validateVehicle()
{
    validateUid();
    var vehicle = document.getElementById("vin").value;
    if (!vehicle) {
        alert('Please select vehicle');
        return false;
    }
}

function validateDriver()
{
    validateUid();
    var driver = document.getElementById("driverUid").value;
    if (!driver) {
        alert('Please select driver');
        return false;
    }
}

function confirmDeleteDriver() {
    return confirm("Delete driver?");
}





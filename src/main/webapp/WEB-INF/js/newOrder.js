/**
 * Created by mvolkov on 22.06.2016.
 */


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
    if (isNaN(mass))
    {
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



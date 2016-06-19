/**
 * Created by mvolkov on 19.06.2016.
 */
function isInt(value) {
    return !isNaN(value) &&
        parseInt(Number(value)) == value &&
        !isNaN(parseInt(value, 10));
}
function validateVehicleInput() {
    var capacity = document.getElementById("capacity").value;
    if (isNaN(capacity))
    {
        alert('Please enter numeric value for capacity');
        return false;
    }
    var nDrivers = document.getElementById("numberOfDrivers").value;
    if (!isInt(nDrivers))
    {
        alert('Number of drivers should be integer from 1 to 3!');
        return false
    }
    if (nDrivers > 3 || nDrivers < 1)
    {
        alert('Number of drivers should be integer from 1 to 3!');
        return false;
    }
    var vin = document.getElementById("vin").value;
    if (vin.length != 7)
    {
        alert('VIN should contain 2 characters and 5 digits!');
        return false;
    }
    if (!/^([A-Z]|[a-z]){2}\d{5}$/.test(vin))
    {
        alert('VIN should contain 2 characters and 5 digits!');
        return false;
    }

}
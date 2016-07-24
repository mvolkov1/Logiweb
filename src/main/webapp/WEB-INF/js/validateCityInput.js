/**
 * Created by mvolkov on 24.07.2016.
 */
function confirmDeleteCity() {
    return confirm("Delete city?");
}

function validateCity() {
    var newCity = document.getElementById("newCity").value;
    if (!newCity) {
        alert('Please enter city name');
        return false;
    }
    if (newCity.length < 2) {
        alert('City name should contain at least 2 characters!');
        return false;
    }
}
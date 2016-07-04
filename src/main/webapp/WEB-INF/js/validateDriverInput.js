/**
 * Created by mvolkov on 19.06.2016.
 */
/**
 * Created by mvolkov on 19.06.2016.
 */
function isInt(value) {
    return !isNaN(value) &&
        parseInt(Number(value)) == value &&
        !isNaN(parseInt(value, 10));
}
function validateDriverInput() {

    var uid = document.getElementById("uid").value;
    if (!uid)
    {
        alert("Please enter UID")
        return false;
    }
    var monthHours = document.getElementById("monthHours").value;
    if (!monthHours)
    {
        alert("Please enter month hours");
        return false;
    }
    if (!isInt(monthHours))
    {
        alert('Month hours should be integer from 0 to 176!');
        return false
    }
    if (monthHours > 176 || monthHours < 0)
    {
        alert('Month hours should be integer from 1 to 3!');
        return false;
    }
    if (!document.getElementById("city").value) {
        alert('Please select city! ');
        return false;
    }
}
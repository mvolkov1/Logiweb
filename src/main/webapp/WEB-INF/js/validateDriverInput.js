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

    var monthHours = document.getElementById("monthHours").value;
    if (!isInt(monthHours))
    {
        alert('monthHours should be integer from 0 to 176!');
        return false
    }
    if (monthHours > 176 || monthHours < 0)
    {
        alert('monthHours should be integer from 1 to 3!');
        return false;
    }
}
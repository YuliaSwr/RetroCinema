function setDate() {
    dateInput = document.getElementById("date-input");
    dateInput.min = new Date().toISOString().split("T")[0];
}

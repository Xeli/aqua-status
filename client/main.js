$(document).ready(function() {
    createNewStream("temperatures", updateTemperature);

    getInitialStatus("temperatures", setTemperature);
});

$(document).ready(function() {
    createNewStream("temperatures", updateTemperature);
    createNewStream("lights", updateLights);

    getInitialStatus("temperatures", setTemperature);
    getInitialStatus("lights", setLight);
});

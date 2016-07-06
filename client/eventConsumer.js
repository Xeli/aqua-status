function setValue(element, value) {
    var template = element.data('template');
    value = Math.round(value * 100) / 100;
    element.text(template.replace('template', value));
    element.data("value", value);
}

function setTemperature(data) {
    setValue($('.temperature'), data.value);
}
function updateTemperature(data) {
    updateNumericValue($('.temperature'), data.value);
}

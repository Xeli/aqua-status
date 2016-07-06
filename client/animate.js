function updateNumericValue(element, newValue) {
    var oldValue = element.data('value');
    updateNumericValueWorker(element, oldValue, newValue, oldValue < newValue);
}

function updateNumericValueWorker(element, currentValue, newValue, growing) {
    var done = (growing && currentValue >= newValue) || (!growing && currentValue <= newValue)
    if(done) {
        setValue(element, newValue);
        return;
    }

    if(growing) {
        currentValue += 0.1;
    } else {
        currentValue -= 0.1;
    }
    setTimeout(function() {
        setValue(element, currentValue);
        updateNumericValueWorker(element, currentValue, newValue, growing);
    }, 50);
}

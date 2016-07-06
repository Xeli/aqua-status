var urlToChangeStream = "/api/[modelName]/change-stream?_format=event-stream";

var createNewStream = function(modelNamePlural, callback) {
    var eventSource = new EventSource(urlToChangeStream.replace('[modelName]', modelNamePlural));
    eventSource.addEventListener("data", function(msg) {
        var data = JSON.parse(msg.data);
        callback(data.data);
    });
};

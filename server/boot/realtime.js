var eventStream = require('event-stream');
module.exports = function(app) {
    var Temperature = app.models.Temperature;
    Temperature.createChangeStream(function(err, changes) {
        changes.pipe(eventStream.stringify()).pipe(process.stdout);
    });

    var Light = app.models.Light;
    Light.createChangeStream(function(err, changes) {
        changes.pipe(eventStream.stringify()).pipe(process.stdout);
    });
}

module.exports = function(Ato) {
    Ato.averageDuration = function(cb) {
        Ato.find({where: {switchOn: true},
                  limit: 1,
                  order: 'date DESC',
                  skip: 10}, function(error, result) {
            if(error != null || result.length == 0) {
                cb("Not enough data found");
                return;
            }
            Ato.find({where: {date: {gte: result[0].date}}}, function(error, result){
                if(error != null || result.length == 0) {
                    cb("Not enough data found");
                    return;
                }

                var resultCount = Math.floor(result.length / 2);
                var averageDuration = 0;
                var i = 0;
                while(i + 1 < result.length) {
                    var start = result[i].date;
                    var end   = result[i+1].date;

                    averageDuration += (end.getTime() - start.getTime()) / 1000 / resultCount;
                    i += 2;
                }
                cb(null, Math.round(averageDuration));
            });
        });
    };

    Ato.remoteMethod('averageDuration',{
        returns: {arg: 'duration', type: 'number'},
        http: {
            verb: "GET"
        },
    });
};

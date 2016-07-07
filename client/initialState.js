function getInitialStatus(modelNamePlural, callback) {
    jQuery.ajax({
        method: 'GET',
        data: {
            'filter': {
                'order': 'date DESC'
            }
        },
        url: '/api/' + modelNamePlural + '/findOne',
        dataType: 'json',
        success: function(data, status, xhr) {
            callback(data);
        }
    });
}



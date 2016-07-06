function getInitialStatus(modelNamePlural, callback) {
    jQuery.ajax({
        method: 'GET',
        data: {
            'order': 'date DESC'
        },
        url: '/api/' + modelNamePlural + '/findOne',
        dataType: 'json',
        success: function(data, status, xhr) {
            callback(data);
        }
    });
}



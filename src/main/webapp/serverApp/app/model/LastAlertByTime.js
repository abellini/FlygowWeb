Ext.define('ExtDesktop.model.LastAlertByTime', {
    extend: 'Ext.data.Model',
    requires: [
        'Ext.data.Field'
    ],
    idProperty: 'id',
    fields: [{
        name: 'time',
        type: 'string'
    },{
        name: 'value',
        type: 'int'
    }]
});
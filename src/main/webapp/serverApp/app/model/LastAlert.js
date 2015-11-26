Ext.define('ExtDesktop.model.LastAlert', {
    extend: 'Ext.data.Model',
    requires: [
        'Ext.data.Field'
    ],
    idProperty: 'id',
    fields: [{
        name: 'alertType',
        type: 'string'
    },{
        name: 'value',
        type: 'int'
    },{
        name: 'color',
        type: 'string'
    }]
});
Ext.define('ExtDesktop.model.Tablet', {
    extend: 'Ext.data.Model',
    requires: [
        'Ext.data.Field'
    ],
    idProperty: 'id',
    fields: [{
        name: 'id'
    },{
        name: 'number',
        type: 'int'
    }]
});
Ext.define('ExtDesktop.model.Accompaniment', {
    extend: 'Ext.data.Model',
    requires: [
        'Ext.data.Field'
    ],
    idProperty: 'id',
    fields: [{
        name: 'id'
    },{
        name: 'name',
        type: 'string'
    }]
});
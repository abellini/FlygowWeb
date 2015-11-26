Ext.define('ExtDesktop.model.AlertItem', {
    extend: 'Ext.data.Model',
    requires: [
        'Ext.data.Field'
    ],
    idProperty: 'id',
    fields: [
		{
            name: 'id',
            type: 'long'
        },{
            name: 'tabletNumber',
            type: 'int'
        },{
            name: 'alertHour',
            type: 'long'
        },{
            name: 'attendantId',
            type: 'int'
        },{
            name: 'attendantName',
            type: 'string'
        },{
            name: 'type',
            type: 'string'
        },{
            name: 'alertStatus',
            type: 'string'
        }
    ]
});
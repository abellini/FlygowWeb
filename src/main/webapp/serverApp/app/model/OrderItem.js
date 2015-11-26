Ext.define('ExtDesktop.model.OrderItem', {
    extend: 'Ext.data.Model',

    requires: [
        'Ext.data.Field'
    ],

    idProperty: 'id',

    fields: [
		{
            name: 'id'
        },{
            name: 'observations'
        },{
            name: 'quantity',
            type: 'int'
        },{
            name: 'value',
            type: 'float'
        },{
            name: 'food'
        },{
            name: 'orderid',
            type: 'int'
        },{
            name: 'status'
        },{
            name: 'iniorderdate'
        },{
			name: 'operationalArea'
		},{
			name: 'statusId',
			type: 'int'
		},{
			name: 'tabletNumber',
			type: 'int'
		},{
			name: 'accompaniments'
		}
    ]
});
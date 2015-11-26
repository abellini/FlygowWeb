Ext.define('ExtDesktop.store.OrderItems', {
    extend: 'Ext.data.Store',

    requires: [
        'ExtDesktop.model.OrderItem'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'ExtDesktop.model.OrderItem',
            storeId: 'orderItems',
			groupField: 'operationalArea',
            proxy: {
				type: 'ajax',
				url: 'order/listPendentsToday',
				reader: {
					type: 'json',
					idProperty: 'id'
				}
			}
        }, cfg)]);
    }
});
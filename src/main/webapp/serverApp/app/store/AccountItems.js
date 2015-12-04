Ext.define('ExtDesktop.store.AccountItems', {
    extend: 'Ext.data.Store',

    requires: [
        'ExtDesktop.model.OrderItem'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'ExtDesktop.model.OrderItem',
            storeId: 'accountItems',
			groupField: 'tabletNumber',
            proxy: {
				type: 'ajax',
				url: 'order/listOrders',
				reader: {
					type: 'json',
					idProperty: 'id'
				}
			}
        }, cfg)]);
    }
});
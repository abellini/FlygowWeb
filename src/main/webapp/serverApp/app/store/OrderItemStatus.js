Ext.define('ExtDesktop.store.OrderItemStatus', {
    extend: 'Ext.data.Store',
    requires: [
        'ExtDesktop.model.OrderItemStatus'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'ExtDesktop.model.OrderItemStatus',
            storeId: 'orderItemStatus',
            autoLoad: false,
            proxy: {
				type: 'ajax',
				url: 'order/listOrderItemStatus',
				method: 'GET',
                headers: { 'Content-Type': 'application/json;charset=utf-8' },
				reader: {
                    type: 'json',
                    idProperty: 'id',
                    getData: function(data){
                        for(i = 0; i < data.length; i++){
                            data[i].name = _(data[i].name);
                        }
                        return data;
                    }
                }
			}
        }, cfg)]);
    }
});
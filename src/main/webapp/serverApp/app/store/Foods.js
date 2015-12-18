Ext.define('ExtDesktop.store.Foods', {
    extend: 'Ext.data.Store',
    requires: [
        'ExtDesktop.model.Food'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'ExtDesktop.model.Food',
            storeId: 'foods',
            autoLoad: false,
            proxy: {
				type: 'ajax',
				url: 'food/listAll',
				method: 'GET',
                headers: { 'Content-Type': 'application/json;charset=utf-8' },
				reader: {
                    type: 'json',
                    idProperty: 'id'
                }
			}
        }, cfg)]);
    }
});
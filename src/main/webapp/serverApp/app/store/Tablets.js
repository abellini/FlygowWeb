Ext.define('ExtDesktop.store.Tablets', {
    extend: 'Ext.data.Store',
    requires: [
        'ExtDesktop.model.Tablet'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'ExtDesktop.model.Tablet',
            storeId: 'foods',
            autoLoad: false,
            proxy: {
				type: 'ajax',
				url: 'tablet/listAll',
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
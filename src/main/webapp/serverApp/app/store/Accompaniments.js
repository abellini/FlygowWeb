Ext.define('ExtDesktop.store.Accompaniments', {
    extend: 'Ext.data.Store',
    requires: [
        'ExtDesktop.model.Accompaniment'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'ExtDesktop.model.Accompaniment',
            storeId: 'accompaniments',
            autoLoad: false,
            proxy: {
				type: 'ajax',
				url: 'accompaniment/listAll',
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
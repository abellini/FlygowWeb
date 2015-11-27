Ext.define('ExtDesktop.store.LastAlertsByTime', {
    extend: 'Ext.data.Store',
    requires: [
        'ExtDesktop.model.LastAlertByTime'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'ExtDesktop.model.LastAlertByTime',
            storeId: 'lastAlertsByTime',
            autoLoad: false,
            proxy: {
				type: 'ajax',
				url: 'attendant/listLastAlertsByTime',
				method: 'GET',
                headers: { 'Content-Type': 'application/json;charset=utf-8' },
				reader: {
					type: 'json'
				}
			}
        }, cfg)]);
    }
});
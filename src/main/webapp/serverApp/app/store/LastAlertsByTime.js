Ext.define('ExtDesktop.store.LastAlertsByTime', {
    extend: 'Ext.data.Store',
    requires: [
        'ExtDesktop.model.LastAlertByTime'
    ],
    constructor: function(cfg) {
        var me = this, lastDaysSize = 5;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'ExtDesktop.model.LastAlertByTime',
            storeId: 'lastAlertsByTime',
            autoLoad: false,
            proxy: {
				type: 'ajax',
				url: 'attendant/listLastAlertsByTime/' + lastDaysSize,
				method: 'GET',
                headers: { 'Content-Type': 'application/json;charset=utf-8' },
				reader: {
					type: 'json'
				}
			}
        }, cfg)]);
    }
});
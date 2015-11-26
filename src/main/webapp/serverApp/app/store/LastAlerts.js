Ext.define('ExtDesktop.store.LastAlerts', {
    extend: 'Ext.data.Store',
    requires: [
        'ExtDesktop.model.LastAlert'
    ],
    constructor: function(cfg) {
        var me = this, lastAlertsSize = 8;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'ExtDesktop.model.LastAlert',
            storeId: 'lastAlert',
            autoLoad: false,
            proxy: {
				type: 'ajax',
				url: 'attendant/listLastAlertsForChart/' + lastAlertsSize,
				method: 'GET',
                headers: { 'Content-Type': 'application/json;charset=utf-8' },
				reader: {
					type: 'json',
					getData: function(data){
                        for(i = 0; i < data.length; i++){
                            data[i].alertType = _(data[i].alertType);
                        }
                        return data;
                    }
				}
			}
        }, cfg)]);
    }
});
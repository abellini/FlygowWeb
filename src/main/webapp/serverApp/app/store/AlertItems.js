Ext.define('ExtDesktop.store.AlertItems', {
    extend: 'Ext.data.Store',
    requires: [
        'ExtDesktop.model.AlertItem'
    ],
    constructor: function(cfg) {
        var me = this, lastAlertsSize = 8;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'ExtDesktop.model.AlertItem',
            storeId: 'alertItems',
			groupField: 'alertStatus',
			sorters: [{
                sorterFn: function(o1, o2){
                    return o1.data.id < o2.data.id ? -1 : 1;
                }
            }],
            proxy: {
				type: 'ajax',
				url: 'attendant/listLastAlerts/' + lastAlertsSize,
				method: 'GET',
				noCache: true,
                headers: { 'Content-Type': 'application/json;charset=utf-8' },
				reader: {
					type: 'json',
					idProperty: 'id',
					defineTypeOfCall: function(alertType){
                        if(alertType === 'TO_PAYMENT'){
                            return _('to') + ' <b>' + _('REALIZE THE PAYMENT') + '</b>';
                        } else if (alertType === 'TO_ATTENDANCE'){
                            return _('to') + ' <b>' + _('ATTENDANCE') + '</b>';
                        }
                        return "";
                    },
                    defineTypeOfStatusCall: function(status){
                        if(status === 'OPENED'){
                            return '<span class="alert-opened">' + _('OPENED') + '</span>';
                        } else if (status === 'ATTENDED'){
                            return '<span class="alert-attended">' + _('ATTENDED') + '</span>';
                        }
                        return "";
                    },
					getData: function(data){
                        for(i = 0; i < data.length; i++){
                            data[i].type = this.defineTypeOfCall(data[i].type);
                            data[i].alertStatus = this.defineTypeOfStatusCall(data[i].alertStatus);
                            data[i].alertHour = Ext.Date.format(new Date(data[i].alertHour), "d/m/Y H:i:s");
                        }
                        return data;
                    }
				}
			}
        }, cfg)]);
    }
});
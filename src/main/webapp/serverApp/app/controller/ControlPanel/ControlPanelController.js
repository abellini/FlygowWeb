Ext.define('ExtDesktop.controller.ControlPanel.ControlPanelController', {
    extend: 'Ext.app.Controller',
	// getters for views
    refs: [{
        selector: 'tabpanel[itemId=controlTabPanel]',
        ref: 'controlTabPanel'
    },{
        selector: 'button[itemId=btnControlTabAlerts]',
        ref: 'btnControlTabAlerts'
    },{
		selector: 'button[itemId=btnControlTabDevices]',
        ref: 'btnControlTabDevices'
	},{
		selector: 'button[itemId=btnControlTabAccounts]',
        ref: 'btnControlTabAccounts'
	}],
	init: function(){
		var me = this;
		me.control({
			'button[itemId=btnControlTabAlerts]': {
                click: me.onControlTabAlertsBtn.bind(me)
            },
			'button[itemId=btnControlTabDevices]': {
                click: me.onControlTabDevicesBtn.bind(me)
            },
            'button[itemId=btnControlTabAccounts]': {
                click: me.onControlTabAccountsBtn.bind(me)
            }
		})
	},
	onControlTabAlertsBtn: function(){
        var me = this;
        var tabpanel = me.getControlTabPanel();
        var alertPanel = tabpanel.getComponent('alertspanel');
        if(!alertPanel){
            alertPanel = Ext.create('ExtDesktop.view.ControlPanel.Alerts.AlertsPanel');
            tabpanel.add(alertPanel);
        }
        tabpanel.setActiveTab(alertPanel);
	},
	onControlTabDevicesBtn: function(){
        console.debug('onControlTabDevicesBtn');
	},
	onControlTabAccountsBtn: function(){
        console.debug('onControlTabAccountsBtn');
	}
})
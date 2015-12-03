Ext.define('ExtDesktop.view.ControlPanel.Alerts.AlertsPanel', {
    extend: 'Ext.panel.Panel',
	requires: [
	    'ExtDesktop.view.ControlPanel.Alerts.LastAlertsPanel',
	    'ExtDesktop.view.ControlPanel.Alerts.AllAlertsPanel'
	],
    alias: 'widget.alertspanel',
	itemId: 'alertspanel',
	border: false,
    //TODO: Trocar por imagem correta da aba
	iconCls: 'x-icon-accompaniment-tab',
	title: _('Alerts'),
	layout: 'border',
	closable: true,
	initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [{
                xtype: 'lastalertspanel',
                region: 'center'
            },{
                xtype: 'allalertspanel',
                region: 'south',
                height: 230,
                split: true
            }]
        });
        me.callParent(arguments);
    }
});
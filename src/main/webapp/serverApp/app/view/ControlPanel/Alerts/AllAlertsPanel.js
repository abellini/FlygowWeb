Ext.define('ExtDesktop.view.ControlPanel.Alerts.AllAlertsPanel', {
    extend: 'Ext.panel.Panel',
	requires: [
	    'Ext.ux.DataView.Animated',
	    'Ext.XTemplate',
	    'Chart.ux.Highcharts.PieSerie',
	    'Chart.ux.Highcharts.SplineSerie'
	],
    alias: 'widget.allalertspanel',
	itemId: 'allalertspanel',
	border: true,
	collapsible: true,
	title: _('Visual Data'),
	layout: 'border',
	initComponent: function() {
        var me = this, storeLastAlerts = Ext.data.StoreManager.lookup('LastAlerts'),
        storeLastAlertsByTime = Ext.data.StoreManager.lookup('LastAlertsByTime');
		Ext.applyIf(me, {
			items: [{
				xtype: 'panel',
				region: 'west',
				html: '1',
				width: '74%',
				split: true,
				layout: 'fit',
				items:[{
					xtype: 'highchart',
					series:[{
						dataIndex: 'value',
						name: _('Total Alerts')
					}],
					xField: 'time',
					store: storeLastAlertsByTime,
					chartConfig: {
						chart: {
							type: 'spline'
						},
						title: {
							text: _('Total alerts in the last days...')
						}
					}
				}]
			},{
				xtype: 'panel',
				region: 'center',
				layout: 'fit',
				items:[{
					xtype: 'highchart',
					series:[{
						type: 'pie',
					    categorieField: 'alertType',
					    dataField: 'value',
					    name: _('Alerts'),
					    colorField: 'color'
					}],
					store: storeLastAlerts,
					chartConfig: {
						chart: {
						   type: 'pie'
						},
						title: {
						   text: _('Last status of alerts')
						}
					}
				}]
			}]
		});
		storeLastAlerts.load();
		storeLastAlertsByTime.load();
		me.callParent(arguments);
    }
});
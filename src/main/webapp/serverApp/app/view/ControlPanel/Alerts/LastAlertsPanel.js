Ext.define('ExtDesktop.view.ControlPanel.Alerts.LastAlertsPanel', {
    extend: 'Ext.panel.Panel',
	requires: [
	    'ExtDesktop.ux.DataView.Animated',
	    'Ext.XTemplate',
	],
    alias: 'widget.lastalertspanel',
	itemId: 'lastalertspanel',
	border: false,
	title: _('Last Alerts'),
	autoScroll: true,
	initComponent: function() {
        var me = this;
		var store = Ext.data.StoreManager.lookup('AlertItems');
		var dataview = Ext.create('Ext.view.View', {
			store: store,
			tpl  : Ext.create('Ext.XTemplate',
				'<tpl for=".">',
					'<div class="alert">',
						'<img width="64" height="64" src="/flygow/serverApp/resources/images/statusLarge.png" />',
						'<strong>Tablet {tabletNumber}</strong>',
						'<span>Chamando <b>{attendantName}</b></span><br>',
						'<span>{type}</span><br>',
						'<span>às {alertHour}</span><br>',
						'<span>Status: {alertStatus}</span>',
					'</div>',
				'</tpl>'
			),

			id: 'alerts',

			itemSelector: 'div.alert',
			overItemCls : 'alert-hover',
			multiSelect : false,
			autoScroll  : true,
			listeners: {
				itemcontextmenu: function(view, record, item, index, e, eOpts ){
					var position = e.getXY(), me = this;
					e.stopEvent();
					view.select(index);
					if(record.data.alertStatus.indexOf('opened') != -1){
						var menu = new Ext.menu.Menu({
							items: [
								{
									text: _('Define as "ATTENDED"'),
									handler: function() {
										var alertId = record.data.id;

										Ext.Ajax.request({
											url: 'attendant/readNotification/'+alertId,
											success: function(response){
												var resp = Ext.decode(response.responseText);
												var dataview = Ext.getCmp('alerts');
												var taskbar = myDesktopApp.desktop.taskbar;
												dataview.getStore().load();
												dataview.refresh();
												taskbar.alertSize = resp.length;
												taskbar.doLayout();
												taskbar.remove(taskbar.items.length-2);
												taskbar.doLayout();
												if(taskbar.alertSize > 0){
													taskbar.insertNotificationIcon(resp);
												}
												var storeLastAlertsStatus = Ext.data.StoreManager.lookup('LastAlerts');
												storeLastAlertsStatus.reload();
											},
											failure: function(response) {}
										});
									}
								}
							]
						});
						menu.showAt(position);
					}
				}
			}
		});
		store.load();
		Ext.applyIf(me, {
			items: dataview
		});
		me.callParent(arguments);
    }
});
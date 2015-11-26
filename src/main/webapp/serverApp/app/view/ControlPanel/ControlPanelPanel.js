Ext.define('ExtDesktop.view.ControlPanel.ControlPanelPanel', {
    extend: 'Ext.panel.Panel',
	requires: [
		''
    ],
    alias: 'widget.controlpanelpanel',
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
			border: false,
			layout: 'border',
			items: [
				{
					xtype: 'panel',
					region: 'north',
					title: _('Menu'),
					collapsible: true,
					layout: 'hbox',
					bodyStyle: {
						'background-image': 'url(staticResources/images/fundo-listrado.png) !important',
						'background-position': '0% 100% !important',
						'background-repeat': 'repeat-x !important'
					},
					items:[{
						xtype: 'buttongroup',
						flex: 1,
						layout: 'hbox',
						style: {
							'padding': '7px',
							'background': 'transparent !important'
						},
						items: [{
							text: _('Alerts'),
							itemId: 'btnControlTabAlerts',
							flex: 1,
							scale: 'small',
							iconCls: 'add',
							iconAlign: 'top'
						},
						{
							xtype: 'spacer',
							width: 10,
							height: 0
						},
						{
							text: _('Devices'),
							itemId: 'btnControlTabDevices',
							flex: 1,
							scale: 'small',
							iconCls: 'add',
							iconAlign: 'top'
						},{
							xtype: 'spacer',
							width: 10,
							height: 0
						}, {
							text: _('Accounts'),
							itemId: 'btnControlTabAccounts',
							flex: 1,
							scale: 'small',
							iconCls: 'add',
							iconAlign: 'top'
						}]
					}]

				}, {
					xtype: 'tabpanel',
					region: 'center',
					itemId: 'controlTabPanel',
					bodyStyle: {
						//TODO: Trocar por imagem correta da home do Painel de Controle
						'background-image': 'url(staticResources/images/fundo-listrado.png) !important',
						'background-position': '0% 100% !important',
						'background-repeat': 'repeat-x !important'
					}
				}
			]
        });

        me.callParent(arguments);
    }

});
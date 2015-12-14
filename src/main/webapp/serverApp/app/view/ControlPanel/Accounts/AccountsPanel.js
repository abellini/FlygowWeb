Ext.define('ExtDesktop.view.ControlPanel.Accounts.AccountsPanel', {
    extend: 'Ext.panel.Panel',
	requires: [
	    'ExtDesktop.view.ControlPanel.Accounts.GridAccounts',
	    'ExtDesktop.view.ControlPanel.Accounts.AccountDetailsPanel'
	],
    alias: 'widget.accountspanel',
	itemId: 'accountspanel',
	border: false,
	closable: true,
	//TODO: Trocar por imagem correta da aba
	iconCls: 'x-icon-accompaniment-tab',
	title: _('Accounts'),
	layout: 'border',
	initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [{
                region: 'center',
                xtype: 'gridaccounts'
            },{
                xtype: 'accountdetailspanel',
                width: 330,
                region: 'east',
                split: true
            }]
        });
        me.callParent(arguments);
    }
});
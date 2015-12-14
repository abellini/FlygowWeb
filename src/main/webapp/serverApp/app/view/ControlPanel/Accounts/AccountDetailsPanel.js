Ext.define('ExtDesktop.view.ControlPanel.Accounts.AccountDetailsPanel', {
    extend: 'Ext.panel.Panel',
	requires: [
	    ''
	],
    alias: 'widget.accountdetailspanel',
	itemId: 'accountdetailspanel',
	border: false,
	title: _('Account Details'),
	layout: 'border',
	collapsible: true,
	collapsed: true,
	initComponent: function() {
        var me = this;

        Ext.applyIf(me, {

        });
        me.callParent(arguments);
    }
});
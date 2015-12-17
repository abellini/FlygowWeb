Ext.define('ExtDesktop.view.ControlPanel.Accounts.AccountDetailsPanel', {
    extend: 'Ext.form.Panel',
    alias: 'widget.accountdetailspanel',
	itemId: 'accountdetailspanel',
	border: false,
	title: _('Account Details'),
	collapsible: true,
	collapsed: true,
	autoScroll: true,
	initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
        	bodyPadding: 5,
        	layout: 'anchor',
			defaults: {
				anchor: '100%'
			},
			fieldDefaults: {
				labelAlign: 'left',
				msgTarget: 'side'
			},
			items: [
				{
					fieldLabel: _('Item ID'),
					xtype: 'textfield',
					name: 'id',
					readOnly: true
				},{
					fieldLabel: _('Status'),
					xtype: 'textfield',
					name: 'status'
				},{
					fieldLabel: _('Item'),
					xtype: 'textfield',
					name: 'food'
				},{
					fieldLabel: _('Date'),
					xtype: 'datefield',
					format: 'd/m/Y',
					name: 'iniorderdate',
					editable: false
				},{
					fieldLabel: _('Quantity'),
					xtype: 'textfield',
					name: 'quantity'
				},{
					fieldLabel: _('Item Value'),
					xtype: 'textfield',
					name: 'value'
				},{
					fieldLabel: _('Accomp'),
					xtype: 'textfield',
					name: 'accompaniments',
					width: 200
				},{
					fieldLabel: _('Observations'),
					xtype: 'textarea',
					name: 'observations'
				}
			]
        });
        me.callParent(arguments);
    }
});
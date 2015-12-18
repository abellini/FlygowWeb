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
					fieldLabel: _('Tablet Number'),
					xtype: 'combobox',
					name: 'tabletNumber',
					queryMode: 'local',
					displayField: 'number',
					valueField: 'id',
					emptyText: _('Select...'),
					editable: false,
					onTriggerClick: function() {
						this.getStore().load();
						this.expand();
					},
					store: Ext.data.StoreManager.lookup('Tablets'),
					listeners: {
						afterrender: function(){
							this.getStore().load();
						}
					},
					allowBlank: false
				},{
					fieldLabel: _('Status'),
					xtype: 'combobox',
					name: 'status',
					queryMode: 'local',
					displayField: 'name',
					valueField: 'id',
					emptyText: _('Select...'),
					editable: false,
					onTriggerClick: function() {
						this.getStore().load();
						this.expand();
					},
					store: Ext.data.StoreManager.lookup('OrderItemStatus'),
					listeners: {
						afterrender: function(){
							this.getStore().load();
						}
					},
					allowBlank: false
				},{
					fieldLabel: _('Item'),
					xtype: 'combobox',
					name: 'food',
					queryMode: 'local',
					displayField: 'name',
					valueField: 'id',
					emptyText: _('Select...'),
					editable: false,
					onTriggerClick: function() {
						this.getStore().load();
						this.expand();
					},
					store: Ext.data.StoreManager.lookup('Foods'),
					listeners: {
						afterrender: function(){
							this.getStore().load();
						}
					},
					allowBlank: false
				},{
					fieldLabel: _('Date'),
					xtype: 'datefield',
					format: 'd/m/Y',
					name: 'iniorderdate',
					editable: false,
					allowBlank: false
				},{
					fieldLabel: _('Quantity'),
					xtype: 'numberfield',
					name: 'quantity',
					allowBlank: false
				},{
					fieldLabel: _('Item Value'),
					xtype: 'numberfield',
					name: 'value',
					allowBlank: false
				},{
					fieldLabel: _('Accomp'),
					xtype: 'combobox',
					name: 'accompaniments',
					queryMode: 'local',
					displayField: 'name',
					valueField: 'id',
					emptyText: _('Select...'),
					editable: false,
					multiSelect: true,
					onTriggerClick: function() {
						this.getStore().load();
						this.expand();
					},
					store: Ext.data.StoreManager.lookup('Accompaniments'),
					listeners: {
						afterrender: function(){
							this.getStore().load();
						}
					}
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
Ext.define('ExtDesktop.view.Orders.OrdersDetails', {
    extend: 'Ext.form.Panel',
    alias: 'widget.ordersdetails',
	itemId: 'orderform',
	createUpdateUrl: '',
	border: false,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
			itemId: me.itemId,
			layout: 'anchor',
			items: [
				{
					xtype: 'fieldset',
					title: _('Order Informations'),
					collapsible: false,
					style: {
						borderColor: '#3892d3',
						borderStyle: 'solid',
						borderWidth: '1px'
					},
					items: [
						{
							xtype: 'fieldcontainer',
							layout: 'hbox',
							defaults: {
								flex: 1,
								readOnly: true
							},
							items:[
								{
									fieldLabel: _('Item ID'),
									xtype: 'textfield',
									flex: 0.1,
									name: 'id',
									margin: '0 10 0 0'
								},{
									fieldLabel: _('Status'),
									xtype: 'textfield',
									flex: 0.2,
									name: 'status',
									margin: '0 10 0 0'
								},{
									fieldLabel: _('Item'),
									xtype: 'textfield',
									name: 'food',
									flex: 0.8,
									margin: '0 10 0 0'
								},{
									fieldLabel: _('Date'),
									margin: '0 10 0 0',
									xtype: 'datefield',
									flex: 0.2,
									format: 'd/m/Y',
									name: 'iniorderdate'
								},{
									fieldLabel: _('Quantity'),
									xtype: 'textfield',
									flex: 0.1,
									name: 'quantity',
									margin: '0 10 0 0'
								},{
									fieldLabel: _('Item Value'),
									xtype: 'textfield',
									flex: 0.1,
									name: 'value',
									margin: '0 10 0 0'
								}
							]
						},{
							xtype: 'fieldcontainer',
							layout: 'hbox',
							defaults: {
								flex: 1,
								readOnly: true
							},
							items: [
								{
									fieldLabel: _('Accompaniments') + '/' + _('Promotion Items'),
									margin: '0 10 0 0',
									xtype: 'textfield',
									name: 'accompaniments'
								}
							]
						},{
							xtype: 'fieldcontainer',
							layout: 'hbox',
							defaults: {
								flex: 1,
								readOnly: true
							},
							items: [
								{
									fieldLabel: _('Observations'),
									margin: '0 10 0 0',
									xtype: 'textarea',
									name: 'observations'
								}
							]
						}
					]	
				}		
			],
			fieldDefaults: {
				labelAlign: 'top',
				msgTarget: 'side'
			},
			defaults: {
				anchor: '100%'
			},
			padding: '10'
        });
        me.callParent(arguments);
    }

});
Ext.define('ExtDesktop.view.Orders.OrdersPanel', {
    extend: 'Ext.panel.Panel',
	requires: [
		'ExtDesktop.view.Orders.OrdersDetails',
		'ExtDesktop.view.Orders.GridOrders'
    ],
    alias: 'widget.orderspanel',
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
			border: false,
			layout: 'border',
			items: [
				{
					xtype: 'panel',
					region: 'north',
					height: 250,
					layout: 'fit',
					items:[{
						xtype: 'ordersdetails'
					}]
				},{
					xtype: 'panel',
					region: 'center',
					layout: 'border',
					items:[{
						xtype: 'gridorders',
						region: 'center'
					},{
						xtype: 'panel',
						region: 'east',
						width: 200,
						layout: {
							type: 'vbox',
							align: 'stretch'
						},
						defaults: {
							flex: 1
						},
						items: [{
							xtype: 'fieldset',
							margin: '0 10 10 10',
							title: '<b>' + _('Number Table') + '</b>',
							collapsible: false,
							style: {
								borderColor: '#3892d3',
								borderStyle: 'solid',
								borderWidth: '1px'
							},
							layout: 'fit',
							items: [{
								xtype: 'panel',
								itemId: 'numberTable',
								border: false,
								style: {
									'background-image': 'url(staticResources/images/fundo-listrado.png) !important',
									'background-repeat': 'no-repeat !important',
									'background-position': 'left bottom !important',
									'position':'relative'
								},
								bodyStyle: 'background:transparent;',
								html: ''
							}]
						},{
							xtype: 'button',
							itemId: 'readyProduct',
							margin: '10 10 10 10',
							cls: 'x-icon-ready-product',
							text: _('Product Ready')
						},{
							xtype: 'button',
							itemId: 'cancelProduct',
							margin: '10 10 10 10',
							cls: 'x-icon-cancel-product',
							text: _('Cancel Order')
						}]
					}]
				}
			]
        });

        me.callParent(arguments);
    }

});
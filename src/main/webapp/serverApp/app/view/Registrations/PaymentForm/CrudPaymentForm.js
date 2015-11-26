Ext.define('ExtDesktop.view.Registrations.PaymentForm.CrudPaymentForm', {
    extend: 'ExtDesktop.view.default.CrudPanel',
    alias: 'widget.crudpaymentform',
    closable: false,
	border: false,
	iconCls: 'x-icon-paymentform-tab',
	title: _('Payment Form'),
	itemId: 'paymentform',
	storeId: 'paymentformStore',
	readUrl: 'paymentform/listAll',
	deleteUrl: 'paymentform/delete',
	createUpdateUrl: 'paymentform/saveUpdate',
	fields: [{
		fieldLabel: _('ID'),
		name: 'id',
		hidden: true,
		gridFlex: 0.2
	},{
		fieldLabel: _('Name'),
		name: 'name',
		gridFlex: 1
	},{
		fieldLabel: _('Description'),
		name: 'description',
		gridFlex: 1
	}],
	formFields: [
		{
			xtype: 'textfield',
			fieldLabel: _('ID'),
			name: 'id',
			allowBlank: true,
			hidden: true
		},
		{
			xtype: 'fieldset',
			title: _('General Informations'),
			collapsible: true,
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
						flex: 1
					},
					items:[
						{
							fieldLabel: _('Name'),
							xtype: 'textfield',
							margin: '0 10 0 0',
							name: 'name',
							allowBlank: false
						},{
							fieldLabel: _('Description'),
							xtype: 'textfield',
							name: 'description',
							allowBlank: false
						}
					]
				}
			]
		}
	]	
});
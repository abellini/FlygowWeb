Ext.define('ExtDesktop.view.Registrations.Coin.CrudCoin', {
    extend: 'ExtDesktop.view.default.CrudPanel',
    alias: 'widget.crudcoin',
    closable: false,
	border: false,
	iconCls: 'x-icon-coin-tab',
	title: _('Coins'),
	itemId: 'crudcoin',
	storeId: 'coinStore',
	readUrl: 'coin/listAll',
	deleteUrl: 'coin/delete',
	createUpdateUrl: 'coin/saveUpdate',
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
		fieldLabel: _('Symbol'),
		name: 'symbol',
		gridFlex: 1
	},{
		fieldLabel: _('Conversion'),
		name: 'conversion',
		gridFlex: 1
	}],
	formFields:[
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
							name: 'name',
							margin: '0 10 0 0',
							allowBlank: false
						},{
							fieldLabel: _('Symbol'),
							xtype: 'textfield',
							name: 'symbol',
							margin: '0 10 0 0',
							allowBlank: false
						},{
							fieldLabel: _('Conversion'),
							name: 'conversion',
							allowBlank: false,
							xtype: 'numberfield'
						}
					]
				}	
			]
		}
	]
});
Ext.define('ExtDesktop.view.Registrations.OperationalArea.CrudOperationalArea', {
    extend: 'ExtDesktop.view.default.CrudPanel',
    alias: 'widget.crudoperationalarea',
    closable: false,
	border: false,
	iconCls: 'x-icon-operationalarea-tab',
	title: _('Operational Area'),
	itemId: 'operationalarea',
	storeId: 'operationalareaStore',
	readUrl: 'operationalarea/listAll',
	deleteUrl: 'operationalarea/delete',
	createUpdateUrl: 'operationalarea/saveUpdate',
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
	},{
		fieldLabel: _('Roles'),
		name: 'stringRoles',
		gridFlex: 1,
		columnRenderer: function(value, metaData, record, rowIndex, colIndex, store, view){
			var val = '';
			for(var i = 0; i < value.length; i++){
				if(i != value.length - 1){
					val += value[i].name + ', ';
				}else{
					val += value[i].name;
				}
			}
			return val;
		}
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
							margin: '0 10 0 0',
							xtype: 'textfield',
							name: 'description',
							allowBlank: false
						},
						{
							fieldLabel: _('Roles'),
							xtype: 'combobox',
							name: 'stringRoles',
							queryMode: 'local',
							displayField: 'name',
							margin: '0 10 0 0',
							valueField: 'id',
							editable: false,
							multiSelect: true,
							onTriggerClick: function() {
								this.getStore().load();
								this.expand();
							},
							store: Ext.create('Ext.data.JsonStore',{
								autoLoad: false,
								proxy: {
									type: 'ajax',
									url: 'role/listAll',
									reader: {
										type: 'json',
										idProperty: 'id'
									}
								},
								fields: ['id', 'name']
							}),
							listeners: {
								afterrender: function(){
									this.getStore().load();
								}
							},
							allowBlank: true
						}
					]
				}
			]
		}
	]
});
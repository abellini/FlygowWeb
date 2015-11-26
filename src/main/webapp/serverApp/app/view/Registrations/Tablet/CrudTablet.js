Ext.define('ExtDesktop.view.Registrations.Tablet.CrudTablet', {
    extend: 'ExtDesktop.view.default.CrudPanel',
    alias: 'widget.crudtablet',
    closable: false,
	border: false,
	iconCls: 'x-icon-tablet-tab',
	title: _('Tablet'),
	itemId: 'crudtablet',
	storeId: 'tabletStore',
	readUrl: 'tablet/listAll',
	deleteUrl: 'tablet/delete',
	createUpdateUrl: 'tablet/saveUpdate',
	fields: [{
		fieldLabel: _('ID'),
		name: 'id',
		hidden: true,
		gridFlex: 0.4
	},{
		fieldLabel: _('Number'),
		name: 'number',
		gridFlex: 1
	},{
		fieldLabel: _('Coin'),
		name: 'coinId',
		gridFlex: 1,
		columnRenderer: function(value, metaData, record, rowIndex, colIndex, store, view){
			if(value.length > 0){
				return value[0].name;
			}else{
				return '';
			}
		}
	},{
		fieldLabel: _('IP'),
		name: 'ip',
		gridFlex: 1
	},{
		fieldLabel: _('Port'),
		name: 'port',
		gridFlex: 1
	},{
		fieldLabel: _('Server IP'),
		name: 'serverIp',
		gridFlex: 1
	},{
		fieldLabel: _('Server Port'),
		name: 'serverPort',
		gridFlex: 1
	},{
		fieldLabel: _('Attendant'),
		name: 'attendantId',
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
	},{
		fieldLabel: _('Advertisement'),
		name: 'advertisementId',
		gridFlex: 0.8,
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
							fieldLabel: _('IP'),
							margin: '0 10 0 0',
							xtype: 'textfield',
							name: 'ip',
							allowBlank: false
						},{
							fieldLabel: _('Port'),
							xtype: 'numberfield',
							name: 'port',
							allowDecimals: false,
							minValue: 0,
							allowBlank: false
						}
					]
				},
				{
					xtype: 'fieldcontainer',
					layout: 'hbox',
					defaults: {
						flex: 1
					},
					items:[
						{
							fieldLabel: _('Server IP'),
							margin: '0 10 0 0',
							xtype: 'textfield',
							name: 'serverIp',
							allowBlank: false
						},{
							fieldLabel: _('Server Port'),
							xtype: 'numberfield',
							name: 'serverPort',
							allowDecimals: false,
							minValue: 0,
							allowBlank: false
						}
					]
				},
				{
					xtype: 'fieldcontainer',
					layout: 'hbox',
					defaults: {
						flex: 1
					},
					items:[
						{
							fieldLabel: _('Number'),
							margin: '0 10 0 0',
							xtype: 'numberfield',
							name: 'number',
							allowDecimals: false,
							minValue: 0,
							allowBlank: false
						},{
							fieldLabel: _('Coin'),
							xtype: 'combobox',
							name: 'coinId',
							queryMode: 'local',
							displayField: 'name',
							valueField: 'id',
							emptyText: _('Select...'),
							editable: false,
							onTriggerClick: function() {
								this.getStore().load();
								this.expand();
							},
							store: Ext.create('Ext.data.JsonStore',{
								autoLoad: false,
								proxy: {
									type: 'ajax',
									url: 'coin/listAll',
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
							allowBlank: false
						}
					]
				},
				{
					xtype: 'fieldcontainer',
					layout: 'hbox',
					defaults: {
						flex: 1
					},
					items:[
						{
							fieldLabel: _('Attendant'),
							margin: '0 10 0 0',
							xtype: 'combobox',
							name: 'attendantId',
							queryMode: 'local',
							displayField: 'name',
							valueField: 'id',
							editable: false,
							multiSelect: false,
							onTriggerClick: function() {
								this.getStore().load();
								this.expand();
							},
							store: Ext.create('Ext.data.JsonStore',{
								autoLoad: false,
								proxy: {
									type: 'ajax',
									url: 'attendant/listAll',
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
							allowBlank: false
						},{
							fieldLabel: _('Advertisement'),
							xtype: 'combobox',
							name: 'advertisementId',
							queryMode: 'local',
							displayField: 'name',
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
									url: 'advertisement/listAll',
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
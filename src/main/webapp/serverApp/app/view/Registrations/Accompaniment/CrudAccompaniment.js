Ext.define('ExtDesktop.view.Registrations.Accompaniment.CrudAccompaniment', {
    extend: 'ExtDesktop.view.default.CrudPanel',
    alias: 'widget.crudaccompaniment',
    closable: false,
	border: false,
	iconCls: 'x-icon-accompaniment-tab',
	title: _('Accompaniments'),
	itemId: 'crudaccompaniment',
	storeId: 'accompanimentStore',
	readUrl: 'accompaniment/listAll',
	deleteUrl: 'accompaniment/delete',
	createUpdateUrl: 'accompaniment/saveUpdate',
	fields: [{
		fieldLabel: _('ID'),
		name: 'id',
		hidden: true,
		gridFlex: 0.4
	},{
		fieldLabel: _('Name'),
		name: 'name',
		gridFlex: 1
	},{
		fieldLabel: _('Value'),
		name: 'value',
		gridFlex: 1
	},{
        
        name: 'photo',
        fieldLabel: _('Photo'),
		columnRenderer: function(value, metaData, record, rowIndex, colIndex, store, view){
			if(value == 'OK'){
				return '<img src="staticResources/images/silk/tick.png" />';
			}else{
				return '<img src="staticResources/images/silk/cancel.png" />';
			}
		}
    },{
        name: 'video',
        fieldLabel: _('Video'),
		columnRenderer: function(value, metaData, record, rowIndex, colIndex, store, view){
			if(value == 'OK'){
				return '<img src="staticResources/images/silk/tick.png" />';
			}else{
				return '<img src="staticResources/images/silk/cancel.png" />';
			}
		}
    },{
		fieldLabel: _('Description'),
		name: 'description',
		gridFlex: 1.3
	},{
		fieldLabel: _('Category'),
		name: 'category',
		gridFlex: 1,
		columnRenderer: function(value, metaData, record, rowIndex, colIndex, store, view){
			if(value.length > 0){
				return value[0].name;
			}else{
				return '';
			}
		}
	},{
		fieldLabel: _('Operational Area'),
		name: 'operationalArea',
		gridFlex: 1,
		columnRenderer: function(value, metaData, record, rowIndex, colIndex, store, view){
			if(value.length > 0){
				return value[0].name;
			}else{
				return '';
			}
		}
	},{
		fieldLabel: _('Active'),
		name: 'active',
		columnRenderer: function(value, metaData, record, rowIndex, colIndex, store, view){
			if(value){
				return '<img src="staticResources/images/silk/thumb_up.png" />';
			}else{
				return '<img src="staticResources/images/silk/thumb_down.png" />';
			}
		},
		gridFlex: 0.4
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
							margin: '0 10 0 0',
							xtype: 'textfield',
							name: 'name',
							allowBlank: false
						},{
							fieldLabel: _('Value'),
							xtype: 'numberfield',
							name: 'value',
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
							fieldLabel: _('Description'),
							margin: '0 10 0 0',
							xtype: 'textfield',
							name: 'description',
							allowBlank: false
						},{
							fieldLabel: _('Category'),
							xtype: 'combobox',
							name: 'category',
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
									url: 'category/listAll',
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
							fieldLabel: _('Operational Area'),
							margin: '0 10 0 0',
							xtype: 'combobox',
							name: 'operationalArea',
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
									url: 'operationalarea/listAll',
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
							fieldLabel: _('Active'),
							name: 'active',
							xtype: 'checkboxfield',
							submitValue: true
						}
					]
				}
			]
		},
		{
			xtype: 'fieldset',
			title: _('Media'),
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
							xtype: 'filefield',
							margin: '0 10 0 0',
							name: 'photo',
							fieldLabel: _('Photo'),
							labelWidth: 50,
							msgTarget: 'side',
							allowBlank: true,
							buttonText: _('Select Photo...')
						},{
							xtype: 'filefield',
							name: 'video',
							fieldLabel: _('Video'),
							labelWidth: 50,
							msgTarget: 'side',
							allowBlank: true,
							buttonText: _('Select Video...')
						}
					]
				}
			]	
		}			
	]	
});
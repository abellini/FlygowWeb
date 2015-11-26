Ext.define('ExtDesktop.view.Registrations.Attendant.CrudAttendant', {
    extend: 'ExtDesktop.view.default.CrudPanel',
    alias: 'widget.crudattendant',
    closable: false,
	border: false,
	iconCls: 'x-icon-attendant-tab',
	title: _('Attendants'),
	itemId: 'crudattendant',
	storeId: 'attendantStore',
	readUrl: 'attendant/listAll',
	deleteUrl: 'attendant/delete',
	createUpdateUrl: 'attendant/saveUpdate',
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
			fieldLabel: _('Last Name'),
			name: 'lastName',
			gridFlex: 1
		},{
			fieldLabel: _('Address'),
			name: 'address',
			gridFlex: 1
		},{
			fieldLabel: _('Birth Date'),
			name: 'birthDate',
			gridFlex: 1
		},
		{
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
		},
		{
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
			fieldLabel: _('E-mail'),
			name: 'email',
			gridFlex: 1
		},{
			fieldLabel: _('Login'),
			name: 'login',
			gridFlex: 0.8
		}
	],
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
					name: 'name',
					allowBlank: false,
					margin: '0 10 0 0'
				},{
					fieldLabel: _('Last Name'),
					xtype: 'textfield',
					name: 'lastName',
					allowBlank: false
				}
			]
		},
		{
			xtype: 'fieldcontainer',
			layout: 'hbox',
            defaults: {
				flex: 1,
				defaultMargins: {top: 0, right: 5, bottom: 0, left: 0}
			},
			items:[
				{
					fieldLabel: _('Address'),
					xtype: 'textfield',
					name: 'address',
					allowBlank: false,
					margin: '0 10 0 0'
				},{
					fieldLabel: _('Birth Date'),
					xtype: 'datefield',
					format: 'd/m/Y',
					name: 'birthDate',
					allowBlank: false
				}
			]
		},
		{
			xtype: 'fieldcontainer',
			layout: 'hbox',
            defaults: {
				flex: 1,
				defaultMargins: {top: 0, right: 5, bottom: 0, left: 0}
			},
			items:[
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
				},
				{
					fieldLabel: _('E-mail'),
					xtype: 'textfield',
					name: 'email',
					vtype: 'email',
					allowBlank: true
				}
			]
		},
		{
			xtype: 'fieldcontainer',
			layout: 'hbox',
            defaults: {
				flex: 1,
				defaultMargins: {top: 0, right: 5, bottom: 0, left: 0}
			},
			items:[
				{
					fieldLabel: _('Login'),
					xtype: 'textfield',
					margin: '0 10 0 0',
					name: 'login',
					maxLength: 20,
					allowBlank: false
				},{
					fieldLabel: _('Password'),
					xtype: 'textfield',
					name: 'password',
					maxLength: 30,
					inputType: 'password',
					allowBlank: true,
					columnHidden: true
				}
			]
		}]		
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
					flex: 1,
					defaultMargins: {top: 0, right: 5, bottom: 0, left: 0}
				},
				items:[
					{
						xtype: 'filefield',
						name: 'photo',
						fieldLabel: _('Photo'),
						labelWidth: 50,
						msgTarget: 'side',
						allowBlank: true,
						buttonText: _('Select Photo...')
					}
				]
			}
		]
	}]
});
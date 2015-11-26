Ext.define('ExtDesktop.view.Registrations.Category.CrudCategory', {
    extend: 'ExtDesktop.view.default.CrudPanel',
    alias: 'widget.crudcategory',
    closable: false,
	border: false,
	iconCls: 'x-icon-category-tab',
	title: _('Categories'),
	itemId: 'crudcategory',
	storeId: 'categoryStore',
	readUrl: 'category/listAll',
	deleteUrl: 'category/delete',
	createUpdateUrl: 'category/saveUpdate',
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
							margin: '0 10 0 0',
							xtype: 'textfield',
							name: 'name',
							allowBlank: false
						},
						{
							fieldLabel: _('Description'),
							xtype: 'textfield',
							name: 'description',
							allowBlank: false
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
		}
	]
});
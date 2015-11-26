Ext.define('ExtDesktop.view.Registrations.Advertisement.CrudAdvertisement', {
    extend: 'ExtDesktop.view.default.CrudPanel',
    alias: 'widget.crudadvertisement',
    closable: false,
	border: false,
	iconCls: 'x-icon-advertisement-tab',
	title: _('Advertisements'),
	storeId: 'advertisementStore',
	itemId: 'crudadvertisement',
	readUrl: 'advertisement/listAll',
	deleteUrl: 'advertisement/delete',
	createUpdateUrl: 'advertisement/saveUpdate',
	imageUrl: 'advertisement/loadImage',
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
		fieldLabel: _('Initial Date'),
		name: 'inicialDate',
		gridFlex: 1
	},{
		fieldLabel: _('Final Date'),
		name: 'finalDate',
		gridFlex: 1
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
							fieldLabel: _('Active'),
							name: 'active',
							margin: '0 10 0 0',
							xtype: 'checkboxfield',
							submitValue: true
						},
						{
							fieldLabel: _('Name'),
							xtype: 'textfield',
							name: 'name',
							allowBlank: false
						}
					]
				},{
					xtype: 'fieldcontainer',
					layout: 'hbox',
					defaults: {
						flex: 1
					},
					items:[
						{
							fieldLabel: _('Initial Date'),
							xtype: 'datefield',
							margin: '0 10 0 0',
							format: 'd/m/Y',
							name: 'inicialDate',
							allowBlank: false
						},{
							fieldLabel: _('Final Date'),
							xtype: 'datefield',
							format: 'd/m/Y',
							name: 'finalDate',
							allowBlank: false
						}
					]
				}
			]
		},{
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
							margin: '0 10 0 0',
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
Ext.define('ExtDesktop.view.default.CrudPanel', {
    extend: 'Ext.panel.Panel',
	requires: [
       'ExtDesktop.view.default.CrudGrid',
	   'ExtDesktop.view.default.CrudForm'
    ],
    alias: 'widget.crudpanel',
	itemId: '',
	storeId: '',
	fields: [],
	formFields: [],
	readUrl: '',
	deleteUrl: '',
	createUpdateUrl: '',
	imageUrl: '',
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
	border: false,
    initComponent: function() {
        var me = this, itemIdGrid = me.itemId+'grid', itemIdForm = me.itemId+'form', 
        	itemIdMedia = me.itemId+'media';
        Ext.applyIf(me, {
			items: [{
				xtype: 'panel',
				layout: {
       				type: 'hbox',
      				align: 'stretch'
  			    },
    			items: [{
					xtype: 'crudform',
					flex: 1,
					fields: me.fields,
					formFields: me.formFields,
					itemId: itemIdForm,
					createUpdateUrl: me.createUpdateUrl
				}]
			},{
				xtype: 'crudgrid',
				flex: 1.7,
				fields: me.fields,
				readUrl: me.readUrl,
				itemId: itemIdGrid,
				storeId: me.storeId,
				deleteUrl: me.deleteUrl
			}]
        });
        me.callParent(arguments);
    }

});
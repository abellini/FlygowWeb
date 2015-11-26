Ext.define('ExtDesktop.view.default.CrudForm', {
    extend: 'Ext.form.Panel',
    alias: 'widget.crudform',
	formFields: [],
	itemId: '',
	createUpdateUrl: '',
	border: false,
    initComponent: function() {
        var me = this, registerItemId = me.itemId+'register', resetItemId = me.itemId+'reset';
        Ext.applyIf(me, {
			layout: 'anchor',
			items: me.formFields,
			url: me.createUpdateUrl,
			fieldDefaults: {
				labelAlign: 'top',
				msgTarget: 'side'
			},
			defaults: {
				anchor: '100%'
			},
			padding: '10',
			buttons: [{
				text: _('Reset'),
				itemId: resetItemId,
				iconCls: 'x-icon-reset',
				handler: function() {
					var form = this.up('form').getForm();
					form.reset();
					var formFields = form.getFields().items;
					for(var i = 0; i < formFields.length; i++){
						var field = formFields[i];
						if(field.isDisabled()){
							field.setDisabled(false);
						}
					}
				}
			}, {
				text: _('Register'),
				iconCls: 'x-icon-register',
				itemId: registerItemId,
				formBind: true,
				disabled: true
			}],
			listeners: {
				beforeaction: function( cmp, action, eOpts ){
					var tab = this.up('panel');
					tab.getEl().mask(_('Saving...'), 'x-mask-load');
				},
				actioncomplete: function( cmp, action, eOpts ){
					var tab = this.up('panel');
					tab.getEl().unmask();
				},
				actionfailed: function( cmp, action, eOpts ){
					var tab = this.up('panel');
					tab.getEl().unmask();
				}	
			}
        });

        me.callParent(arguments);
    }

});
Ext.define('ExtDesktop.controller.Registrations.Coin.CoinController', {
    extend: 'Ext.app.Controller',
	itemId: 'crudcoin',
	// getters for views
    refs: [{
        selector: 'grid[itemId=crudcoingrid]',
        ref: 'gridCrud'
    },{
        selector: 'form[itemId=crudcoinform]',
        ref: 'formCrud'
    },{
		selector: 'button[itemId=crudcoingriddelete]',
        ref: 'btnDelete'
	}],
	init: function(){
		var me = this;
		me.control({
			'grid[itemId=crudcoingrid]' : {
                afterrender: me.onRenderGrid.bind(me)
            },
			'button[itemId=crudcoinformregister]': {
                click: me.onRegisterBtn.bind(me)
            },
            'button[itemId=crudcoinformreset]': {
				click: me.onResetBtn.bind(me)
			},
			'button[itemId=crudcoingriddelete]': {
                click: me.onDeleteBtn.bind(me)
            }
		})	
	},
	onRenderGrid: function(){
		var me = this, grid = me.getGridCrud(), selModel = grid.getSelectionModel();
		selModel.on('select', me.setFormValuesByGrid.bind(me));
		selModel.on('deselect', me.clearFormValuesByGrid.bind(me));
	},
	setFormValuesByGrid: function(selModel, eOpts){
		var me = this, record = selModel.getSelection()[0], 
			form = me.getFormCrud(), formCmp = form.getForm(), formFields = formCmp.getFields().items;
		for(var i = 0; i < formFields.length; i++){
			var field = formFields[i], value = record.data[field.name];
			field.setValue(value);
		}
	},
	clearFormValuesByGrid: function(selModel, eOpts){
		var me = this, form = me.getFormCrud();
		form.getForm().reset();
	},
	onResetBtn: function(){
		var me = this, grid = me.getGridCrud();
		grid.getSelectionModel().deselectAll();
	},
	onRegisterBtn: function(){
		var me = this, form = me.getFormCrud().getForm();
		if (form.isValid()) {
			form.submit({
				success: function(form, action) {
					me.getGridCrud().getStore().load();
					form.reset();
				},
				failure: function(form, action) {
					Ext.Msg.show({
						 title: _('Error'),
						 msg: action.result.message,
						 buttons: Ext.Msg.OK,
						 icon: Ext.Msg.ERROR
					});
				}
			});
			me.onResetBtn.bind(me);
		}
	},
	onDeleteBtn: function(){
		var me = this, grid = me.getGridCrud(), selModel = grid.getSelectionModel(), records = selModel.getSelection(), ids = '';
		if(records.length > 0){
			Ext.Msg.show({
				title: _('Delete Items'),
				msg: _('Are you sure you want to perform this action? It will not get back!'),
				buttons: Ext.Msg.YESNOCANCEL,
				icon: Ext.Msg.QUESTION,
				fn: function(id){
					if(id == 'yes'){
						for(var i = 0; i < records.length; i++){
							var rec = records[i];
							ids += rec.data['id'];
							if(i != records.length-1){
								ids += ','
							}
						}
						grid.getEl().mask(_('Removing...'), 'x-mask-load');
						Ext.Ajax.request({
							url: me.getBtnDelete().deleteUrl,
							params: {
								ids: ids
							},
							success: function(response){
								grid.getEl().unmask();
								grid.getStore().load();
							},
							failure: function(response) {
								grid.getEl().unmask();
								var resp = Ext.decode(response.responseText);
								Ext.Msg.show({
									 title: _('Error'),
									 msg: resp.message,
									 buttons: Ext.Msg.OK,
									 icon: Ext.Msg.ERROR
								});
							}
						});
					}
				}
			});
		}
	}
});

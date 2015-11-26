Ext.define('ExtDesktop.controller.Order.OrderController', {
    extend: 'Ext.app.Controller',
	// getters for views
    refs: [{
        selector: 'grid[itemId=gridorders]',
        ref: 'gridOrders'
    },{
        selector: 'form[itemId=orderform]',
        ref: 'orderForm'
    },{
		selector: 'button[itemId=readyProduct]',
        ref: 'btnReady'
	},{
		selector: 'button[itemId=cancelProduct]',
        ref: 'btnCancel'
	}],
	init: function(){
		var me = this;
		me.control({
			'grid[itemId=gridorders]' : {
                afterrender: me.onRenderGrid.bind(me),
				destroy: me.onDestroyGrid.bind(me)
            },
			'button[itemId=readyProduct]': {
                click: me.onReadyBtn.bind(me)
            },
			'button[itemId=cancelProduct]': {
                click: me.onCancelBtn.bind(me)
            }
		})	
	},
	connectToNewOrders: function() {
		var me = this, grid = me.getGridOrders();
    	var socket = new SockJS('/flygow/new-order');
    	var stompClientToNewOrders = Stomp.over(socket);
		grid.stompClientToNewOrders = stompClientToNewOrders;
    	stompClientToNewOrders.connect({}, function(frame) {
    		stompClientToNewOrders.subscribe('/clients/newOrder', function(data, me){
    			store = grid.getStore();
    			store.load({
					callback:function(records, operation, success){
						if(!success){
							try{
								Ext.Msg.alert(_('Error'), _('Error to retrieve orders from server!'));
							}catch(e){
								Ext.Msg.alert(_('Error'), _('Error to retrieve orders from server!'));
							}
						}
					}
				});
    		});
    	});
    },
    disconnectToNewOrders: function(){
		var me = this, grid = me.getGridOrders();
		grid.stompClientToNewOrders.disconnect();
    },
	onRenderGrid: function(){
		var me = this, grid = me.getGridOrders(), selModel = grid.getSelectionModel();
		selModel.on('select', me.setFormValuesByGrid.bind(me));
		selModel.on('deselect', me.clearFormValuesByGrid.bind(me));
		grid.connectToNewOrders = me.connectToNewOrders;
		grid.disconnectToNewOrders = me.disconnectToNewOrders;
		me.connectToNewOrders();
	},
	onDestroyGrid: function(){
		var me = this;
		me.disconnectToNewOrders();
	},
	setFormValuesByGrid: function(selModel, eOpts){
		var me = this, record = selModel.getSelection()[0], 
			form = me.getOrderForm(), formCmp = form.getForm(), formFields = formCmp.getFields().items;
		for(var i = 0; i < formFields.length; i++){
			var field = formFields[i], value = record.data[field.name];
			if(value instanceof Object){
				if(value.length > 1){
					var values = [];
					for(var y = 0; y < value.length; y++){
						if(y > 0){
							values.push(" " + value[y].name);
						}else{
							values.push(value[y].name);
						}
					}
					field.setValue(values);
				}else{
					if(typeof(value[0]) != 'undefined'){
						field.setValue(value[0].name);
					}
				}
			}else{
				field.setValue(value);
			}
		}
		var numberTablet = record.data['tabletNumber'];
		me.setPanelTabletNumberValue(numberTablet);
	},
	setPanelTabletNumberValue: function(numberTablet){
		if(numberTablet < 10){
			numberTablet = '0' + numberTablet;
		}
		var spanClass = '<span class = "number-table">' + numberTablet + '</span>';
		var panel = Ext.ComponentQuery.query('panel[itemId=numberTable]')[0];
		panel.body.update(spanClass);
	},
	resetPanelTabletNumberValue: function(){
		var panel = Ext.ComponentQuery.query('panel[itemId=numberTable]')[0];
		panel.body.update("");
	},
	clearFormValuesByGrid: function(selModel, eOpts){
		var me = this, form = me.getOrderForm();
		form.getForm().reset();
		me.resetPanelTabletNumberValue();
	},
	onReadyBtn: function(){
		var me = this, grid = me.getGridOrders(), selModel = grid.getSelectionModel();
		if(selModel.getSelection().length == 1){
			var record = selModel.getSelection()[0];
			Ext.Msg.show({
				title: _('Confirm'),
				msg: _('Are you sure you want to perform this action? It will not get back!'),
				buttons: Ext.Msg.YESNOCANCEL,
				icon: Ext.Msg.QUESTION,
				fn: function(id){
					if(id == 'yes'){
						grid.getEl().mask(_('Loading...'), 'x-mask-load');
						Ext.Ajax.request({
							url: 'order/itemReady',
							params: {
								orderItemId: record.data.id
							},
							success: function(response){
								grid.getEl().unmask();
								me.clearFormValuesByGrid();
								var store = Ext.data.StoreManager.lookup('OrderItems');
								store.getProxy().setExtraParam('strSearch', undefined);
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
	},
	onCancelBtn: function(){
		var me = this, grid = me.getGridOrders(), selModel = grid.getSelectionModel();
		if(selModel.getSelection().length == 1){
			var record = selModel.getSelection()[0];
			Ext.Msg.show({
				title: _('Confirm'),
				msg: _('Are you sure you want to perform this action? It will not get back!'),
				buttons: Ext.Msg.YESNOCANCEL,
				icon: Ext.Msg.QUESTION,
				fn: function(id){
					if(id == 'yes'){
						grid.getEl().mask(_('Loading...'), 'x-mask-load');
						Ext.Ajax.request({
							url: 'order/itemCancel',
							params: {
								orderItemId: record.data.id
							},
							success: function(response){
								grid.getEl().unmask();
								me.clearFormValuesByGrid();
								var store = Ext.data.StoreManager.lookup('OrderItems');
								store.getProxy().setExtraParam('strSearch', undefined);
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

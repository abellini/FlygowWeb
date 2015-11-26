Ext.define('ExtDesktop.view.default.CrudGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.crudgrid',
	storeId: '',
	itemId: '',
	fields: [],
	readUrl: '',
	deleteUrl: '',
	multiSelect: true,
    autoScroll: true,
	border: false,
	makeColumns: function(){
		var me = this, columns = [];
		for(var i = 0; i < me.fields.length; i++){
			var colObj = {};
			colObj.xtype = 'gridcolumn';
			colObj.dataIndex = me.fields[i].name;
			colObj.text = me.fields[i].fieldLabel;
			colObj.flex = me.fields[i].gridFlex;
			try{
				colObj.renderer = me.fields[i].columnRenderer;
			}catch(e){
				console.error('Error', e);
			}
			try{
				colObj.width = me.fields[i].columnWidth;
			}catch(e){
				console.error('Error', e);
			}
			try{
				colObj.hidden = me.fields[i].columnHidden;
			}catch(e){
				console.error('Error', e);
			}
			columns.push(colObj);
		}
		return columns;
	},
	changeMedia: function(record, type){
		var me = this, formId = me.itemId.slice(0, me.itemId.indexOf('grid')) + 'form', form = Ext.ComponentQuery.query('[itemId='+formId+']')[0]
		formCmp = form.getForm(), formFields = formCmp.getFields().items;
		for(var i = 0; i < formFields.length; i++){
			var field = formFields[i];
			if(field.name != type){
				field.setDisabled(true);
			}else{
				field.setDisabled(false);
			}	
		}
	},
	removeMedia: function(record, type){
		var grid = this, idController = grid.itemId.slice(4,grid.itemId.indexOf("grid")), url = idController + '/' + 'removeMedia';
		Ext.Msg.show({
			title: _('Remove Media'),
			msg: _('Are you sure you want to perform this action? It will not get back!'),
			buttons: Ext.Msg.YESNOCANCEL,
			icon: Ext.Msg.QUESTION,
			fn: function(id){
				if(id == 'yes'){
					grid.getEl().mask(_('Removing...'), 'x-mask-load');
					Ext.Ajax.request({
						url: url,
						params: {
							id: record.data['id'],
							type: type
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
	},
    initComponent: function() {
        var me = this, itemIdBtnDelete = me.itemId+'delete';
        Ext.applyIf(me, {
			store: Ext.create('Ext.data.JsonStore',{
				storeId: me.storeId,
				autoLoad: false,
				proxy: {
					type: 'ajax',
					url: me.readUrl,
					reader: {
						type: 'json',
						idProperty: 'id'
					}
				},
				fields: me.fields
			}),
			columns: me.makeColumns(),
			bbar: [
			{
				xtype: 'combobox',
				width: 230,
				enableKeyEvents: true,
				triggerCls: 'x-icon-search',
				onTriggerClick: function(){
					try{
						var txtSearch = this.getValue(); 
						if(typeof(txtSearch) != 'undefined' && txtSearch != '' && txtSearch != null){
							var store = this.up('grid').getStore();
							store.getProxy().setExtraParam('strSearch', txtSearch);
							store.load();
						}
					}catch(e){
						console.error(e);
					}
				},
				listeners: {
					specialkey: function(field, e){
                    // e.HOME, e.END, e.PAGE_UP, e.PAGE_DOWN,
                    // e.TAB, e.ESC, arrow keys: e.LEFT, e.RIGHT, e.UP, e.DOWN
						if (e.getKey() == e.ENTER) {
							field.onTriggerClick();
						}
					}
				},
				allowBlank: true
			},'->',{
				xtype: 'button',
				text: _('Refresh'),
				iconCls: 'x-icon-refresh',
				handler: function(){
					var store = this.up('grid').getStore();
					store.getProxy().setExtraParam('strSearch', undefined);
					store.load();
				}
			},{
				xtype: 'button',
				text: _('Delete'),
				itemId: itemIdBtnDelete,
				deleteUrl: me.deleteUrl,
				iconCls: 'x-icon-delete'
			}],
			listeners: {
				afterrender: function(){
					var me = this;
					me.getStore().load();
				},
				cellcontextmenu: function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
					var position = e.getXY(), me = this;
                    e.stopEvent();
					if ((typeof(record.data.photo) == 'undefined') && (typeof(record.data.video) != 'undefined')) {
					    if(record.data.video == 'OK'){
							var menu_grid = new Ext.menu.Menu({
								items: [
									{
										text: _('Manage Video'), menu:[
											{ text: _('Show Video'), handler: function() {
												var win = Ext.getCmp('mediaArea');
												if(typeof(win) == 'undefined'){
													var win = Ext.create('ExtDesktop.view.default.MediaArea', {
														media: 'Video', 
														crudName: me.itemId,
														record: record
													});
													win.show();
												}else{
													win.load('Video', me.itemId, record);
													win.show();
												}
											}},
											{ text: _('Change Video'), handler: function() {
												me.changeMedia(record, 'video');
											}},
											{ text: _('Remove Video'), handler: function() {
												me.removeMedia(record, 'video');
											}}
										]
									}
								]
							});
							menu_grid.showAt(position);
						}						
					} else if ((typeof(record.data.photo) != 'undefined') && (typeof(record.data.video) == 'undefined')) {
					    if(record.data.photo == 'OK'){
							var menu_grid = new Ext.menu.Menu({
								items: [
									{
										text: _('Manage Photo'), menu:[
											{ text: _('Show Photo'), handler: function() {
												var win = Ext.getCmp('mediaArea');
												if(typeof(win) == 'undefined'){
													var win = Ext.create('ExtDesktop.view.default.MediaArea', {
														media: 'Photo', 
														crudName: me.itemId,
														record: record
													});
													win.show();
												}else{
													win.load('Photo', me.itemId, record);
													win.show();
												}
											}},
											{ text: _('Change Photo'), handler: function() {
												me.changeMedia(record, 'photo');
											}},
											{ text: _('Remove Photo'), handler: function() {
												me.removeMedia(record, 'photo');
											}}
										]
									}	
								]
							});
							menu_grid.showAt(position);
						}						
					} else if ((typeof(record.data.photo) != 'undefined') && (typeof(record.data.video) != 'undefined')) {
						if(record.data.photo == 'OK' && record.data.video != 'OK'){
							var menu_grid = new Ext.menu.Menu({
								items: [
									{
										text: _('Manage Photo'), menu:[
											{ text: _('Show Photo'), handler: function() {
												var win = Ext.getCmp('mediaArea');
												if(typeof(win) == 'undefined'){
													var win = Ext.create('ExtDesktop.view.default.MediaArea', {
														media: 'Photo', 
														crudName: me.itemId,
														record: record
													});
													win.show();
												}else{
													win.load('Photo', me.itemId, record);
													win.show();
												}
											}},
											{ text: _('Change Photo'), handler: function() {
												me.changeMedia(record, 'photo');
											}},
											{ text: _('Remove Photo'), handler: function() {
												me.removeMedia(record, 'photo');
											}}
										]
									}	
								]
							});
							menu_grid.showAt(position);
						}else if (record.data.photo != 'OK' && record.data.video == 'OK'){
							var menu_grid = new Ext.menu.Menu({
								items: [
									{
										text: _('Manage Video'), menu:[
										
											{ text: _('Show Video'), handler: function() {
												var win = Ext.getCmp('mediaArea');
												if(typeof(win) == 'undefined'){
													var win = Ext.create('ExtDesktop.view.default.MediaArea', {
														media: 'Video', 
														crudName: me.itemId,
														record: record
													});
													win.show();
												}else{
													win.load('Video', me.itemId, record);
													win.show();
												}
											}},
											{ text: _('Change Video'), handler: function() {
												me.changeMedia(record, 'video');
											}},
											{ text: _('Remove Video'), handler: function() {
												me.removeMedia(record, 'video');
											}}
										]
									}	
								]
							});
							menu_grid.showAt(position);
						}else if(record.data.photo == 'OK' && record.data.video == 'OK'){
							var menu_grid = new Ext.menu.Menu({
								items: [
									{
										text: _('Manage Photo'), menu:[
											{ text: _('Show Photo'), handler: function() {
												var win = Ext.getCmp('mediaArea');
												if(typeof(win) == 'undefined'){
													var win = Ext.create('ExtDesktop.view.default.MediaArea', {
														media: 'Photo', 
														crudName: me.itemId,
														record: record
													});
													win.show();
												}else{
													win.load('Photo', me.itemId, record);
													win.show();
												}
											}},
											{ text: _('Change Photo'), handler: function() {
												me.changeMedia(record, 'photo');
											}},
											{ text: _('Remove Photo'), handler: function() {
												me.removeMedia(record, 'photo');
											}}
										]
									},
									{
										text: _('Manage Video'), menu:[
											{ text: 'Show Video', handler: function() {
												var win = Ext.getCmp('mediaArea');
												if(typeof(win) == 'undefined'){
													var win = Ext.create('ExtDesktop.view.default.MediaArea', {
														media: 'Video', 
														crudName: me.itemId,
														record: record
													});
													win.show();
												}else{
													win.load('Video', me.itemId, record);
													win.show();
												}
											}},
											{ text: _('Change Video'), handler: function() {
												me.changeMedia(record, 'video');
											}},
											{ text: _('Remove Video'), handler: function() {
												me.removeMedia(record, 'video');
											}}
										]
									}	
								]
							});
							menu_grid.showAt(position);
						}					 	
					}
				}
            }
        });
        me.callParent(arguments);
    }

});
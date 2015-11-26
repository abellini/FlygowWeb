Ext.define('ExtDesktop.view.default.GridOrders', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.gridorders',
	itemId: 'gridorders',
	multiSelect: false,
    autoScroll: true,
	border: false,
	imageLoadOn: 'staticResources/images/silk/table_refresh.png',
	imageLoadOff: 'staticResources/images/silk/table_delete.png',
	stringOn: _('ON'),
	stringOff: _('OFF'),
	changeAutoLoad: function(state){
		var me = this;
		var div = document.getElementById('autoRefreshMode');
		div.innerHTML = '';
		var img = document.getElementById('imageAutoLoad');
		if(state){
			img.src = me.imageLoadOn;
			div.innerHTML = '&nbsp;'+_('AutoLoad')+''+'<b>&nbsp;' + me.stringOn + '</b>';
		}else{
			img.src = me.imageLoadOff;
			div.innerHTML = '&nbsp;'+_('AutoLoad')+''+'<b>&nbsp;' + me.stringOff + '</b>';
		}
	},
    initComponent: function() {
        var me = this;
		
        Ext.applyIf(me, {
			itemId: me.itemId,
			store: Ext.data.StoreManager.lookup('OrderItems'),
			features: [
				{
					ftype:'grouping'
				}
			],
			columns: [
				{
					xtype: 'gridcolumn',
					text: '',
					flex: 0.2,
					renderer: function(value, metaData, record, rowIndex, colIndex, store, view){
						if(record.data['statusId'] == 1){
							return '<img src="staticResources/images/silk/shading.png" />';
						}else if (record.data['statusId'] == 2){
							return '<img src="staticResources/images/silk/accept.png" />';
						}else{
							return '<img src="staticResources/images/silk/cancel.png" />';
						}
					}
				},{
					xtype: 'gridcolumn',
					dataIndex: 'id',
					text: _('ID'),
					flex: 0.2
				},{
					xtype: 'gridcolumn',
					dataIndex: 'operationalArea',
					text: _('Operational Area'),
					hidden: true,
					flex: 1
				},{
					xtype: 'gridcolumn',
					dataIndex: 'orderid',
					text: _('Order'),
					hidden: true,
					flex: 1
				},{
					xtype: 'gridcolumn',
					dataIndex: 'food',
					text: _('Item'),
					flex: 1.5
				},{
					xtype: 'gridcolumn',
					dataIndex: 'quantity',
					text: _('Quantity'),
					flex: 0.5,
					renderer: function(value, metaData, record, rowIndex, colIndex, store, view){
						if(value < 10){
							value = '0' + value;
						}
						return value + ' ' + _('un.');
					}
				},{
					xtype: 'gridcolumn',
					dataIndex: 'accompaniments',
					text: _('Accompaniments'),
					renderer: function(value, metaData, record, rowIndex, colIndex, store, view){
						var val = '';
						for(var i = 0; i < value.length; i++){
							if(typeof(value[i].name) == 'undefined'){
								return value;
							}
							if(i != value.length - 1){
								val += value[i].name + ', ';
							}else{
								val += value[i].name;
							}
						}
						return val;
					},
					flex: 1
				},{
					xtype: 'gridcolumn',
					dataIndex: 'observations',
					text: _('Observations'),
					flex: 1.8
				},{
					xtype: 'gridcolumn',
					dataIndex: 'value',
					text: _('Value'),
					flex: 0.5
				},{
					xtype: 'gridcolumn',
					dataIndex: 'iniorderdate',
					text: _('Date'),
					flex: 0.7
				},{
					xtype: 'gridcolumn',
					dataIndex: 'status',
					text: _('Status'),
					flex: 1,
					renderer: function(value, metaData, record, rowIndex, colIndex, store, view){
						return _(value);
					}
				}
			],
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
							var grid = Ext.ComponentQuery.query('grid[itemId=gridorders]')[0];
							
							grid.disconnectToNewOrders.bind(grid);
							
							grid.changeAutoLoad(false);
							
							var store = Ext.data.StoreManager.lookup('OrderItems');
							store.getProxy().setExtraParam('strSearch', txtSearch);
							store.load();
							
							var form = Ext.ComponentQuery.query('form[itemId=orderform]')[0];
							form.getForm().reset();
							
							var panel = Ext.ComponentQuery.query('panel[itemId=numberTable]')[0];
							panel.body.update("");
							
							grid.selModel.deselectAll();
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
			},
			'<img src="staticResources/images/silk/shading.png">&nbsp;' + '<b>' + _('In Attendance') + '</b>' + '&nbsp;&nbsp;&nbsp;<img src="staticResources/images/silk/accept.png">&nbsp;' + '<b>' + _('Accept') + '</b>' +'&nbsp;&nbsp;&nbsp;<img src="staticResources/images/silk/cancel.png">&nbsp;' + '<b>' + _('Cancel') + '</b>',
			'->',
			'<img id="imageAutoLoad" src="' + me.imageLoadOn + '" /><div id="autoRefreshMode" class="autoRefreshMode">&nbsp;' + _('AutoLoad') + '<b>&nbsp;' + me.stringOn + '</b></div>',
			{
				xtype: 'button',
				text: _('Refresh'),
				iconCls: 'x-icon-refresh',
				handler: function(){
					var store = Ext.data.StoreManager.lookup('OrderItems');
					store.getProxy().setExtraParam('strSearch', undefined);
					store.load();
					
					var form = Ext.ComponentQuery.query('form[itemId=orderform]')[0];
					form.getForm().reset();
					
					var panel = Ext.ComponentQuery.query('panel[itemId=numberTable]')[0];
					panel.body.update("");
					
					var grid = Ext.ComponentQuery.query('grid[itemId=gridorders]')[0];
					grid.selModel.deselectAll();
					
					grid.connectToNewOrders.bind(grid);

					grid.changeAutoLoad(true);
				}
			}],
			listeners: {
				afterrender: function(){
					var me = this;
					me.getStore().load();
				}
            }
        });
        me.callParent(arguments);
    }

});
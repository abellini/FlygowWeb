Ext.define('ExtDesktop.view.ControlPanel.Accounts.GridAccounts', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.gridaccounts',
    itemId: 'gridaccounts',
    border: false,
    multiSelect: true,
    autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            itemId: me.itemId,
            store: Ext.data.StoreManager.lookup('AccountItems'),
            features: [
                {
                    ftype:'grouping'
                }
            ],
            listeners: {
                afterrender: function(){
                    var me = this;
                    me.getStore().load();
                }
            },
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
                                var grid = Ext.ComponentQuery.query('grid[itemId=gridaccounts]')[0];

                                grid.changeAutoLoad(false);

                                var store = Ext.data.StoreManager.lookup('OrderItems');
                                store.getProxy().setExtraParam('strSearch', txtSearch);
                                store.load();

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
                }
            ]
        });

        me.callParent(arguments);
    }
});
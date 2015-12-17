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
                    flex: 1.1
                },{
                    xtype: 'gridcolumn',
                    dataIndex: 'tabletNumber',
                    text: _('Tablet Number'),
                    hidden: true,
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
            tbar: [
                _('Tablet Number') + ': ',
                {
                    xtype: 'combobox',
                    width: 70,
                    enableKeyEvents: true,
                    triggerCls: 'x-icon-search',
                    itemId: 'combobox-tabletNumber',
                    allowBlank: true,
                    maskRe: /[0-9]/
                },
                '|',
                _('From') + ': ',
                {
                    xtype: 'datefield',
                    width: 110,
                    itemId: 'datefield-dateFrom',
                    maxValue: new Date(),
                    editable: false,
                    value: Ext.Date.add(new Date(), Ext.Date.DAY, -1),
                    format: 'd/m/Y',
                },
                 _('To') + ': ',
                 {
                     xtype: 'datefield',
                     width: 110,
                     itemId: 'datefield-dateTo',
                     value: new Date(),
                     editable: false,
                     maxValue: new Date(),
                     format: 'd/m/Y',
                 },
                 '|',
                 {
                     xtype     : 'radiofield',
                     boxLabel  : _('In attendance'),
                     name      : 'orderItemStatus',
                     inputValue: '1',
                     checked   : true,
                     itemId    : 'radio-inAttendance'
                 }, {
                     xtype     : 'radiofield',
                     boxLabel  : _('Accept'),
                     name      : 'orderItemStatus',
                     inputValue: '2',
                     itemId    : 'radio-accept'
                 }, {
                     xtype     : 'radiofield',
                     boxLabel  : _('Cancel'),
                     name      : 'orderItemStatus',
                     inputValue: '3',
                     itemId    : 'radio-cancel'
                 },
                 '->',
                 {
                    xtype: 'button',
                    text: _('Clean Filters'),
                    itemId: 'btnAccountsClearFilters'
                 },
                 {
                    xtype: 'button',
                    text: _('Search'),
                    itemId: 'btnAccountsSearch'
                 }
            ]
        });

        me.callParent(arguments);
    }
});
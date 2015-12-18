Ext.define('ExtDesktop.controller.ControlPanel.Accounts.GridAccountsController', {
    extend: 'Ext.app.Controller',
	// getters for views
    refs: [{
        selector: 'combobox[itemId=combobox-tabletNumber]',
        ref: 'comboboxTabletNumber'
    },{
        selector: 'grid[itemId=gridaccounts]',
        ref: 'gridAccounts'
    },{
        selector: 'datefield[itemId=datefield-dateFrom]',
        ref: 'datefieldDateFrom'
    },{
        selector: 'datefield[itemId=datefield-dateTo]',
        ref: 'datefieldDateTo'
    },{
		selector: 'radiofield[itemId=radio-inAttendance]',
        ref: 'radioInAttendance'
	},{
        selector: 'radiofield[itemId=radio-accept]',
        ref: 'radioAccept'
    },{
        selector: 'radiofield[itemId=radio-cancel]',
         ref: 'radioCancel'
    },{
		selector: 'button[itemId=btnAccountsClearFilters]',
        ref: 'btnAccountsClearFilters'
	},{
        selector: 'button[itemId=btnAccountsSearch]',
        ref: 'btnAccountsSearch'
    }],
    filters: {
        dateFrom: '',
        dateTo: '',

    },
	init: function(){
		var me = this;
		me.control({
		    'combobox[itemId=combobox-tabletNumber]': {
		        specialkey: me.onSpecialkey.bind(me)
		    },
			'grid[itemId=gridaccounts]': {
                afterrender: me.onAfterRender.bind(me),
                select: me.loadRecord.bind(me),
                deselect: me.unloadRecord.bind(me)
            },
			'button[itemId=btnAccountsClearFilters]': {
                click: me.onAccountsClearFiltersBtn.bind(me)
            },
            'button[itemId=btnAccountsSearch]': {
                click: me.onAccountsSearchBtn.bind(me)
            }
		})
	},
	loadRecord: function(el, record, index, eOpts){
        var form = Ext.ComponentQuery.query('form[itemId=accountdetailspanel]')[0];
        form.loadRecord(record);
	},
	unloadRecord: function(el, record, index, eOpts){
        var form = Ext.ComponentQuery.query('form[itemId=accountdetailspanel]')[0];
        form.getForm().reset();
	},
	onTriggerClick: function(){
        var me = this;
        try{
            var txtSearch = me.getComboboxTabletNumber().getValue();
            if(typeof(txtSearch) != 'undefined' && txtSearch != '' && txtSearch != null){
                var grid = me.getGridAccounts();


                var store = grid.getStore();
                store.getProxy().setExtraParam('strSearch', txtSearch);
                store.load();

                grid.selModel.deselectAll();
            }
        }catch(e){
            console.error(e);
        }
    },
	onSpecialkey: function(field, e){
	    var me = this;
	    // e.HOME, e.END, e.PAGE_UP, e.PAGE_DOWN,
        // e.TAB, e.ESC, arrow keys: e.LEFT, e.RIGHT, e.UP, e.DOWN
        if (e.getKey() == e.ENTER) {
            me.onTriggerClick();
        }
	},
	onAfterRender: function(grid, eOpts){
        grid.getStore().load();
	},
	onAccountsClearFiltersBtn: function(){
        var me = this;

        me.getComboboxTabletNumber().reset();

        me.getDatefieldDateFrom().reset();
        me.getDatefieldDateTo().reset();
        me.getGridAccounts().getStore().load();

        me.getRadioInAttendance().reset();
        me.getRadioAccept().reset();
        me.getRadioCancel().reset();
	},
	getOrderState(){
	    var me = this;
	    var inAttendanceFieldData = me.getRadioInAttendance().getValue();
        var acceptFieldData = me.getRadioAccept().getValue();
        var cancelFieldData = me.getRadioCancel().getValue();

        if(inAttendanceFieldData){
            return me.getRadioInAttendance().getSubmitValue();
        } else if (acceptFieldData){
            return me.getRadioAccept().getSubmitValue();
        } else if (cancelFieldData){
            return me.getRadioCancel().getSubmitValue();
        }
	},
	onAccountsSearchBtn: function(){
	    var me = this;

	    var tabletNumberFieldData = me.getComboboxTabletNumber().getValue() || "";
	    var dateFromFieldData = me.getDatefieldDateFrom().getSubmitValue();
	    var dateToFieldData = me.getDatefieldDateTo().getSubmitValue();
        var orderState = me.getOrderState();

        var grid = me.getGridAccounts();

        try{
            var store = grid.getStore();
            store.getProxy().setExtraParam('strSearch', tabletNumberFieldData);
            store.getProxy().setExtraParam('dateIniStr', dateFromFieldData);
            store.getProxy().setExtraParam('dateEndStr', dateToFieldData);
            store.getProxy().setExtraParam('statusId', orderState);

            store.load();

            grid.selModel.deselectAll();
        }catch(e){
            console.error(e);
        }

	}
})
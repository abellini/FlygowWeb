Ext.define('ExtDesktop.view.module.Orders', {
    extend: 'Ext.ux.desktop.Module',

    requires: [
        'ExtDesktop.view.Orders.OrdersPanel'
    ],

    id: 'Orders-win',

    init: function() {
        this.launcher = {
            text: _('Orders'),
			//Define icon style of start menu
            iconCls:'order-icon-shortcut'
        };
    },

    createWindow: function() {
		var me = this;
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow(me.id);
        if(!win){
            win = desktop.createWindow({
                id: me.id,
                closeAction:'hide',
                title: _('Orders'),
				iconCls: 'order-icon-shortcut',
				maximized: true,
                layout:{
					type:'fit'
				},
                items:[{
                    xtype:'orderspanel',
                    flex:1
                }]
            });
        }
        return win;
    }

});
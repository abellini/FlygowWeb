Ext.define('ExtDesktop.view.module.ControlPanel', {
    extend: 'Ext.ux.desktop.Module',

    requires: [
        'ExtDesktop.view.ControlPanel.ControlPanelPanel'
    ],

    id: 'ControlPanel-win',

    init: function() {
        this.launcher = {
            text: _('Control Panel'),
			//Define icon style of start menu
            iconCls:'control-panel-icon-shortcut'
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
                title: _('Control Panel'),
				iconCls: 'control-panel-icon-shortcut',
				maximized: true,
                layout:{
					type:'fit'
				},
                items:[{
                    xtype:'controlpanelpanel',
                    flex:1
                }]
            });
        }
        return win;
    }

});
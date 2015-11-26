Ext.define('ExtDesktop.view.module.Registrations', {
    extend: 'Ext.ux.desktop.Module',

    requires: [
        'ExtDesktop.view.Registrations.RegistrationsPanel'
    ],

    id: 'Registrations-win',

    init: function() {
        this.launcher = {
            text: _('Registrations'),
			//Define icon style of start menu
            iconCls:'registration-icon-shortcut'
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
                title: _('Registrations'),
				iconCls: 'registration-icon-shortcut',
				maximized: true,
                layout:{
					type:'fit'
				},
                items:[{
                    xtype:'registrationspanel',
                    flex:1
                }]
            });
        }
        return win;
    }

});
Ext.define('ExtDesktop.view.Desktop', {
    extend: 'Ext.ux.desktop.App',
    alias: 'widget.mydesktop',

    requires: [
        'Ext.window.MessageBox',
        'Ext.ux.desktop.ShortcutModel'
    ],

    init: function() {
        this.callParent();
    },

    /*onSettings: function() {
        Ext.widget('window',{
            title: 'Settings',
            width: 200,
            height: 200
        }).show();
    },*/
	
	getModulesByServer: function(){
		var modules = [];
		if(loggedUser.modules.length > 0){
			for(var i = 0; i < loggedUser.modules.length; i++){
				var module = loggedUser.modules[i];
				modules.push('ExtDesktop.view.module.'+module.name);
			}
		}
		return modules;
	},
	
    getModules: function() {
        var priv = {
            modules: this.getModulesByServer(),
            shortcuts: loggedUser.modules
        };

        var modules = priv.modules;

        var modulesAux = [];

        Ext.each(modules, function(item) {
            modulesAux.push(Ext.create(item));
        }, this);

        return modulesAux;
    },

    getDesktopConfig: function() {
        var me = this, ret = me.callParent();

        var priv = {
            modules: this.getModulesByServer(),
            shortcuts: loggedUser.modules
        };

        Ext.each(priv.shortcuts, function(item) {
            item.name = _(''+item.name)
        }, this);

        return Ext.apply(ret, {

            /*contextMenuItems: [
                { text: 'Change Settings', handler: me.onSettings, scope: me }
            ],*/

            shortcuts: Ext.create('Ext.data.Store', {
                model: 'Ext.ux.desktop.ShortcutModel',
                data: priv.shortcuts
            }),

            wallpaper: 'resources/wallpapers/' + loggedUser.wallpaperImageName,
            wallpaperStretch: false
        });
    },

    getStartConfig: function() {
        var me = this, ret = me.callParent();

        return Ext.apply(ret, {
            title: loggedUser.name,
            iconCls: 'user',
            height: 300,
            toolConfig: {
                width: 100,
                items: [
                    /*{
                        text:'Settings',
                        iconCls:'settings',
                        handler: me.onSettings,
                        scope: me
                    },
                    '-',*/
                    {
                        text:_('Logout'),
                        iconCls:'logout',
                        handler: me.onLogout,
                        scope: me
                    }
                ]
            }
        });
    },

    getTaskbarConfig: function() {
        var ret = this.callParent();

        return Ext.apply(ret, {
            trayItems: [
                { xtype: 'trayclock', flex: 1 }
            ]
        });
    },

    onLogout: function() {
        Ext.Msg.confirm(_('Logout'), _('Are you sure you want to logout?'), function(btn){
			if(btn == 'yes'){
				Ext.Ajax.request({
					url: '/flygow/logout',
					method: 'POST',
					success: function(response){
						window.location.replace("index");
					}
				});
			}
		});
    }

});
/*!
 * Ext JS Library 4.0
 * Copyright(c) 2006-2011 Sencha Inc.
 * licensing@sencha.com
 * http://www.sencha.com/license
 */

/**
 * @class Ext.ux.desktop.TaskBar
 * @extends Ext.toolbar.Toolbar
 */
Ext.define('Ext.ux.desktop.TaskBar', {
    // This must be a toolbar. we rely on acquired toolbar classes and inherited toolbar methods for our
    // child items to instantiate and render correctly.
    extend: 'Ext.toolbar.Toolbar',

    requires: [
        'Ext.button.Button',
        'Ext.resizer.Splitter',
        'Ext.menu.Menu',

        'Ext.ux.desktop.StartMenu'
    ],

    alias: 'widget.taskbar',

    cls: 'ux-taskbar',

    /**
     * @cfg {String} startBtnText
     * The text for the Start Button.
     */
    startBtnText: _('Start'),

    alertSize: 0,

    currentDisplayedAlerts: [],

    initComponent: function () {
        var me = this;
        
        me.startMenu = new Ext.ux.desktop.StartMenu(me.startConfig);

        me.quickStart = new Ext.toolbar.Toolbar(me.getQuickStart());

        me.windowBar = new Ext.toolbar.Toolbar(me.getWindowBarConfig());

        me.tray = new Ext.toolbar.Toolbar(me.getTrayConfig());

        me.items = [
            {
                xtype: 'button',
                cls: 'ux-start-button',
                iconCls: 'ux-start-button-icon',
                menu: me.startMenu,
                menuAlign: 'bl-tl',
                text: me.startBtnText
            },
            me.quickStart,
            {
                xtype: 'splitter', html: '&#160;',
                height: 14, width: 2, // TODO - there should be a CSS way here
                cls: 'x-toolbar-separator x-toolbar-separator-horizontal'
            },
            me.windowBar,
            '-',
            me.tray
        ];

        me.callParent();
        me.updateNotificationArea(true);
    },

    afterLayout: function () {
        var me = this;
        me.callParent();
        me.windowBar.el.on('contextmenu', me.onButtonContextMenu, me);
    },

    updateNotificationArea: function(isFirst, fromWebSocket){
        var me = this;
        me.giveMeTheGeneralAlerts(isFirst, fromWebSocket);
    },
    /**
        Método para definir qual o tipo de chamado do alerta solicitado
    */
    defineTypeOfCall: function(alertType){
        if(alertType === 'TO_PAYMENT'){
            return _('to') + ' <b>' + _('REALIZE THE PAYMENT') + '</b>';
        }
        return "";
    },
    /**
        Método que recebe o objeto de alerta e formata para um texto amigável a ser apresentado em tela.
    */
    formatAlertText: function(alert){
        var me = this;
        return _('The tablet number') + ' <b>' + alert.tabletNumber + '</b> ' + _('requires the attendant') + ' <b>' + alert.attendantName + '</b> ' + ' ' + me.defineTypeOfCall(alert.type);
    },
    formatNotificationTitle: function(alertHour){
        return _('Called at') + ': ' + Ext.Date.format(new Date(alertHour), 'd/m/Y H:i:s');
    },
    buildNotificationBox: function(notificationAlertObj){
        var me = this;
        var notificationConfig = {
            position: 'br',
            iconCls: 'ux-notification-icon-information',
            cls: 'ux-notification-light',
            autoCloseDelay: 15000,
            closable: true,
            html: me.formatAlertText(notificationAlertObj),
            alertId: notificationAlertObj.id,
            slideInAnimation: 'bounceOut',
            slideBackAnimation: 'easeIn',
            title: me.formatNotificationTitle(notificationAlertObj.alertHour),
            header: {
                titlePosition: 1,
                items: [
                    {
                        xtype: 'button',
                        cls: 'x-icon-register',
                        alertId: notificationAlertObj.id,
                        style: {
                            'border': 'none',
                            'margin-right': '7px'
                        },
                        handler: function(){
                            var alertId = this.alertId;
                            me.readNotification(alertId);
                            me.closeDisplayedNotification(alertId);
                        }
                    }
                ]
            },
            listeners: {
                'render': function(panel) {
                    panel.setBodyStyle('cursor', 'pointer');
                    panel.body.on('click', function() {
                        var controlPanel = myDesktopApp.getModule('ControlPanel-win');
                        controlPanel.createWindow().show();
                        var controlPanelController = _myAppGlobal.getController('ControlPanel.ControlPanelController');
                        controlPanelController.onControlTabAlertsBtn();
                    });
                }
            }
        };
        var createdNotificationObj = Ext.create('widget.uxNotification', notificationConfig);
        me.currentDisplayedAlerts.push(createdNotificationObj);
        createdNotificationObj.show();
    },
    closeAllDisplayedNotifications: function(){
        var me = this;
        for(var i = 0; i < me.currentDisplayedAlerts.length; i++){
            var notification = me.currentDisplayedAlerts[i];
            notification.close();
        }
    },
    closeDisplayedNotification: function(alertId){
        var me = this;
        for(var i = 0; i < me.currentDisplayedAlerts.length; i++){
            var notification = me.currentDisplayedAlerts[i];
            if(notification.alertId == alertId){
                notification.close();
                return;
            }
        }
    },
    /**
        Metodo que retorna todos os alertas pendentes para exibição no click do ícone de
        notificação na área de trabalho.
    */
    showAllNotifications: function(alerts){
        var me = this;
        me.closeAllDisplayedNotifications();
        me.currentDisplayedAlerts.length = 0;
        for(var i = alerts.length; i >=0 ; i--){
            try{
                var notificationAlertObj = alerts[i];
                me.buildNotificationBox(notificationAlertObj);
            }catch(e){}
        }
    },
    insertNotificationIcon: function(data){
        var me = this;
        var notificationIcon = me.insert(me.items.length-1, '<div id="notificationarea" style="cursor:pointer;"><img src="/flygow/serverApp/resources/images/notification.png"><span style="-moz-border-radius: 30px; -webkit-border-radius: 30px; border-radius: 30px; background-color:#6b0114;  padding: 4px; color: white;font-size: 12px;  position: relative;top: -6px;left: -11px;z-index: -1;">' + data.length + '</span></div>');
        notificationIcon.getEl().on('click', function(){
            me.showAllNotifications(data)
        });
        me.doLayout();
    },
    readNotification: function(alertId){
        var me = this;
        Ext.Ajax.request({
            url: 'attendant/readNotification/'+alertId,
            success: function(response){
                var resp = Ext.decode(response.responseText);
                me.alertSize = resp.length;
                me.doLayout();
                me.remove(me.items.length-2);
                me.doLayout();
                if(me.alertSize > 0){
                    me.insertNotificationIcon(resp);
                }
                try{
                    var dataview = Ext.getCmp('alerts');
                    dataview.getStore().load();
                    dataview.refresh();

                    var storeLastAlertsStatus = Ext.data.StoreManager.lookup('LastAlerts');
                    storeLastAlertsStatus.reload();
                }catch(e){}
            },
            failure: function(response) {}
        });
    },
    /**
        Metodo que carrega a area de alertas de sistema na barra de tarefas
    */
    giveMeTheGeneralAlerts: function(isFirst, fromWebSocket){
        var me = this;
        Ext.Ajax.request({
            url: 'attendant/listPendentAlerts',
            success: function(response){
                var resp = Ext.decode(response.responseText);
                if(fromWebSocket || me.alertSize < resp.length){
                    me.doLayout();
                    if(me.items.length > 5){
                        me.remove(me.items.length-2);
                        me.doLayout();
                    }
                    if(resp.length > 0){
                        me.insertNotificationIcon(resp);
                    }
                    if(!isFirst){
                        //MOSTRA OS BALOES DE NOVAS NOTIFICAÇOES NO CANTO INFERIOR DIREITO DA TELA
                        me.buildNotificationBox(resp[0]);
                        me.alertSize = resp.length;
                    }
                }
            },
            failure: function(response) {
                var notificationConfig = {
                    position: 'br',
                    cls: 'ux-notification-light',
                    iconCls: 'ux-notification-icon-error',
                    autoCloseDelay: 30000,
                    closable: true,
                    html: _('Error to get system alerts'),
                    slideInAnimation: 'bounceOut',
                    slideBackAnimation: 'easeIn'
                };
                Ext.create('widget.uxNotification', notificationConfig).show();
            }
        });
    },

    /**
     * This method returns the configuration object for the Quick Start toolbar. A derived
     * class can override this method, call the base version to build the config and
     * then modify the returned object before returning it.
     */
    getQuickStart: function () {
        var me = this, ret = {
            minWidth: 20,
            width: Ext.themeName === 'neptune' ? 70 : 60,
            items: [],
            enableOverflow: true
        };

        Ext.each(this.quickStart, function (item) {
            ret.items.push({
                tooltip: { text: item.name, align: 'bl-tl' },
                //tooltip: item.name,
                overflowText: item.name,
                iconCls: item.iconCls,
                module: item.module,
                handler: me.onQuickStartClick,
                scope: me
            });
        });

        return ret;
    },

    /**
     * This method returns the configuration object for the Tray toolbar. A derived
     * class can override this method, call the base version to build the config and
     * then modify the returned object before returning it.
     */
    getTrayConfig: function () {
        var ret = {
            items: this.trayItems
        };
        delete this.trayItems;
        return ret;
    },

    getWindowBarConfig: function () {
        return {
            flex: 1,
            cls: 'ux-desktop-windowbar',
            items: [ '&#160;' ],
            layout: { overflowHandler: 'Scroller' }
        };
    },

    getWindowBtnFromEl: function (el) {
        var c = this.windowBar.getChildByElement(el);
        return c || null;
    },

    onQuickStartClick: function (btn) {
        var module = this.app.getModule(btn.module),
            window;

        if (module) {
            window = module.createWindow();
            window.show();
        }
    },
    
    onButtonContextMenu: function (e) {
        var me = this, t = e.getTarget(), btn = me.getWindowBtnFromEl(t);
        if (btn) {
            e.stopEvent();
            me.windowMenu.theWin = btn.win;
            me.windowMenu.showBy(t);
        }
    },

    onWindowBtnClick: function (btn) {
        var win = btn.win;

        if (win.minimized || win.hidden) {
            btn.disable();
            win.show(null, function() {
                btn.enable();
            });
        } else if (win.active) {
            btn.disable();
            win.on('hide', function() {
                btn.enable();
            }, null, {single: true});
            win.minimize();
        } else {
            win.toFront();
        }
    },

    addTaskButton: function(win) {
        var config = {
            iconCls: win.iconCls,
            enableToggle: true,
            toggleGroup: 'all',
            width: 140,
            margins: '0 2 0 3',
            text: Ext.util.Format.ellipsis(win.title, 20),
            listeners: {
                click: this.onWindowBtnClick,
                scope: this
            },
            win: win
        };

        var cmp = this.windowBar.add(config);
        cmp.toggle(true);
        return cmp;
    },

    removeTaskButton: function (btn) {
        var found, me = this;
        me.windowBar.items.each(function (item) {
            if (item === btn) {
                found = item;
            }
            return !found;
        });
        if (found) {
            me.windowBar.remove(found);
        }
        return found;
    },

    setActiveButton: function(btn) {
        if (btn) {
            btn.toggle(true);
        } else {
            this.windowBar.items.each(function (item) {
                if (item.isButton) {
                    item.toggle(false);
                }
            });
        }
    }
});

/**
 * @class Ext.ux.desktop.TrayClock
 * @extends Ext.toolbar.TextItem
 * This class displays a clock on the toolbar.
 */
Ext.define('Ext.ux.desktop.TrayClock', {
    extend: 'Ext.toolbar.TextItem',

    alias: 'widget.trayclock',

    cls: 'ux-desktop-trayclock',

    html: '&#160;',

    timeFormat: 'g:i A',

    tpl: '{time}',

    initComponent: function () {
        var me = this;

        me.callParent();

        if (typeof(me.tpl) == 'string') {
            me.tpl = new Ext.XTemplate(me.tpl);
        }
    },

    afterRender: function () {
        var me = this;
        Ext.Function.defer(me.updateTime, 100, me);
        me.callParent();
    },

    onDestroy: function () {
        var me = this;

        if (me.timer) {
            window.clearTimeout(me.timer);
            me.timer = null;
        }

        me.callParent();
    },

    updateTime: function () {
        var me = this, time = Ext.Date.format(new Date(), me.timeFormat),
            text = me.tpl.apply({ time: time });
        if (me.lastText != text) {
            me.setText(text);
            me.lastText = text;
        }
        me.timer = Ext.Function.defer(me.updateTime, 10000, me);
    }
});

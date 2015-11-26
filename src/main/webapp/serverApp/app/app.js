Ext.Loader.setConfig({
    enabled: true,
    paths: {
        'Ext.ux.desktop': './desktop',
        'Chart.ux': './app/chart'
    }
});

function connectToAttendantAlerts(context) {
	var socket = new SockJS('/flygow/receive-attendant-alerts');
	var stompClientToAttendantAlerts = Stomp.over(socket);
	context.stompClientToAttendantAlerts = stompClientToAttendantAlerts;
	stompClientToAttendantAlerts.connect({}, function(frame) {
		stompClientToAttendantAlerts.subscribe('/clients/receiveNewAlert', function(data){
			myDesktopApp.desktop.taskbar.updateNotificationArea();
			try{
				var dataview = Ext.getCmp('alerts');
				dataview.getStore().load();
				dataview.refresh();

				var storeLastAlertsStatus = Ext.data.StoreManager.lookup('LastAlerts');
				storeLastAlertsStatus.reload();
			}catch(e){}
		});
	});
}
var myDesktopApp;
Ext.application({
	
	launch: function() {
		_myAppGlobal = this;
		connectToAttendantAlerts(_myAppGlobal);
	},

    requires: [
        'Ext.Loader',
        'ExtDesktop.view.Desktop',
    ],
	stores:[
		'OrderItems',
		'AlertItems',
		'LastAlerts',
		'LastAlertsByTime'
	],
    controllers: [
        'Desktop',
        'Registrations.Coin.CoinController',
        'Registrations.Advertisement.AdvertisementController',
		'Registrations.Category.CategoryController',
		'Registrations.PaymentForm.PaymentFormController',
		'Registrations.OperationalArea.OperationalAreaController',
		'Registrations.Accompaniment.AccompanimentController',
		'Registrations.Food.FoodController',
		'Registrations.Promotion.PromotionController',
		'Registrations.Attendant.AttendantController',
		'Registrations.Role.RoleController',
		'Registrations.Tablet.TabletController',
		'Order.OrderController',
		'ControlPanel.ControlPanelController'
    ],
    name: 'ExtDesktop'
});

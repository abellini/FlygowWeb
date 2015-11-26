Ext.define('ExtDesktop.view.Registrations.RegistrationsPanel', {
    extend: 'Ext.tab.Panel',
	requires: [
        'ExtDesktop.view.Registrations.Coin.CrudCoin',
        'ExtDesktop.view.Registrations.Advertisement.CrudAdvertisement',
		'ExtDesktop.view.Registrations.Category.CrudCategory',
		'ExtDesktop.view.Registrations.PaymentForm.CrudPaymentForm',
		'ExtDesktop.view.Registrations.OperationalArea.CrudOperationalArea',
		'ExtDesktop.view.Registrations.Accompaniment.CrudAccompaniment',
		'ExtDesktop.view.Registrations.Food.CrudFood',
		'ExtDesktop.view.Registrations.Promotion.CrudPromotion',
		'ExtDesktop.view.Registrations.Attendant.CrudAttendant',
		'ExtDesktop.view.Registrations.Role.CrudRole',
		'ExtDesktop.view.Registrations.Tablet.CrudTablet'
    ],
    alias: 'widget.registrationspanel',
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
			activeTab: 0,
			items: [
				{
					xtype: 'crudrole'
				},
				{
					xtype: 'crudattendant'
				},
				{
					xtype: 'crudcoin'					
				},
				{
					xtype: 'crudadvertisement'					
				},
				{
					xtype: 'crudtablet'
				},
				{
					xtype: 'crudcategory'					
				},
				{
					xtype: 'crudpaymentform'					
				},
				{
					xtype: 'crudoperationalarea'					
				},
				{
					xtype: 'crudaccompaniment'					
				},
				{
					xtype: 'crudfood'
				},
				{
					xtype: 'crudpromotion'
				}
			]
        });

        me.callParent(arguments);
    }

});
Ext.define('ExtDesktop.view.default.MediaArea', {
    extend: 'Ext.window.Window',
    alias: 'widget.mediaarea',
	itemId: 'mediaArea',
	urlPhotoPrefix: 'getPhoto',
	urlVideoPrefix: 'getVideo',
	media: '',
	crudName: '',
	record: null,
	title: _('Media Area'),
    height: 400,
    width: 400,
	resizable: false,
    layout: 'fit',
	load: function(media, crudName, record){
		var me = this;
		me.removeAll();
		me.media = media;
		me.crudName = crudName;
		me.record = record;
		me['load'+media]();
	},
	loadPhoto: function(){
		var me = this, urlPrefix = me.urlPhotoPrefix + '/' + me.crudName.slice(4,me.crudName.indexOf("grid")),
		recId = me.record.data.id, url = urlPrefix + '/' + recId;
		me.getEl().mask(_('Load Image'), 'x-mask-loading');
		Ext.Ajax.request({
			url: urlPrefix + '/' + 'photoInfo' + '/' + recId,
			success: function(response){
				var resp = Ext.decode(response.responseText);
				me.setWidth(resp.imageWidth);
				me.setHeight(resp.imageHeight);
				var photoItem = {
					xtype: 'image',
					src: url,
					autoEl: 'div'
				};
				me.add(photoItem);
				me.doLayout();
				me.getEl().unmask();
			},
			failure: function(response){
				var resp = Ext.decode(response.responseText);
				Ext.Msg.show({
					 title: _('Error'),
					 msg: resp.message,
					 buttons: Ext.Msg.OK,
					 icon: Ext.Msg.ERROR
				});
				me.getEl().unmask();
			}
		});
		
	},
	loadVideo: function(){
		var me = this, urlPrefix = me.urlVideoPrefix + '/' + me.crudName.slice(4,me.crudName.indexOf("grid")),
		recId = me.record.data.id, url = urlPrefix + '/' + recId;
		me.getEl().mask(_('Load Video'), 'x-mask-loading');
		me.add({
			xtype: 'panel',
			border: false,
			layout: 'fit',
			html: '<video id="video" controls autoplay>' + 
					'<source src="' + url + '"  type="video/mp4" />' +
					'Your browser does not support the <code>video</code> element.'+
				  '</video>',
			listeners: {
				afterrender: function(){
					var video = document.getElementById("video");
					var videoHeight;
					var videoWidth;
					var barHeight = 35;
					video.addEventListener("loadedmetadata", function () {
						videoHeight = video.videoHeight + barHeight;
						videoWidth = video.videoWidth;
						var win = Ext.ComponentQuery.query('window[itemId=mediaArea]')[0];
						win.setWidth(videoWidth);
						win.setHeight(videoHeight);
					}, false);
				}
			}	  
		});
		me.doLayout();		
		me.getEl().unmask();
	},
    initComponent: function() {
        var me = this;
		Ext.apply(me,{
			itemId: me.itemId,
			id: me.itemId,
			listeners: {
				'afterrender': function(win, opts){
					me.load(me.media, me.crudName, me.record);
				}
			}
		});
        me.callParent(arguments);
    }

});
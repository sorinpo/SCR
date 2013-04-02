Ext.application({
    name: 'EDU',
    
    appFolder : 'resources/js/edu',
    
    controllers : [
        'PupilController'
    ],
    
    launch: function() {
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            items: [{
            	xtype: 'pupil_list',
            	title: ('Creștem împreună - ' + APP_SEC.username),
            	tools : [{
            		type: 'close',
            		tooltip: 'Ieșire',
            		handler: function(){
            			window.location.href = 'resources/j_spring_security_logout';
                    }
            	}]
            }]
            
        });
    }    
    
});
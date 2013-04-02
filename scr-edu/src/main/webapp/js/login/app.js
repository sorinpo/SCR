Ext.application({
    name: 'LOGIN',
    
    appFolder : 'resources/js/login',
    
    launch: function() {
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            items: [{
            	xtype: 'form',
            	autoShow: true,
            	floating: true,
            	title: 'Autentificare',
            	url: window.login_url,
            	standardSubmit: true,
            	defaults: {
            		padding : 3
            	},
            	items :[{
            		xtype: 'component',
            		html: '<span style="color:red;font-weight:bold">Autentificare eșuată</span>',
            		hidden: (window.location.href.indexOf('login_error=t') < 0) 
            	},{
            		xtype: 'textfield',
            		fieldLabel: 'Numele utilizatorului',
            		name: 'j_username'
            	},{
            		xtype: 'textfield',
            		fieldLabel: 'Parola',	
            		name: 'j_password'
            	}],
            	bbar : [{
            		text: 'Trimite',
            		handler: function(button) {
            			button.up('form').submit();
            		}
            	}]
            }]
            
        });
    }    
    
});
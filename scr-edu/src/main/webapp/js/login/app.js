Ext.application({
    name: 'LOGIN',
    
    appFolder : 'resources/js/login',
    
    init: function() {
        this.control({
        	'[action=submit]' : {
        		click : this.submitForm
        	},
        	'form textfield': {
        		specialkey: function(field, e){
                    if (e.getKey() == e.ENTER) {
                    	this.submitForm(field);
                    }
                }
            },
            'form': {
            	afterrender : function(comp){
            		comp.down('[name=j_username]').focus(false, 100);
            	}
            }
        });
    },
    
    launch: function() {
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            items: [{
            	xtype: 'form',
            	id: 'loginForm',
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
            		allowBlank: false,
            		name: 'j_username',
            		tabIndex : 1
            	},{
            		xtype: 'textfield',
            		fieldLabel: 'Parola',
            		allowBlank: false,
            		inputType: 'password',
            		name: 'j_password',
            		tabIndex : 2
            	}],
            	bbar : [{
            		text: 'Trimite',
            		action: 'submit',
            		tabIndex : 3
            	}]
            }]
            
        });
        
        if(window.DEV){
        	var form = Ext.ComponentManager.get('loginForm'),
        		bbar = form.down('toolbar');
        	Ext.Object.each(window.DEV, function(user, pass){
        		var button = Ext.button.Button({
        			text: user, 
        			handler: function(button){ 
	        			form.down('[name=j_username]').setValue(user);
	        			form.down('[name=j_password]').setValue(pass);
	        			form.submit();
    				} 
    			});
        		
        		bbar.add( button );
        	});
        }
    },
    
    submitForm: function(childComp){
    	var form = childComp.up('form');
		if(form.isValid())
			form.submit();
    }
    
});
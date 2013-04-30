Ext.define('EDU.controller.ConfigController', {
    extend: 'Ext.app.Controller',

	views : [ 'admin.Config' ],

    init: function() {
    	var me = this;
    	
        me.control({
        	'config': {
        		afterrender: function(form){
                	
        			form.loadRecord(ParamManager.get('config'));
        			
                }
        	},
        	'config [action=save]': {
        		click : me.sendImport
        	}        	
        });
       
    },
    
    sendImport: function(button) {
    	var form = button.up('form'),
    		record = form.getRecord(),
    		values = Helpers.inflate(form.getValues());

    	if(form.isValid()){
    		
    		record.set(values);
    		
    		record.save({
    			
	    		callback: function(config, op) {
	    			if(op.success){
	    				ParamManager.set('activeYear', config.get('activeYear') );
	        			ParamManager.set('activeMonths', config.get('activeMonths') );
	        			ParamManager.set('config', config );
		                Ext.Msg.alert('Succes', 'Datele s-au salvat cu succes');
	    			} else {
	    				Ext.Msg.alert('Eroare', 'Serverul raporteaza o eroare: ' + op.getError());
	    			}
	            }
            });
    		
        }
    }
    
});


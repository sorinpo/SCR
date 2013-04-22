Ext.define('EDU.controller.InfoController', {
    extend: 'Ext.app.Controller',

    models : [ 'Info' , 'CentruInfo'],

	views : [ 'info_centru.Edit', 'info.Edit' ],

    init: function() {
    	var me = this;
    	
        me.control({
        	
        	'info_edit': {
        		afterrender : function(form){
        			me.loadInfo(form);
        			
        			me.mon(ParamManager, 'change', function(paramName, newValue){
    		        	if(paramName == "activeYear" || paramName == "runas"){
    		        		me.loadInfo(form);
    		        	}
    		        });
        		}
        	},
        	
        	'info_edit [action=save]': {
        		click : me.saveInfo
        	},
        	
        	'info_centru_edit' : {
        		afterrender : function(form){
        			me.loadCentruInfo(form);
        			
        			me.mon(ParamManager, 'change', function(paramName, newValue){
        				if(paramName == "runas"){
    		        		me.loadCentruInfo(form);
    		        	}
    		        });
		        	
        		}
        	},
        	
        	'info_centru_edit [action=save]' :{
        		click : me.saveCentruInfo
        	}
        	
        });
       
    },

    loadInfo: function(form) {
    	
    	EDU.model.Info.load(null, {
    	    scope: this,
    	    failure: Helpers.operationFailed,
    	    success: function(record){
    	    	form.loadRecord(record);
    	    }
    	});
    	
    },
    
    saveInfo: function(button) {
    	var form   = button.up('form'),
	        record = form.getRecord(),
	        values = Helpers.inflate(form.getValues());

    	record.set(values);
    	record.save();
    },
    
    loadCentruInfo: function(form) {
    	
    	EDU.model.CentruInfo.load(null, {
    	    scope: this,
    	    failure: Helpers.operationFailed,
    	    success: function(record){
    	    	form.loadRecord(record);
    	    }
    	});
    	
    },
    
    saveCentruInfo: function(button) {
    	var form   = button.up('form'),
	        record = form.getRecord(),
	        values = Helpers.inflate(form.getValues());

    	record.set(values);
    	record.save();
    }
    
});


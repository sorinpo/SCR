Ext.define('EDU.controller.PupilController', {
    extend: 'Ext.app.Controller',

    models : [ 'Pupil' ],
    
	stores : [ 'Pupils', 'Users', 'Countries' ],

	views : [ 'pupil.List', 'pupil.Edit', 'form.Participation' ],

    init: function() {
        this.control({
        	
        	'pupil_list': {
                itemdblclick: this.editPupil
            },
        
        	'pupil_list [action=add]': {
        		click : this.addPupil
        	},
        	
        	'pupil_list [action=delete]': {
        		click : this.deletePupil
        	},
        	
        	'pupil_edit [action=save]': {
        		click : this.savePupil
        	}
        });
    },

    addPupil: function(button) {
        var view = Ext.widget('pupil_edit'),
        	record = Ext.create('EDU.model.Pupil');

        if(!APP_SEC.isAdmin){
        	record.set('county', APP_SEC.username.toUpperCase());
        }
        
        view.down('form').loadRecord(record);
    },
    
    editPupil: function(grid, record) {
        var view = Ext.widget('pupil_edit');

        view.down('form').loadRecord(record);
    },
    
    savePupil: function(button) {
    	var win    = button.up('window'),
	        form   = win.down('form'),
	        record = form.getRecord(),
	        values = this.inflate(form.getValues()),
	        store = this.getPupilsStore();

    	record.set(values);
    	win.close();
    	
    	if(record.phantom){
    		store.add(record);
    	}
	    // synchronize the store after editing the record
	    store.sync();
    },
    
    deletePupil: function(button) {
    	var grid = button.up('grid'),
    	    store = grid.getStore(),
    		selection = grid.getSelectionModel().getSelection();

    	if(selection && selection.length){
        	//TODO ask for confirmation
			store.remove(selection);
			store.sync();
    	}
    },
    
    //private 
    inflate: function(data){
    	var ret;
    	
    	if(Ext.isArray(data)){
    		ret = [];
    		
    		Ext.Array.Each(data, function(item) {
    			
    			ret.push( this.inflate(item) );
    		});
    		
    	} else if(Ext.isObject(data)){
    		
    		ret = {};
    		
    		Ext.Object.each(data, function(key, value){
    			
    			var obj = ret,
    				keyArray = key.split('.'),
    				lastKey = keyArray.pop();
    			
    			Ext.Array.each(keyArray, function(prop){
    				
    				if(typeof obj[prop] === "undefined") {
    					obj[prop] = {};    					
    				}
    				
    				obj = obj[prop];
    				
    			});
    			
    			obj[lastKey] = value;
    			
    		});
    		
    	} else {
    		
    		ret = data;
    		
    	}
    	
    	return ret;
    	
    }
});


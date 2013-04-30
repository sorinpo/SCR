Ext.define('EDU.controller.PupilController', {
    extend: 'Ext.app.Controller',

    models : [ 'Pupil', 'Participation' ],
    
	stores : [ 'Pupils', 'Participations', 'Countries' ],

	views : [ 'pupil.Main', 'pupil.List', 'pupil.Edit', 'pupil.participation.Edit' ],

    init: function() {
        var me= this;
    	me.control({
        	
        	'pupil_list': {
                itemdblclick: me.editPupil,
                
                select : me.pupilSelected,
                
                afterrender: function(grid){
                	grid.getStore().load();
                	
                	me.mon(ParamManager, 'change', function(paramName, newValue){
    		        	if(paramName == "activeYear" || paramName == "runas"){
    		        		var pForm = grid.up('pupil_main').down('pupil_participation_edit');
    		        		pForm.setDisabled(true);
    		        		grid.getStore().load();
    		        	}
    		        });
                }
            },
        
        	'pupil_list [action=add]': {
        		click : me.addPupil
        	},
        	
        	'pupil_list [action=delete]': {
        		click : me.deletePupil
        	},
        	
        	'pupil_edit [action=save]': {
        		click : me.savePupil
        	},
        	
        	'pupil_participation_edit' : {
        		afterrender: function(form){
               	
                	me.setActiveMonths(form, ParamManager.get('activeMonths'));
                	
                	me.mon(ParamManager, 'change', function(paramName, newValue){
                		if(paramName == "activeMonths"){
                			me.setActiveMonths(form, newValue);
                		}
    		        });
                }
        	},
        	
        	'pupil_participation_edit [action=save]': {
        		click : me.saveParticipation
        	}
        	
        });
    },

    addPupil: function(button) {
        var view = Ext.widget('pupil_edit'),
        	record = Ext.create('EDU.model.Pupil');

        record.set('owner', APP_SEC.runas);
        
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
	        values = Helpers.inflate(form.getValues()),
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
    
    pupilSelected: function(rowModel, record) {
    	
    	var pForm = rowModel.view.up('pupil_main').down('pupil_participation_edit');
    	
    	pForm.setDisabled(false);
    	
    	EDU.model.Participation.load(record.getId(), {
    		scope: this,
    	    failure: Helpers.operationFailed,
    	    success: function(record){
    	    	pForm.loadRecord(record);
    	    }
    	});
    	
    },
    
    saveParticipation: function(button) {
    	var form    = button.up('form'),
	        record = form.getRecord(),
	        values = Helpers.inflate(form.getValues());

    	record.set(values);
    	
    	record.save();
    	
    },
    
    setActiveMonths: function(form, activeMonths){
    	Ext.each(form.query('activity'), function(comp){
    		comp.setActiveMonths(activeMonths);
    	});
    }
});


Ext.define('EDU.controller.PupilController', {
    extend: 'Ext.app.Controller',

    models : [ 'Pupil', 'Participation' ],
    
	stores : [ 'Pupils', 'Countries' ],

	views : [ 'pupil.Main', 'pupil.List', 'pupil.Edit', 'pupil.participation.Edit' ],
	
	suspendCheckboxChangeEvent : false,
	
	dataToSave : {},
	
	loadedPupilId : undefined,
	
    init: function() {
        var me = this;
        
        me.autosaveTask = new Ext.util.DelayedTask( function(){
        	var data = me.dataToSave;
        	
        	if(!Ext.Object.isEmpty(data) && me.loadedPupilId){
        		me.dataToSave = {};
        		
        		data.pupilId = me.loadedPupilId;
        		data.year = ParamManager.get('activeYear');
        		
        		Ext.Ajax.request({
        			url : SERVER_ROOT + 'participations.json',
        			defaultHeaders : {'Accept' : 'application/json'},
        			jsonData: data	
        		});
        		
        	}
        	
        });
        
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
    		        		me.loadedPupilId = undefined;
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
        	
        	'pupil_participation_edit activity checkbox' : {
        		change : function(checkbox, value) {
        			
        			if(!me.suspendCheckboxChangeEvent){
        				
        				me.addToSaveData(checkbox.ownerCt.name, checkbox.itemId, checkbox.value);
        				me.autosaveTask.delay(1000);
        				
        			}
        			
        		}
        	}
        	
        });
    },

    addPupil: function(button) {
        var view = Ext.widget('pupil_edit'),
        	record = Ext.create('EDU.model.Pupil');

        record.set('owner', ParamManager.get('runas'));
        
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
    		
    		grid.up('pupil_main')
    			.down('pupil_participation_edit').setDisabled(true);
    		
			store.remove(selection);
			store.sync();
    	}
    },
    
    pupilSelected: function(rowModel, record) {
    	
    	var me = this,
    		pForm = rowModel.view.up('pupil_main').down('pupil_participation_edit');
    	
    	pForm.setDisabled(true);
    	
    	me.loadOrDeferPupil(record.getId(), pForm);
    },
    
    loadOrDeferPupil : function(pupilId, pForm){
    	var me = this;
    	
    	if(!Ext.Object.isEmpty(me.dataToSave)){//there is a save going on, wait to finish 
    		
    		Ext.Function.defer(me.loadOrDeferPupil, 100, me, [pupilId, pForm]);

    	} else {
    	
	    	me.suspendCheckboxChangeEvent = true;
	    	EDU.model.Participation.load(pupilId, {
	    	    callback : function(rec, op){
	    	    	if(op.success){
	    	    		pForm.setDisabled(false);
	    	    		pForm.loadRecord(rec);
	    	    	} else {
	    	    		Ext.callback(Helpers.operationFailed, me, arguments);
	    	    	}
	    	    	me.suspendCheckboxChangeEvent = false;
	    	    	me.loadedPupilId = pupilId;
	    	    }
	    	});
    	
    	}
    	
    },
    
    setActiveMonths: function(form, activeMonths){
    	Ext.each(form.query('activity'), function(comp){
    		comp.setActiveMonths(activeMonths);
    	});
    },
    
    addToSaveData : function(pprop, prop, newVal){
    	var me=this, 
    		pobj, oldVal;
    	
    	if(!(pobj = me.dataToSave[pprop])){
    		pobj = me.dataToSave[pprop] = {};
    	}
    	
    	if((oldVal = pobj[prop]) === undefined ){//set the value
    		pobj[prop] = newVal;
    	} else if( oldVal !== newVal ) {//the value was changed back to what it was in the first place, no need to save 
    		delete pobj[prop];
    		if(Ext.Object.isEmpty(pobj)){
    			delete me.dataToSave[pprop];
    		}
    	}
    }
    
});


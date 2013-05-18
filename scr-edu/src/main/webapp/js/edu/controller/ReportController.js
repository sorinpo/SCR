Ext.define('EDU.controller.ReportController', {
    extend: 'Ext.app.Controller',

	views : [ 'admin.Report' ],

    init: function() {
    	var me = this;
    	
        me.control({
        	'#report [action=send]': {
        		click : me.getReport
        	},
	        '#export [action=send]': {
	    		click : me.getExport
	    	},
	    	'report [action=checkAll]': {
        		click : me.checkAll
        	},
	    	'report [action=uncheckAll]': {
        		click : me.uncheckAll
        	}
        });
       
    },
    
    getReport: function(button){
    	this.submitAndGetFile(button.up('form'), SERVER_ROOT + 'report');
    },
    
    getExport: function(button){
    	this.submitAndGetFile(button.up('form'), SERVER_ROOT + 'export');
    },
    
    //private
    submitAndGetFile: function(form, url) {
    	if(form.isValid()){
            form.submit({
                url: url,
                params: {'year' : ParamManager.get('activeYear') },
                standardSubmit: true,
                target: 'download_frame'
            });
        }
    },
    
    checkAll: function(button){
    	this.setAll(button.up('form'), true);
    },
    
    uncheckAll: function(button){
    	this.setAll(button.up('form'), false);
    },
    
    setAll: function(form, val){
    	
    	Ext.Array.each(form.query('usergroup checkbox'), function(checkbox){
    		checkbox.setValue(val);
    	});

    }
    
});


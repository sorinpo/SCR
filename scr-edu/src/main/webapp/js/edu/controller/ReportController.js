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
	    	}
        });
       
    },
    
    getReport: function(button){
    	this.submitAndGetFile(button.up('form'), SERVER_ROOT + 'report')
    },
    
    getExport: function(button){
    	this.submitAndGetFile(button.up('form'), SERVER_ROOT + 'export')
    },
    
    submitAndGetFile: function(form, url) {
    	if(form.isValid()){
            form.submit({
                url: url,
                params: {'year' : ParamManager.get('activeYear') },
                standardSubmit: true,
                target: 'download_frame'
            });
        }
    }
    
});


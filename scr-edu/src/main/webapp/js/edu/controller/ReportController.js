Ext.define('EDU.controller.ReportController', {
    extend: 'Ext.app.Controller',

	views : [ 'admin.Report' ],

    init: function() {
    	var me = this;
    	
        me.control({
        	'report [action=send]': {
        		click : me.sendImport
        	}        	
        });
       
    },
    
    sendImport: function(button) {
    	var form   = button.up('form');

    	if(form.isValid()){
            form.submit({
                url: SERVER_ROOT + 'report',
                //waitMsg: 'Se trimite cererea',
                params: {'year' : ParamManager.get('activeYear') },
                standardSubmit: true,
                target: 'download_frame'/*,
                success: function(fp, o) {
                	//Ext.StoreManager.get('Pupils').load();
                    //Ext.Msg.alert('Succes', 'Fișierul a fost încărcat cu succes.');
                },
                failure: function(fp, o) {
                    Ext.Msg.alert('Eroare', 'Serverul raporteaza o eroare: ' + o.result.message);
                }*/
            });
        }
    }
    
});


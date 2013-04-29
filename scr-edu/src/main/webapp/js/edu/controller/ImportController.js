Ext.define('EDU.controller.ImportController', {
    extend: 'Ext.app.Controller',

	views : [ 'admin.Import' ],

    init: function() {
    	var me = this;
    	
        me.control({
        	'import [action=send]': {
        		click : me.sendImport
        	}        	
        });
       
    },

    
    sendImport: function(button) {
    	var form   = button.up('form');

    	if(form.isValid()){
            form.submit({
                url: SERVER_ROOT + 'import',
                waitMsg: 'Se Încarcă fișierul',
                params: {'owner' : ParamManager.get('runas'), 'year' : ParamManager.get('activeYear') },
                success: function(fp, o) {
                	Ext.StoreManager.get('Pupils').load();
                    Ext.Msg.alert('Succes', 'Fișierul a fost încărcat cu succes: ' + o.result.message);
                },
                failure: function(fp, o) {
                    Ext.Msg.alert('Eroare', 'Serverul raporteaza o eroare: ' + o.result.message);
                }
            });
        }
    }
    
});


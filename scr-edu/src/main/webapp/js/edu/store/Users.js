Ext.define('EDU.store.Users', {
    extend: 'Ext.data.Store',
    
    fields: ['id','username','role','description'],
    
    proxy: {
        type: 'ajax',
        url: SERVER_ROOT + 'users.json',
        reader: {
            type: 'json'
        }
    },

	autoLoad: true
    
});
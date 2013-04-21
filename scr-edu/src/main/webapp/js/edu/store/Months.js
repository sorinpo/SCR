Ext.define('EDU.store.Months', {
    extend: 'Ext.data.Store',
    
    fields: ['id', 'ord', 'abbr', 'name'],
    
    proxy: {
        type: 'ajax',
        url: SERVER_ROOT + 'months.json',
        reader: {
            type: 'json'
        }
    },

	autoLoad: true
    
});
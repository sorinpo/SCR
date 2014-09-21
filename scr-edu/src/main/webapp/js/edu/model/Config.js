Ext.define('EDU.model.Config', {
    extend: 'Ext.data.Model',
    fields: [
       //{name : 'id', type: 'auto', defaultValue: null }, 
       //{name : 'version', type: 'auto', defaultValue: null },
       
       {name : 'activeYear', type: 'int'},
       {name: 'activeMonths', type: 'auto', defaultValue: null }
    ],
    
    proxy: {
        type: 'ajax',
        url: SERVER_ROOT + 'config.json',
        reader: {
            type: 'json'
        }
    }
});
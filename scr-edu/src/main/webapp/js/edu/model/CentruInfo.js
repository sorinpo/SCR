Ext.define('EDU.model.CentruInfo', {
    extend: 'Ext.data.Model',
    fields: [
       {name : 'id', type: 'auto', defaultValue: null }, 
       
       {name : 'userId', type: 'int'},
       
       {name : 'locality', type: 'string'},
       {name : 'address', type: 'string'},
       
       {name : 'team', type: 'string'}
       
    ],
    
    proxy: {
        type: 'rest',
        appendId: false,
        url: SERVER_ROOT + 'centru_infos.json',
        getParams: Helpers.getParams({'username':'runas'}),
        reader: {
            type: 'json'
        }
    }
});
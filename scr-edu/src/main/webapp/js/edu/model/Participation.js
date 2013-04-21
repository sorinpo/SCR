Ext.define('EDU.model.Participation', {
    
	extend: 'Ext.data.Model',
    fields: [
       {name : 'id', type: 'auto', defaultValue: null }, 
       /*{name : 'version', type: 'auto', defaultValue: null },*/
       
       {name : 'year', type: 'int'},
       
       {name : 'school', type: 'auto', defaultValue: null },
       {name : 'extra', type: 'auto', defaultValue: null },
       {name : 'group', type: 'auto', defaultValue: null },
       {name : 'individual', type: 'auto', defaultValue: null },
       {name : 'online', type: 'auto', defaultValue: null },
       {name : 'discussion', type: 'auto', defaultValue: null }
    ],
    
    proxy: {
        type: 'rest',
        appendId: false,
        url: SERVER_ROOT + 'participations.json',
        getParams: Helpers.getParams({'year' : 'activeYear'}),
        reader: {
            type: 'json'
        }
    }
});
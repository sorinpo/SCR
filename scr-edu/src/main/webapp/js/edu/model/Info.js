Ext.define('EDU.model.Info', {
    extend: 'Ext.data.Model',
    fields: [
       {name : 'id', type: 'auto', defaultValue: null }, 
       {name : 'version', type: 'auto', defaultValue: null },
       
       {name : 'year', type: 'int'},
       {name : 'userId', type: 'int'},
       
       {name : 'beneficiariIndirecti', type: 'auto', defaultValue: null },
       {name : 'voluntariImplicati', type: 'auto', defaultValue: null },
       {name : 'intalniriGrupuriLucru', type: 'auto', defaultValue: null },
       {name : 'participantiGrupuriLucru', type: 'auto', defaultValue: null },
       {name : 'conferinteOrganizate', type: 'auto', defaultValue: null },
       {name : 'participantiConferinte', type: 'auto', defaultValue: null },
       {name : 'aparitiiPresa', type: 'auto', defaultValue: null },
       
       {name : 'links', type: 'string', defaultValue: null }
    ],
    
    proxy: {
        type: 'rest',
        appendId: false,
        url: SERVER_ROOT + 'infos.json',
        getParams: Helpers.getParams({'username':'runas', 'year':'activeYear'}),
        reader: {
            type: 'json'
        }
    }
});
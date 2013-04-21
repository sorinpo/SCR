Ext.define('EDU.model.Pupil', {
    extend: 'Ext.data.Model',
    fields: [
       {name : 'id', type: 'auto', defaultValue: null }, 
       {name : 'version', type: 'auto', defaultValue: null },
       
       {name : 'name', type: 'string'},
       {name : 'owner', type: 'string'},
       {name : 'comment', type: 'string'},
       {name : 'leftToCountry', type: 'string'},
       
       {name : 'parentState', type: 'auto', defaultValue: null },

       {name : 'locked', type: 'boolean'}/*,
       
       {name : 'school', type: 'auto', defaultValue: null },
       {name : 'extra', type: 'auto', defaultValue: null },
       {name : 'group', type: 'auto', defaultValue: null },
       {name : 'individual', type: 'auto', defaultValue: null },
       {name : 'online', type: 'auto', defaultValue: null },
       {name : 'discussion', type: 'auto', defaultValue: null }*/
    ]
});
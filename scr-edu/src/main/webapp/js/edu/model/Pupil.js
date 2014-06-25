Ext.define('EDU.model.Pupil', {
    extend: 'Ext.data.Model',
    fields: [
       {name : 'id', type: 'auto', defaultValue: null }, 
       {name : 'version', type: 'auto', defaultValue: null },
       
       {name : 'name', type: 'string'},
       
       {name : 'birthDate', type: 'date', dateFormat: 'time', convert: function(v) {
    	   if(!v) {
    		   return "";
    	   } else if(isNaN(v)){
    		   return Ext.Date.parse(v, "j/n/Y");  
    	   } else {
    		   return new Date(v);
    	   }
       }},
       
       {name : 'owner', type: 'string'},
       {name : 'comment', type: 'string'},
       {name : 'leftToCountry', type: 'string'},
       
       {name : 'parentState', type: 'auto', defaultValue: null },

       {name : 'recruitmentDate', type: 'date', dateFormat: 'time', convert: function(v) {
    	   if(!v) {
    		   return "";
    	   } else if(isNaN(v)){
    		   return Ext.Date.parse(v, "j/n/Y");  
    	   } else {
    		   return new Date(v);
    	   }
       }},
       {name : 'recruitmentMethod', type: 'auto', defaultValue: null },
              
       {name : 'locked', type: 'boolean'}
       
    ]
});
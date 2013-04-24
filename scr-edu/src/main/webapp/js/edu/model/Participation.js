Ext.define('EDU.model.Participation', {
    
	extend: 'Ext.data.Model',
    fields: [
       {name : 'id', type: 'auto', defaultValue: null }, 
       {name : 'version', type: 'auto', defaultValue: null },
       
       {name : 'pupilId', type: 'int'},
       {name : 'year', type: 'int'},
       
       {name : 'school', type: 'auto', defaultValue: null },
       {name : 'freeTime', type: 'auto', defaultValue: null },
       {name : 'extraSchool', type: 'auto', defaultValue: null },
       {name : 'groupCounseling', type: 'auto', defaultValue: null },
       {name : 'individualCounseling', type: 'auto', defaultValue: null },
       {name : 'parentalCommunication', type: 'auto', defaultValue: null },
       {name : 'localMeetings', type: 'auto', defaultValue: null }
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
Ext.define('EDU.view.pupil.participation.Edit', {
    extend: 'Ext.form.Panel',
    
    requires : ['EDU.component.form.ActivityDataGroup', 
                'EDU.component.form.ParentalCommunicationDetailedGroup'],
    
    alias: 'widget.pupil_participation_edit',

    title: 'Participare activități',
    
    defaults : {
    	xtype: 'activity',
    	columns: 6,
    	labelAlign: 'top'
    },
    
    initComponent: function() {
        this.items = [{
            name : 'school',
            fieldLabel: 'Pregătire şcolară'
        },{
            name : 'freeTime',
            fieldLabel: 'Activităţi şcolare de timp liber'
        },{
            name : 'extraSchool',
            fieldLabel: 'Activităţi extraşcolare'
        },{
            name : 'groupCounseling',
            fieldLabel: 'Consiliere psihologica de grup'
        },{
            name : 'individualCounseling',
            fieldLabel: 'Consiliere psihologica  individuală'
        },{
            name : 'parentalCommunication',
            fieldLabel: 'Comunicare cu părinţii plecati'
        },{
            name : 'localMeetings',
            fieldLabel: 'Întâlniri cu părinţii sau cei care au grija de copil în tara'
        },{
        	xtype : 'parentalCommunicationGroup',
            name : 'parentalCommunicationDetailed',
            fieldLabel: 'Cu câti parinți a comunicat copilul'
        }];
        
        //last call
        this.callParent(arguments);
    }
});
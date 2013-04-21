Ext.define('EDU.view.pupil.participation.Edit', {
    extend: 'Ext.form.Panel',
    
    requires : ['EDU.component.form.ParticipationGroup'],
    
    alias: 'widget.pupil_participation_edit',

    title: 'Participare activități',
    
    initComponent: function() {
        this.items = [{
            xtype: 'participation',
            name : 'school',
            columns: 3,
            fieldLabel: 'Pregătire şcolară',
            labelAlign: "top"
        }];

        this.tbar = [
            {
                text: 'Salvează',
                action: 'save'
            }
        ];

        //last call
        this.callParent(arguments);
    }
});
Ext.define('EDU.view.pupil.Edit', {
    extend: 'Ext.window.Window',
    alias: 'widget.pupil_edit',

    title: 'Adaugă înregistrare',
    layout: 'fit',
    autoShow: true,
    
    initComponent: function() {
        this.items = [{
            xtype: 'form',
            defaults : { padding: 3 },
            items: [{
                xtype: 'textfield',
                name : 'name',
                fieldLabel: 'Nume',
                allowBlank: false
            },{
                xtype: 'datefield',
                name : 'birthDate',
                fieldLabel: 'Data nașterii',
                allowBlank: false,
                format: 'j/n/Y',
                altFormats: 'j/n/Y|j/m/Y|d/n/Y|d/m/Y|j.n.Y|j.m.Y|d.n.Y|d.m.Y|j-n-Y|j-m-Y|d-n-Y|d-m-Y',
                maxValue: new Date()
            },{
                xtype: 'radiogroup',
                fieldLabel: 'Situație Părinți',
                vertical: true,
                columns: 1,
                items: [
                    { boxLabel: 'Doar mama plecată', name: 'parentState', inputValue: 'MOTHER' },
                    { boxLabel: 'Doar tatăl plecat', name: 'parentState', inputValue: 'FATHER' },
                    { boxLabel: 'Ambii parinți plecați', name: 'parentState', inputValue: 'BOTH' }
                ],
                allowBlank: false
            },{
        		xtype: 'combobox',
        		store: 'Countries',
        		valueField: 'name',
        		displayField: 'name',
                name : 'leftToCountry',
                fieldLabel: 'Ţara unde este plecat părintele'
        	},{
                xtype: 'datefield',
                name : 'recruitmentDate',
                fieldLabel: 'Data includerii în program',
                allowBlank: false,
                format: 'j/n/Y',
                altFormats: 'j/n/Y|j/m/Y|d/n/Y|d/m/Y|j.n.Y|j.m.Y|d.n.Y|d.m.Y|j-n-Y|j-m-Y|d-n-Y|d-m-Y',
                minValue: '1/7/2013',
                maxValue: new Date()
            },{
                xtype: 'radiogroup',
                fieldLabel: 'Modalitate de includere în program',
                vertical: true,
                columns: 1,
                items: [
                    { boxLabel: 'Identificarea de către echipa de proiect', name: 'recruitmentMethod', inputValue: 'PROJECT_TEAM' },
                    { boxLabel: 'Referirea de către instituțiile partenere (scoala etc)', name: 'recruitmentMethod', inputValue: 'PARTNERS' },
                    { boxLabel: 'Solicitarea includerii de catre copil/familie (a aflat din comunitate despre program)', name: 'recruitmentMethod', inputValue: 'COMMUNITY' }
                ],
                allowBlank: false
            },{
                xtype: 'textarea',
                name : 'comment',
                fieldLabel: 'Observații'
            }]
        }];
        
        this.buttons = [
            {
                text: 'Salvează',
                action: 'save'
            },
            {
                text: 'Anulează',
                scope: this,
                handler: this.close
            }
        ];

        //last call
        this.callParent(arguments);
    }
});
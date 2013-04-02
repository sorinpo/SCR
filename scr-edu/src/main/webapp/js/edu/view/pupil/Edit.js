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
                xtype: 'textarea',
                name : 'comment',
                fieldLabel: 'Observații'
            },{
                xtype: 'participation',
                name : 'school',
                columns: 3,
                fieldLabel: 'Pregătire şcolară'
            }]
        }];

        if(APP_SEC.isAdmin){
        	this.items[0].items.push({
        		xtype: 'combobox',
        		store: 'Users',
        		valueField: 'id',
        		displayField: 'name',
        		editable: false,
        		allowBlank: false,
                name : 'county',
                fieldLabel: 'Județul'
        	});
        }
        
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
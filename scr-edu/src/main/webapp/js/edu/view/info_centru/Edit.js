Ext.define('EDU.view.info_centru.Edit', {
    extend: 'Ext.form.Panel',
    alias: 'widget.info_centru_edit',
    defaults: { padding: 3 },
    fieldDefaults: {
    	labelWidth: 200,
    	width: 500
    },
    items: [{
    	xtype: 'displayfield',
    	fieldLabel: 'Program',
    	value: 'Creștem Împreună'
    },{
    	xtype: 'textfield',
        name : 'locality',
        fieldLabel: 'Localitatea',
        allowBlank: false
    },{
        xtype: 'textfield',
        name : 'address',
        fieldLabel: 'Adresa',
        allowBlank: false
    },{
        xtype: 'textarea',
        name : 'team',
        fieldLabel: 'Echipa',
        allowBlank: false
    }],
    
    dockedItems: {
        xtype: 'toolbar',
        dock: 'top',
        items: [{
            text: 'Salvează',
            action: 'save'
        }]
    }
});
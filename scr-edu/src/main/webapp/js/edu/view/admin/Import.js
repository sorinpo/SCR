Ext.define('EDU.view.admin.Import', {
    extend: 'Ext.form.Panel',
    alias: 'widget.import',
    
    defaults: {
    	anchor: '100%',
    	padding: 3
    },
    
    fieldDefaults: {
        labelAlign: 'left',
        labelWidth: 200
    },
    
    items: [{
    	xtype : 'radiogroup',
    	fieldLabel: 'Comportament in caz de conflicte',
    	items : [{
    		boxLabel: 'Pastreaza datele existente',
    		name: 'mode',
    		inputValue: 'KEEP',
    		checked: true
    	},{
    		boxLabel: 'Abandoneaza',
    		name: 'mode',
    		inputValue: 'ABORT'
    	},{
    		boxLabel: 'Suprascrie datele existente',
    		name: 'mode',
    		inputValue: 'OVERWRITE'
    	}]
    },{
        xtype: 'filefield',
        name: 'file',
        fieldLabel: 'Fișier import',
        msgTarget: 'side',
        allowBlank: false,
        buttonText: 'Selectați un fișier .xlsx...'
    }],
    
    dockedItems: {
        xtype: 'toolbar',
        dock: 'top',
        items: [{
            text: 'Trimite',
            action: 'send'
        }]
    }
});
Ext.define('EDU.view.admin.Config', {
    extend: 'Ext.form.Panel',
    alias: 'widget.config',
    
    defaults: {
    	anchor: '100%',
    	padding: 3
    },
    
    fieldDefaults: {
        labelAlign: 'left',
        labelWidth: 200
    },
    
    items: [{
    	xtype: 'combobox',
    	editable: false,
		allowBlank: false,
		autoSelect: true,
		fieldLabel: 'Anul activ',
		name: 'activeYear',
        store : ['2013', '2014', '2015', '2016']
    },{
    	xtype: 'activity',
        name : 'activeMonths',
        fieldLabel: 'Luni active',
    	columns: 6,
    	labelAlign: 'top'
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
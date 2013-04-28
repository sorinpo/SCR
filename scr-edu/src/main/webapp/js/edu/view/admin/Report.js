Ext.define('EDU.view.admin.Report', {
    extend: 'Ext.form.Panel',
    alias: 'widget.report',
    
    requires : ['EDU.component.form.UserGroup'],
    
    defaults: {
    	anchor: '100%',
    	padding: 3
    },
    
    fieldDefaults: {
        labelAlign: 'left',
        labelWidth: 200
    },
    
    items: [{
    	xtype : 'usergroup',
    	fieldLabel: 'Judete',
    	defaults : {
    		checked: true
    	},
    	columns: 6
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
Ext.define('EDU.view.pupil.Main' ,{
    extend: 'Ext.panel.Panel',
    alias: 'widget.pupil_main',
    
    layout: 'border',
    
    defaults: {
        split: true
    },
    
    initComponent: function() {
        
        this.items = [{
        	xtype: 'pupil_list',
        	region: 'center',
        	flex : 5
        },{
        	xtype: 'pupil_participation_edit',
    	    collapsible: true,
    	    disabled: true,
        	region: 'east',
        	flex: 2,
        	overflowX: 'auto',
        	overflowY: 'auto'
        }];
        
        //last call
        this.callParent(arguments);
    }
});
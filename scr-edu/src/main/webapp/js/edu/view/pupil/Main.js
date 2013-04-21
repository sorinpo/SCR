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
        	flex : 4
        },{
        	xtype: 'pupil_participation_edit',
    	    collapsible: true,
    	    disabled: true,
        	region: 'east',
        	flex: 1
        }];
        
        //last call
        this.callParent(arguments);
    }
});
Ext.define('EDU.component.form.UserGroup', {
    extend:'Ext.form.CheckboxGroup',

    alias: 'widget.usergroup',
    
    name: 'users',
    
    initComponent: function() {
        
    	var me=this,
    		store = Ext.data.StoreManager.lookup('Users');
    	
    	me.items = [];
	    
    	store.each(function(rec){
    		me.items.push(
    			{ boxLabel: rec.get('description'), name: me.name, inputValue: rec.get('username') }	
    		);
    	});
    	
        //last call
    	me.callParent(arguments);
    }
       
});


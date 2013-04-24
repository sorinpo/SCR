Ext.define('EDU.component.form.ActivityDataGroup', {
    extend:'Ext.form.CheckboxGroup',

    alias: 'widget.activity',
     
    initComponent: function() {
        
    	this.items = [
	        { boxLabel: 'ian', itemId:'jan', name: this.name + '.jan', inputValue: true },
	        { boxLabel: 'feb', itemId:'feb', name: this.name + '.feb', inputValue: true },
	        { boxLabel: 'mar', itemId:'mar', name: this.name + '.mar', inputValue: true },
	        { boxLabel: 'apr', itemId:'apr', name: this.name + '.apr', inputValue: true },
	        { boxLabel: 'mai', itemId:'may', name: this.name + '.may', inputValue: true },
	        { boxLabel: 'iun', itemId:'jun', name: this.name + '.jun', inputValue: true },
	        { boxLabel: 'iul', itemId:'jul', name: this.name + '.jul', inputValue: true },
	        { boxLabel: 'aug', itemId:'aug', name: this.name + '.aug', inputValue: true },
	        { boxLabel: 'sep', itemId:'sep', name: this.name + '.sep', inputValue: true },
	        { boxLabel: 'oct', itemId:'oct', name: this.name + '.oct', inputValue: true },
	        { boxLabel: 'noi', itemId:'nov', name: this.name + '.nov', inputValue: true },
	        { boxLabel: 'dec', itemId:'dec', name: this.name + '.dec', inputValue: true }
	    ]
    	
        //last call
        this.callParent(arguments);
    },
    
    setValue : function(value){
    	
    	value = value || {};
    	
    	Ext.Array.each (this.query('checkbox'), function(checkbox){
    		
    		checkbox.setValue(value[checkbox.itemId]);
    		
    	});
    	
    	//this.callParent(arguments);
    }
       
});


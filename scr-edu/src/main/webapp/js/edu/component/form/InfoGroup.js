Ext.define('EDU.component.form.InfoGroup', {
    extend:'Ext.form.CheckboxGroup',

    alias: 'widget.info',
    
    layout: 'column',
    
    defaults : {
    	xtype: 'numberfield',
        minValue: 0, //prevents negative numbers
        // Remove spinner buttons, and arrow key and mouse wheel listeners
        hideTrigger: true,
        keyNavEnabled: false,
        mouseWheelEnabled: false,
        allowDecimals: false,
        labelAlign: 'top',
        width: 50
    },
    
    initComponent: function() {
        
    	this.items = [
	        { fieldLabel: 'ian', itemId:'jan', name: this.name + '.jan' },
	        { fieldLabel: 'feb', itemId:'feb', name: this.name + '.feb' },
	        { fieldLabel: 'mar', itemId:'mar', name: this.name + '.mar' },
	        { fieldLabel: 'apr', itemId:'apr', name: this.name + '.apr' },
	        { fieldLabel: 'mai', itemId:'may', name: this.name + '.may' },
	        { fieldLabel: 'iun', itemId:'jun', name: this.name + '.jun' },
	        { fieldLabel: 'iul', itemId:'jul', name: this.name + '.jul' },
	        { fieldLabel: 'aug', itemId:'aug', name: this.name + '.aug' },
	        { fieldLabel: 'sep', itemId:'sep', name: this.name + '.sep' },
	        { fieldLabel: 'oct', itemId:'oct', name: this.name + '.oct' },
	        { fieldLabel: 'noi', itemId:'nov', name: this.name + '.nov' },
	        { fieldLabel: 'dec', itemId:'dec', name: this.name + '.dec' }
	    ]
    	
        //last call
        this.callParent(arguments);
    },
    
    setValue : function(value){
    	
    	value = value || {};
    	
    	Ext.Array.each (this.query('numberfield'), function(numberfield){
    		
    		numberfield.setValue(value[numberfield.itemId]);
    		
    	});
    	
    },
    
    setActiveMonths : function(activeMonths){
    	
    	activeMonths = activeMonths || {};
    	
    	Ext.Array.each (this.query('numberfield'), function(field){
    		
    		field.setDisabled( !activeMonths[field.itemId] );
    		
    	});
    	
    }
       
});


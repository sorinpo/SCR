Ext.define('EDU.component.form.ParentalCommunicationDetailedGroup', {
    extend:'Ext.form.FieldContainer',

    mixins: {
        field: 'Ext.form.field.Field'
    },
    
    alias: 'widget.parentalCommunicationGroup',
    
    layout: {
        type: 'table',
        columns: 6
    },
    
    defaults : {
    	xtype: 'combobox',
    	editable: false,
		allowBlank: false,
		autoSelect: true,
		labelAlign: 'top',
		width: 60,
        store : [
          ['NONE', '-'],
          ['BOTH', 'Ambii'],
          ['MOTHER', 'Mama'],
          ['FATHER', 'Tatăl']
        ]
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
    
    // private override
    isDirty: function(){
        var boxes = this.query('combobox'),
            b ,
            bLen  = boxes.length;

        for (b = 0; b < bLen; b++) {
            if (boxes[b].isDirty()) {
                return true;
            }
        }
    },
    
    getValue : function() {
    	
    	var value = {};
    	
    	Ext.Array.each (this.query('combobox'), function(combobox) {
    		
    		value[combobox.itemId] = combobox.getValue();
    		
    	});
    	
    	return value;
    },
    
    setValue : function(value){
    	
    	value = value || {};
    	
    	Ext.Array.each (this.query('combobox'), function(combobox){
    		
    		combobox.setValue(value[combobox.itemId]);
    		
    	});

    },
    
    setActiveMonths : function(activeMonths){
    	
    	activeMonths = activeMonths || {};
    	
    	Ext.Array.each (this.query('combobox'), function(field){
    		
    		field.setDisabled( !activeMonths[field.itemId] );
    		
    	});
    	
    }
       
});


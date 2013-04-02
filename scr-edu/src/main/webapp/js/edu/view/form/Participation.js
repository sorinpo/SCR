Ext.define('EDU.view.form.Participation', {
    extend:'Ext.form.CheckboxGroup',
    mixins: {
        field: 'Ext.form.field.Field'
    },
    alias: 'widget.participation',
    requires: ['Ext.layout.container.CheckboxGroup', 'Ext.form.field.Base'],
    
    initComponent: function() {
        
    	this.items = [
	        { boxLabel: 'ian', name: this.name + '.jan', inputValue: true },
	        { boxLabel: 'feb', name: this.name + '.feb', inputValue: true },
	        { boxLabel: 'mar', name: this.name + '.mar', inputValue: true },
	        { boxLabel: 'apr', name: this.name + '.apr', inputValue: true },
	        { boxLabel: 'mai', name: this.name + '.may', inputValue: true },
	        { boxLabel: 'iun', name: this.name + '.jun', inputValue: true },
	        { boxLabel: 'iul', name: this.name + '.jul', inputValue: true },
	        { boxLabel: 'aug', name: this.name + '.aug', inputValue: true },
	        { boxLabel: 'sep', name: this.name + '.sep', inputValue: true },
	        { boxLabel: 'oct', name: this.name + '.oct', inputValue: true },
	        { boxLabel: 'noi', name: this.name + '.nov', inputValue: true },
	        { boxLabel: 'dec', name: this.name + '.dec', inputValue: true }
	    ]
    	
        //last call
        this.callParent(arguments);
    }
       
});


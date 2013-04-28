Ext.define('EDU.component.Counter', {
	
	alternateClassName: ['Counter'],
	
	mixins: {
        observable: 'Ext.util.Observable'
    },
    
    counter: 1,
    
    constructor: function (config) {

        this.mixins.observable.constructor.call(this, config);

        this.addEvents(
            'done'
        );
        
        if(Ext.isFunction(config.handler)){
        	
        	this.on('done', config.handler, config.scope || this );
        	
        }
        
        this.callParent(arguments);
    },
    
    down: function(value) {
    	value = Ext.isNumber(value)?value:1;
        this.counter -= value;
        if(this.counter <= 0){
        	this.fireEvent('done');
        }
    }
    
});
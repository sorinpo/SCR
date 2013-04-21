//singleton that manages parameters and can be observed by UrlParams
Ext.define('EDU.component.ParamManager', {
	
	alternateClassName: ['ParamManager'],
	
	singleton: true,
	
	mixins: {
        observable: 'Ext.util.Observable'
    },
    
    params : {},
    
    constructor: function (config) {

        this.mixins.observable.constructor.call(this, config);

        this.addEvents(
            'beforechange',
            'change'
        );
    },
    
    set: function(name, value) {
        
    	var oldValue=this.params[name];
    	
    	if(oldValue!=value){
    		
    		if( this.fireEvent('beforechange', name, value, oldValue) !== false){
    			
    			this.params[name] = value;
    			
    			this.fireEvent('change', name, value, oldValue)
    		}
    		
    	}
    	
    },
    
    get: function(name) {
    	return this.params[name];
    }
    
});
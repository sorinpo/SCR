//setup initial runas
//XXX AG is a hardcoded default for admins

window.APP_SEC.runas = window.APP_SEC.isAdmin?'AG':window.APP_SEC.username.toUpperCase();

Ext.Loader.setConfig({
    disableCaching: false
});

Ext.namespace('Helpers');
Helpers = {
    
	criticalOperationFailed : function(){
		alert("NASOL");
	},	
		
	operationFailed : function(){
		console.log("NASOL");
	},
		
    inflate: function(data){
    	var ret;
    	
    	if(Ext.isArray(data)){
    		ret = [];
    		
    		Ext.Array.Each(data, function(item) {
    			
    			ret.push( this.inflate(item) );
    		});
    		
    	} else if(Ext.isObject(data)){
    		
    		ret = {};
    		
    		Ext.Object.each(data, function(key, value){
    			
    			var obj = ret,
    				keyArray = key.split('.'),
    				lastKey = keyArray.pop();
    			
    			Ext.Array.each(keyArray, function(prop){
    				
    				if(typeof obj[prop] === "undefined") {
    					obj[prop] = {};    					
    				}
    				
    				obj = obj[prop];
    				
    			});
    			
    			obj[lastKey] = value;
    			
    		});
    		
    	} else {
    		
    		ret = data;
    		
    	}
    	
    	return ret;
    	
    },
    
    getParams: function(paramMap){
    	
    	return function(operation){
    		
    		var params = Ext.data.proxy.Rest.prototype.getParams.call(this, operation);
    		
    		Ext.Object.each(paramMap, function(paramName, paramCorrespondent){
    			if(params[paramName]===undefined){
    				params[paramName] = ParamManager.get(paramCorrespondent);
    			}
    		});
    		
    		return params;
    	}
    	
    }
    
   /* buildUrl: function(){
    	
    	return Helpers.replace( Ext.data.proxy.Rest.prototype.buildUrl.apply(this, arguments), ParamManager.params );
    	
    },
    
    *//**
     * variable substitution in a string
     *//*
    replace: function(str, vars){
    	var varPattern=/\{([A-Za-z0-9_\.]+)\}/g,
    		varResult, 
    		index = 0,
    		resultStr="";
    	
    	while(varResult = varPattern.exec(str)){
    		
    		resultStr +=  (str.substring(index, varResult.index) + vars[varResult[1]] ); 
    		index = varPattern.lastIndex;
    		
    	}
    	
    	return resultStr;
    }*/
}

Ext.application({
    name: 'EDU',
    
    appFolder : 'resources/js/edu',
    
    requires : [ 'EDU.component.ParamManager', 'EDU.component.Counter' ],
    
    models : [
         'Config'     
    ],
    
    stores : [
        'Users',
        'Months'
    ],
    
    controllers : [
        'InfoController',
        'PupilController',
        'ImportController',
        'ReportController'
    ],
    
    launch: function() {
    
    	var me=this,
    		counter = new Counter({ counter: 2, handler: me.loadViewport, scope: me });
    	
    	ParamManager.set('runas', window.APP_SEC.runas);
    	ParamManager.set('activeYear', (new Date()).getFullYear());
    	
    	me.getStore('Users').load({
    		//XXX
    		callback: counter.down,
    		scope: counter
		});
    	
    	EDU.model.Config.load(null, {
    		success: counter.down,
    	    failure: Helpers.criticalOperationFailed,
    	    scope: counter
    	});
    	        
    },
    
    init: function() {
    	var me = this;
    	
        me.control({
        	'#centrul' :{
        		select: function(combo, values){
        			ParamManager.set(
        				'runas', values[0].get('username'));
        		}
        	}
        });
        
    },
    
    loadViewport: function() {
    	
    	Ext.create('Ext.container.Viewport', {
            layout: 'fit',
	        items: [{
	        	xtype: 'tabpanel',
	        	
	            header : {
	            	title: ('Creștem împreună - ' + APP_SEC.username),
	            	titlePosition: 0,
	            	items: [{
	            		xtype: 'combobox',
	            		hidden: !APP_SEC.isAdmin,
	            		store: 'Users',
	            		valueField: 'username',
	            		displayField: 'description',
	            		editable: false,
	            		//allowBlank: false,
	            		autoSelect: true,
	            		itemId: 'centrul',
	                    fieldLabel: 'Centrul'
	            	}],
	            	tools : [{
	            		type: 'close',
	            		tooltip: 'Ieșire',
	            		handler: function(){
	            			window.location.href = 'resources/j_spring_security_logout';
	                    }
	            	}]
	            },
	        	
            	items: [{
            		xtype: 'info_centru_edit',
            		title : 'Informații centru'
            		
            	},{
            		xtype: 'info_edit',
            		title : 'Informații generale'
            		
            	},{
	            	xtype: 'pupil_main',
	            	title: 'Informații beneficiari'
	            },{
	            	xtype: 'panel',
	            	title: 'Admin',
	            	items : [{
	            		xtype: 'import',
	            		title: 'Import date din teritoriu'
	            	},{
	            		xtype: 'report',
	            		title: 'Raportare'
	            	}]
	            }]
	            
	        },{
	        	xtype: 'component',
	        	html: '<iframe id="download_frame"> </iframe>'
	        }]
            
        });
    }

});
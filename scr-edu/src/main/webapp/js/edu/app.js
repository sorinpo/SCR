Ext.Loader.setConfig({
    disableCaching: false
});

Ext.namespace('Helpers');
Helpers = {
    
	criticalOperationFailed : function(){
		alert("Aplicația nu a putut fi pornită. Incercați un refresh cu CTRL+F5.");
	},	
		
	operationFailed : function(){
		console.log("Aplicația nu a putut fi pornită. Incercați un refresh cu CTRL+F5.");
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
        'Users'
    ],
    
    controllers : [
        'InfoController',
        'PupilController',
        'ImportController',
        'ReportController',
        'ConfigController'
    ],
    
    launch: function() {
    
    	var me=this,
    		counter = new Counter({ counter: 2, handler: me.loadViewport, scope: me });
    	
    	me.getStore('Users').load(function(records, operation, success) {
    		//XXX on failure ?
    		if(!APP_SEC.isAdmin){//for the admin, the loading of the users store will trigger the setting of runas param
    			ParamManager.set('runas', APP_SEC.username);    			
    		}
    		
    		Ext.callback(counter.down, counter);
    		
    	});
    	
    	EDU.model.Config.load(null, {
    		success: function(config){
    			ParamManager.set('activeYear', config.get('activeYear') );
    			ParamManager.set('activeMonths', config.get('activeMonths') );
    			ParamManager.set('config', config );
    			Ext.callback(counter.down, counter);
    		},
    	    failure: Helpers.criticalOperationFailed
    	});
    	        
    },
    
    init: function() {
    	var me = this;
    	
    	if(APP_SEC.isAdmin){
	        me.control({
	        	'#centrul' :{
	        		
	        		afterrender : function(combo){
	        			var rec = combo.getStore().first();
	        			ParamManager.set('runas', rec.get('username'));
	        			combo.select( rec );
	        		},
	        		
	        		select: function(combo, values){
	        			ParamManager.set('runas', values[0].get('username'));
	        		}
	        	}
	        });
    	}
        
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
	            	hidden: !APP_SEC.isAdmin,
	            	items : [{
	            		xtype: 'config',
	            		title: 'Configurare'
	            	}/*,{
	            		xtype: 'import',
	            		title: 'Import date din teritoriu'
	            	}*/,{
	            		xtype: 'report',
	            		itemId: 'report',
	            		title: 'Raportare'
	            	},{
	            		xtype: 'report',
	            		itemId: 'export',
	            		title: 'Export'
	            	}]
	            }]
	            
	        },{
	        	xtype: 'component',
	        	html: '<iframe id="download_frame"> </iframe>'
	        }]
            
        });
    }

});
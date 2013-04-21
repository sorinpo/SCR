//mixin that alows store proxy urls to be parametrizable
Ext.define('EDU.component.UrlParams', {
	requires : ['EDU.component.ParamManager'],
	
	urlParams : {},
	
	constructor: function(config){
		alert(arguments);
	}
});
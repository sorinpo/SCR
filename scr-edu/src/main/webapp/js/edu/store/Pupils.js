Ext.define('EDU.store.Pupils', {
    extend: 'Ext.data.Store',
    
/*	mixins: {
        urlParams: 'EDU.component.UrlParams'
    },
    constructor: function(config){
        this.mixins.urlParams.constructor.call(this, config);
    	this.callParent(arguments);
    },*/
    
    model: 'EDU.model.Pupil',
    
    proxy: {
        type: 'rest',
        appendId: false,
        url: SERVER_ROOT + 'pupils.json',
        //buildUrl: Helpers.buildUrl,
        getParams: Helpers.getParams({'owner':'runas'}),
        reader: {
            type: 'json'
        }
    }/*,

	autoLoad: true*/
});
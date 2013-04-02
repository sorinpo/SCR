Ext.define('EDU.view.pupil.List' ,{
    extend: 'Ext.grid.Panel',
    alias: 'widget.pupil_list',

    store: 'Pupils',
    
    initComponent: function() {
        
        this.columns = [
            {header: 'Nume',  dataIndex: 'name',  flex: 1},
            {header: 'Situație Părinți', dataIndex: 'parentState', flex: 1,
            	renderer : function(value){
            		if(value === 'MOTHER') {
            			return 'Doar mama plecată';
            		} else if(value === 'FATHER') {
            			return 'Doar tatăl plecat';
            		} else if(value === 'BOTH') {
            			return 'Ambii parinți plecați';
            		} else {
            			return '';
            		}
            	}
            },
            {header: 'Ţara unde este plecat părintele', dataIndex: 'leftToCountry', flex: 1},
            {header: 'Observații', dataIndex: 'comment', flex: 1},
            {header: 'Pregătire şcolară', dataIndex: 'school', flex: 1,
            	renderer : function(val) {
            		var html = [];
            		
            		if(val.jan) html.push("ian");
            		if(val.feb) html.push("feb");
            		if(val.mar) html.push("mar");
            		if(val.apr) html.push("apr");
            		if(val.may) html.push("mai");
            		if(val.jun) html.push("iun");
            		if(val.jul) html.push("iul");
            		if(val.aug) html.push("aug");
            		if(val.sep) html.push("sep");
            		if(val.oct) html.push("oct");
            		if(val.nov) html.push("noi");
            		if(val.dec) html.push("dec");
            		
            		return html.join();
            	}
            }
        ];
        
        if(APP_SEC.isAdmin){
        	this.columns.push({header: 'Județ', dataIndex: 'county', flex: 1});
        }
        
        this.bbar = [
             {text: 'Adaugă', action: 'add'}
        ]
        if(APP_SEC.isAdmin){
        	this.bbar.push({text: 'Șterge', action: 'delete'});
        }
        
        //last call
        this.callParent(arguments);
    }
});
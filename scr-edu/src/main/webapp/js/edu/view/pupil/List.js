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
            {header: 'Observații', dataIndex: 'comment', flex: 1}
        ];
        
        this.tbar = [
             {text: 'Adaugă', action: 'add'}
        ]
        if(APP_SEC.isAdmin){
        	this.tbar.push({text: 'Șterge', action: 'delete'});
        }
        
        //last call
        this.callParent(arguments);
    }
});
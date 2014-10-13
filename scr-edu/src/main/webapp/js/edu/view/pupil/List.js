Ext.define('EDU.view.pupil.List' ,{
    extend: 'Ext.grid.Panel',
    alias: 'widget.pupil_list',

    store: 'Pupils',
    
    initComponent: function() {
        
        this.columns = [
            {header: 'Nr.', xtype: 'rownumberer', width: 30},
            {header: 'Nume',  dataIndex: 'name',  flex: 1},
            {header: 'Data nașterii',  dataIndex: 'birthDate', xtype: 'datecolumn', format:'j/n/Y', flex: 1},
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
            {header: 'Data includere',  dataIndex: 'recruitmentDate', xtype: 'datecolumn', format:'j/n/Y', flex: 1},
            {header: 'Modalitate includere', dataIndex: 'recruitmentMethod', flex: 1,
            	renderer : function(value){
            		if(value === 'PROJECT_TEAM') {
            			return 'Echipa';
            		} else if(value === 'PARTNERS') {
            			return 'Parteneri';
            		} else if(value === 'COMMUNITY') {
            			return 'Comunitate';
            		} else {
            			return '';
            		}
            	}
            },
            {header: 'Observații', dataIndex: 'comment', flex: 1}
        ];
        
        var tbar = [
             {text: 'Adaugă', action: 'add'}
        ]
        if(APP_SEC.isAdmin){
        	tbar.push({text: 'Șterge', action: 'delete'});
        }
        
        tbar.push({
        	xtype: 'textfield',
        	itemId: 'filter',
        	width: 200,
        	emptyText: 'Tastați pentru a filtra lista'
        });
        
        this.tbar = tbar;
        
        //last call
        this.callParent(arguments);
    }
});
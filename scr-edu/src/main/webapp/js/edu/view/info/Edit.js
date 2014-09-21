Ext.define('EDU.view.info.Edit', {
    extend: 'Ext.form.Panel',
    alias: 'widget.info_edit',
    
    requires : ['EDU.component.form.InfoGroup'],
    
    defaults: {
    	anchor: '100%',
    	padding: 3
    },
    
    items: [{
    	xtype: 'info',
    	name: 'beneficiariIndirecti',
    	fieldLabel : 'Nr. beneficiari indirecți'
    },{
    	xtype: 'info',
    	name: 'voluntariImplicati',
    	fieldLabel : 'Nr. voluntari implicați'
    },{
    	xtype: 'info',
    	name: 'intalniriGrupuriLucru',
    	fieldLabel : 'Nr. întâlniri grupuri de lucru'
    },{
    	xtype: 'info',
    	name: 'participantiGrupuriLucru',
    	fieldLabel : 'Nr. participanți la grupurile de lucru'
    },{
    	xtype: 'info',
    	name: 'conferinteOrganizate',
    	fieldLabel : 'Nr. de conferințe organizate'
    },{
    	xtype: 'info',
    	name: 'participantiConferinte',
    	fieldLabel : 'Nr. participanți la conferințe'
    },{
    	xtype: 'info',
    	name: 'aparitiiPresa',
    	fieldLabel : 'Nr. apariții în presă'
    },{
    	xtype: 'textarea',
    	name: 'links',
    	fieldLabel : 'Link-uri/nume ale aparițiilor'
    }],
    
    dockedItems: {
        xtype: 'toolbar',
        dock: 'top',
        items: [{
            text: 'Salvează',
            action: 'save'
        }]
    }
});
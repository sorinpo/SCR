Ext.define('EDU.store.Pupils', {
    extend: 'Ext.data.Store',
    
    model: 'EDU.model.Pupil',
    
    proxy: {
        type: 'ajax',
        url: 'pupils.json',
        reader: {
            type: 'json'
        }
    },

	autoLoad: true
});
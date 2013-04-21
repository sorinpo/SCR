Ext.define('EDU.store.Participations', {
    extend: 'Ext.data.Store',
    
    model: 'EDU.model.Pupil',
    
    proxy: {
        type: 'rest',
        appendId: false,
        url: SERVER_ROOT + 'participations.json',
        getParams: Helpers.getParams({'year' : 'activeYear'}),
        reader: {
            type: 'json'
        }
    }
});
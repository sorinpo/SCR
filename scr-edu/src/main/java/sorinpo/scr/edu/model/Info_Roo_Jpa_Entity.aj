// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package sorinpo.scr.edu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import sorinpo.scr.edu.model.Info;

privileged aspect Info_Roo_Jpa_Entity {
    
    declare @type: Info: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Info.id;
    
    @Version
    @Column(name = "version")
    private Integer Info.version;
    
    public Info.new() {
        super();
    }

    public Long Info.getId() {
        return this.id;
    }
    
    public void Info.setId(Long id) {
        this.id = id;
    }
    
    public Integer Info.getVersion() {
        return this.version;
    }
    
    public void Info.setVersion(Integer version) {
        this.version = version;
    }
    
}

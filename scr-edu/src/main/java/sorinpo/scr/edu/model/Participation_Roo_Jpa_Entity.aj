// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package sorinpo.scr.edu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import sorinpo.scr.edu.model.Participation;

privileged aspect Participation_Roo_Jpa_Entity {
    
    declare @type: Participation: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Participation.id;
    
    @Version
    @Column(name = "version")
    private Integer Participation.version;
    
    public Participation.new() {
        super();
    }

    public Long Participation.getId() {
        return this.id;
    }
    
    public void Participation.setId(Long id) {
        this.id = id;
    }
    
    public Integer Participation.getVersion() {
        return this.version;
    }
    
    public void Participation.setVersion(Integer version) {
        this.version = version;
    }
    
}
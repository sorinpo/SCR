// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package sorinpo.scr.edu.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sorinpo.scr.edu.model.Pupil;

privileged aspect Pupil_Roo_Equals {
    
    public boolean Pupil.equals(Object obj) {
        if (!(obj instanceof Pupil)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Pupil rhs = (Pupil) obj;
        return new EqualsBuilder().append(comment, rhs.comment).append(deleteRequested, rhs.deleteRequested).append(id, rhs.id).append(leftToCountry, rhs.leftToCountry).append(locked, rhs.locked).append(name, rhs.name).append(owner, rhs.owner).append(parentState, rhs.parentState).append(unlockRequested, rhs.unlockRequested).isEquals();
    }
    
    public int Pupil.hashCode() {
        return new HashCodeBuilder().append(comment).append(deleteRequested).append(id).append(leftToCountry).append(locked).append(name).append(owner).append(parentState).append(unlockRequested).toHashCode();
    }
    
}

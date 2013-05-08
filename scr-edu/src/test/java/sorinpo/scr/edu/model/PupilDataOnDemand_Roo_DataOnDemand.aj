// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package sorinpo.scr.edu.model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;
import sorinpo.scr.edu.model.Pupil;
import sorinpo.scr.edu.model.Pupil.ParentState;
import sorinpo.scr.edu.model.PupilDataOnDemand;

privileged aspect PupilDataOnDemand_Roo_DataOnDemand {
    
    declare @type: PupilDataOnDemand: @Component;
    
    private Random PupilDataOnDemand.rnd = new SecureRandom();
    
    private List<Pupil> PupilDataOnDemand.data;
    
    public Pupil PupilDataOnDemand.getNewTransientPupil(int index) {
        Pupil obj = new Pupil();
        setComment(obj, index);
        setDeleteRequested(obj, index);
        setLeftToCountry(obj, index);
        setLocked(obj, index);
        setName(obj, index);
        setOwner(obj, index);
        setParentState(obj, index);
        setUnlockRequested(obj, index);
        return obj;
    }
    
    public void PupilDataOnDemand.setComment(Pupil obj, int index) {
        String comment = "comment_" + index;
        obj.setComment(comment);
    }
    
    public void PupilDataOnDemand.setDeleteRequested(Pupil obj, int index) {
        Boolean deleteRequested = true;
        obj.setDeleteRequested(deleteRequested);
    }
    
    public void PupilDataOnDemand.setLeftToCountry(Pupil obj, int index) {
        String leftToCountry = "leftToCountry_" + index;
        obj.setLeftToCountry(leftToCountry);
    }
    
    public void PupilDataOnDemand.setLocked(Pupil obj, int index) {
        Boolean locked = true;
        obj.setLocked(locked);
    }
    
    public void PupilDataOnDemand.setName(Pupil obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }
    
    public void PupilDataOnDemand.setOwner(Pupil obj, int index) {
        String owner = "owner_" + index;
        obj.setOwner(owner);
    }
    
    public void PupilDataOnDemand.setParentState(Pupil obj, int index) {
        ParentState parentState = ParentState.class.getEnumConstants()[0];
        obj.setParentState(parentState);
    }
    
    public void PupilDataOnDemand.setUnlockRequested(Pupil obj, int index) {
        Boolean unlockRequested = true;
        obj.setUnlockRequested(unlockRequested);
    }
    
    public Pupil PupilDataOnDemand.getSpecificPupil(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Pupil obj = data.get(index);
        Long id = obj.getId();
        return Pupil.findPupil(id);
    }
    
    public Pupil PupilDataOnDemand.getRandomPupil() {
        init();
        Pupil obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Pupil.findPupil(id);
    }
    
    public boolean PupilDataOnDemand.modifyPupil(Pupil obj) {
        return false;
    }
    
    public void PupilDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Pupil.findPupilEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Pupil' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Pupil>();
        for (int i = 0; i < 10; i++) {
            Pupil obj = getNewTransientPupil(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}

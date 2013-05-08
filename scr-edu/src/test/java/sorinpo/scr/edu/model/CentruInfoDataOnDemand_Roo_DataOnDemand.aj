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
import sorinpo.scr.edu.model.CentruInfo;
import sorinpo.scr.edu.model.CentruInfoDataOnDemand;

privileged aspect CentruInfoDataOnDemand_Roo_DataOnDemand {
    
    declare @type: CentruInfoDataOnDemand: @Component;
    
    private Random CentruInfoDataOnDemand.rnd = new SecureRandom();
    
    private List<CentruInfo> CentruInfoDataOnDemand.data;
    
    public CentruInfo CentruInfoDataOnDemand.getNewTransientCentruInfo(int index) {
        CentruInfo obj = new CentruInfo();
        setAddress(obj, index);
        setLocality(obj, index);
        setTeam(obj, index);
        setUserId(obj, index);
        return obj;
    }
    
    public void CentruInfoDataOnDemand.setAddress(CentruInfo obj, int index) {
        String address = "address_" + index;
        obj.setAddress(address);
    }
    
    public void CentruInfoDataOnDemand.setLocality(CentruInfo obj, int index) {
        String locality = "locality_" + index;
        obj.setLocality(locality);
    }
    
    public void CentruInfoDataOnDemand.setTeam(CentruInfo obj, int index) {
        String team = "team_" + index;
        if (team.length() > 1024) {
            team = team.substring(0, 1024);
        }
        obj.setTeam(team);
    }
    
    public void CentruInfoDataOnDemand.setUserId(CentruInfo obj, int index) {
        Long userId = new Integer(index).longValue();
        obj.setUserId(userId);
    }
    
    public CentruInfo CentruInfoDataOnDemand.getSpecificCentruInfo(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        CentruInfo obj = data.get(index);
        Long id = obj.getId();
        return CentruInfo.findCentruInfo(id);
    }
    
    public CentruInfo CentruInfoDataOnDemand.getRandomCentruInfo() {
        init();
        CentruInfo obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return CentruInfo.findCentruInfo(id);
    }
    
    public boolean CentruInfoDataOnDemand.modifyCentruInfo(CentruInfo obj) {
        return false;
    }
    
    public void CentruInfoDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = CentruInfo.findCentruInfoEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'CentruInfo' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<CentruInfo>();
        for (int i = 0; i < 10; i++) {
            CentruInfo obj = getNewTransientCentruInfo(i);
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

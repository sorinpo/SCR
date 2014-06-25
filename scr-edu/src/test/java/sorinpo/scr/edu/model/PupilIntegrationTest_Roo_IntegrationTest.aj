// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package sorinpo.scr.edu.model;

import java.util.Iterator;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import sorinpo.scr.edu.model.Pupil;
import sorinpo.scr.edu.model.PupilDataOnDemand;
import sorinpo.scr.edu.model.PupilIntegrationTest;

privileged aspect PupilIntegrationTest_Roo_IntegrationTest {
    
    declare @type: PupilIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: PupilIntegrationTest: @Transactional;
    
    @Autowired
    PupilDataOnDemand PupilIntegrationTest.dod;
    
    @Test
    public void PupilIntegrationTest.testCountPupils() {
        Assert.assertNotNull("Data on demand for 'Pupil' failed to initialize correctly", dod.getRandomPupil());
        long count = Pupil.countPupils();
        Assert.assertTrue("Counter for 'Pupil' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void PupilIntegrationTest.testFindPupil() {
        Pupil obj = dod.getRandomPupil();
        Assert.assertNotNull("Data on demand for 'Pupil' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Pupil' failed to provide an identifier", id);
        obj = Pupil.findPupil(id);
        Assert.assertNotNull("Find method for 'Pupil' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Pupil' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void PupilIntegrationTest.testFindAllPupils() {
        Assert.assertNotNull("Data on demand for 'Pupil' failed to initialize correctly", dod.getRandomPupil());
        long count = Pupil.countPupils();
        Assert.assertTrue("Too expensive to perform a find all test for 'Pupil', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Pupil> result = Pupil.findAllPupils();
        Assert.assertNotNull("Find all method for 'Pupil' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Pupil' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void PupilIntegrationTest.testFindPupilEntries() {
        Assert.assertNotNull("Data on demand for 'Pupil' failed to initialize correctly", dod.getRandomPupil());
        long count = Pupil.countPupils();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Pupil> result = Pupil.findPupilEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Pupil' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Pupil' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void PupilIntegrationTest.testFlush() {
        Pupil obj = dod.getRandomPupil();
        Assert.assertNotNull("Data on demand for 'Pupil' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Pupil' failed to provide an identifier", id);
        obj = Pupil.findPupil(id);
        Assert.assertNotNull("Find method for 'Pupil' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyPupil(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Pupil' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void PupilIntegrationTest.testMergeUpdate() {
        Pupil obj = dod.getRandomPupil();
        Assert.assertNotNull("Data on demand for 'Pupil' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Pupil' failed to provide an identifier", id);
        obj = Pupil.findPupil(id);
        boolean modified =  dod.modifyPupil(obj);
        Integer currentVersion = obj.getVersion();
        Pupil merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Pupil' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void PupilIntegrationTest.testPersist() {
        Assert.assertNotNull("Data on demand for 'Pupil' failed to initialize correctly", dod.getRandomPupil());
        Pupil obj = dod.getNewTransientPupil(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Pupil' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Pupil' identifier to be null", obj.getId());
        try {
            obj.persist();
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        obj.flush();
        Assert.assertNotNull("Expected 'Pupil' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void PupilIntegrationTest.testRemove() {
        Pupil obj = dod.getRandomPupil();
        Assert.assertNotNull("Data on demand for 'Pupil' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Pupil' failed to provide an identifier", id);
        obj = Pupil.findPupil(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Pupil' with identifier '" + id + "'", Pupil.findPupil(id));
    }
    
}

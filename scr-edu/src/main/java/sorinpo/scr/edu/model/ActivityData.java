package sorinpo.scr.edu.model;

import javax.persistence.Embeddable;

import org.springframework.roo.addon.javabean.RooJavaBean;

@Embeddable
@RooJavaBean
public class ActivityData {
	
    private boolean jan;
    private boolean feb;
    private boolean mar;
    private boolean apr;
    private boolean may;
    private boolean jun;
    private boolean jul;
    private boolean aug;
    private boolean sep;
    private boolean oct;
    private boolean nov;
    private boolean dec;
   
}

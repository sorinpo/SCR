package sorinpo.scr.edu.model;

import javax.persistence.Embeddable;

import org.springframework.roo.addon.javabean.RooJavaBean;

@Embeddable
@RooJavaBean
public class ActivityData {
	
    private Boolean jan;
    private Boolean feb;
    private Boolean mar;
    private Boolean apr;
    private Boolean may;
    private Boolean jun;
    private Boolean jul;
    private Boolean aug;
    private Boolean sep;
    private Boolean oct;
    private Boolean nov;
    private Boolean dec;
    
    public ActivityData(){
    	this(false);
    }
    
    public ActivityData(boolean initialize){
    	if(initialize){
    		jan = feb = mar = apr = may = jun = jul = aug = sep = oct = nov = dec = false;
    	}
    };
    
}

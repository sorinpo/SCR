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
    }
    
    //XXX bad desing
    public void nullToFalse(){
    	if(jan==null) jan = false;
    	if(feb==null) feb = false;
    	if(mar==null) mar = false;
    	if(apr==null) apr = false;
    	if(may==null) may = false;
    	if(jun==null) jun = false;
    	if(jul==null) jul = false;
    	if(aug==null) aug = false;
    	if(sep==null) sep = false;
    	if(oct==null) oct = false;
    	if(nov==null) nov = false;
    	if(dec==null) dec = false;
    }

}

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
    
    public Boolean get(int month) {
    	switch(month) {
    		case  0: return jan;
    		case  1: return feb;
    		case  2: return mar;
    		case  3: return apr;
    		case  4: return may;
    		case  5: return jun;
    		case  6: return jul;
    		case  7: return aug;
    		case  8: return sep;
    		case  9: return oct;
    		case 10: return nov;
    		case 11: return dec;
    		
    		default: throw new IllegalArgumentException("Month number " + month + " is not defined");
    	}
    }

}

package sorinpo.scr.edu.model;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.roo.addon.javabean.RooJavaBean;

import sorinpo.scr.edu.model.Pupil.ParentState;

@Embeddable
@RooJavaBean
public class ParentalCommunicationData {

	@Enumerated(EnumType.STRING)
    private ParentState jan;
	
	@Enumerated(EnumType.STRING)
    private ParentState feb;
	
	@Enumerated(EnumType.STRING)
    private ParentState mar;
	
	@Enumerated(EnumType.STRING)
    private ParentState apr;
	
	@Enumerated(EnumType.STRING)
    private ParentState may;
	
	@Enumerated(EnumType.STRING)
    private ParentState jun;
	
	@Enumerated(EnumType.STRING)
    private ParentState jul;
	
	@Enumerated(EnumType.STRING)
    private ParentState aug;
	
	@Enumerated(EnumType.STRING)
    private ParentState sep;
	
	@Enumerated(EnumType.STRING)
    private ParentState oct;
	
	@Enumerated(EnumType.STRING)
    private ParentState nov;
	
	@Enumerated(EnumType.STRING)
    private ParentState dec;
    
    public ParentalCommunicationData(){
    	this(false);
    }
    
    public ParentalCommunicationData(boolean initialize){
    	if(initialize){
    		jan = feb = mar = apr = may = jun = jul = aug = sep = oct = nov = dec = ParentState.NONE;
    	}
    }
    
    public void nullToNone(){
    	if(jan==null) jan = ParentState.NONE;
    	if(feb==null) feb = ParentState.NONE;
    	if(mar==null) mar = ParentState.NONE;
    	if(apr==null) apr = ParentState.NONE;
    	if(may==null) may = ParentState.NONE;
    	if(jun==null) jun = ParentState.NONE;
    	if(jul==null) jul = ParentState.NONE;
    	if(aug==null) aug = ParentState.NONE;
    	if(sep==null) sep = ParentState.NONE;
    	if(oct==null) oct = ParentState.NONE;
    	if(nov==null) nov = ParentState.NONE;
    	if(dec==null) dec = ParentState.NONE;
    }
    
    public ParentState get(int month) {
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

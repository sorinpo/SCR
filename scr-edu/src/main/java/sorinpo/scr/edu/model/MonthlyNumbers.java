package sorinpo.scr.edu.model;

import javax.persistence.Embeddable;

import org.springframework.roo.addon.javabean.RooJavaBean;

@Embeddable
@RooJavaBean
public class MonthlyNumbers {
    private int jan;
    private int feb;
    private int mar;
    private int apr;
    private int may;
    private int jun;
    private int jul;
    private int aug;
    private int sep;
    private int oct;
    private int nov;
    private int dec;	
}
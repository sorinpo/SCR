package sorinpo.scr.edu.model;

public enum Month {
	JAN(1,"Ianuarie","Ian"), FEB(2,"Februarie","Feb"), MAR(3,"Martie","Mar"), 
	APR(4,"Aprilie","Apr"), MAY(5,"Mai","Mai"), JUN(6,"Iunie","iun"), 
	JUL(7,"Iulie","Iul"), AUG(8,"August","Aug"), SEP(9,"Septembrie","Sep"), 
	OCT(10,"Octombrie","Oct"), NOV(11,"Noiembrie","Nov"), DEC(12,"Decembrie","Dec");
	
	private int ord;
	private String name;
	private String abbr;
	
	Month(int ord, String name, String abbr){
		this.ord = ord;
		this.name = name;
		this.abbr = abbr;
	}
	
	public int getOrd(){
		return ord;
	}
	
	public String getName(){
		return name;
	}
	
	public String getAbbr(){
		return abbr;
	}
	
}

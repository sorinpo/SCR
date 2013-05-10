package sorinpo.scr.edu.model;

import javax.persistence.Embedded;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(finders = { "findInfoesByUserIdAndYear" })
public class Info {
	
	public Info(long userId, int year){
		this.userId = userId;
		this.year = year;
	}
	
	public void initializeMonthlyNumbers(){
		beneficiariIndirecti = new MonthlyNumbers();
		voluntariImplicati = new MonthlyNumbers();
		intalniriGrupuriLucru = new MonthlyNumbers();
		participantiGrupuriLucru = new MonthlyNumbers();
		conferinteOrganizate = new MonthlyNumbers();
		participantiConferinte = new MonthlyNumbers();
		aparitiiPresa = new MonthlyNumbers();
	}
	
	private int year;
	Long userId;
	
	@Embedded
	private MonthlyNumbers beneficiariIndirecti;
	@Embedded
	private MonthlyNumbers voluntariImplicati;
	@Embedded
	private MonthlyNumbers intalniriGrupuriLucru;
	@Embedded
	private MonthlyNumbers participantiGrupuriLucru;
	@Embedded
	private MonthlyNumbers conferinteOrganizate;
	@Embedded
	private MonthlyNumbers participantiConferinte;
	@Embedded
	private MonthlyNumbers aparitiiPresa;
	
	private String links;

}

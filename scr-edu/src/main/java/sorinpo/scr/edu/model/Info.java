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
	
	public Info(Long userId, int year){
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
	
//	@ElementCollection
//	Collection<String> links;
	
	String links;
	
//	public enum InfoType {
//		BENEFICIARI_INDIRECTI,
//		VOLUNTARI_IMPLICATI,
//		INTALNIRI_GRUPURI_LUCRU,
//		PARTICIPANTI_GRUPURI_LUCRU,
//		CONFERINTE_ORGANIZATE,
//		PARTICIPANTI_CONFERINTE,
//		APARITII_PRESA
//	}

}

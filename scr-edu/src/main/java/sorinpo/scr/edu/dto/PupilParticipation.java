package sorinpo.scr.edu.dto;

import sorinpo.scr.edu.model.Participation;
import sorinpo.scr.edu.model.Pupil;

public class PupilParticipation {
	private Pupil pupil;
	private Participation participation;
		
	public PupilParticipation(){
		this(null, null);
	}
	
	public PupilParticipation(Pupil pupil, Participation participation) {
		super();
		this.pupil = pupil;
		this.participation = participation;
	}
	public Pupil getPupil() {
		return pupil;
	}
	public void setPupil(Pupil pupil) {
		this.pupil = pupil;
	}
	public Participation getParticipation() {
		return participation;
	}
	public void setParticipation(Participation participation) {
		this.participation = participation;
	}
		
}

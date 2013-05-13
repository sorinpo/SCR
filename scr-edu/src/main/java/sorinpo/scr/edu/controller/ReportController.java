package sorinpo.scr.edu.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sorinpo.scr.edu.dto.PupilParticipation;
import sorinpo.scr.edu.model.Participation;
import sorinpo.scr.edu.model.Pupil;
import sorinpo.scr.edu.service.ReportService;
import sorinpo.scr.edu.service.ReportService.ExportException;
import sorinpo.scr.edu.util.SecurityUtil;

@Controller
@RequestMapping(value = "/report")
public class ReportController {

	private static final Logger log = LoggerFactory
			.getLogger(ReportController.class);

	@Autowired
	private ReportService exportService;

	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public void createReport(@RequestParam("users") Collection<String> users,
			@RequestParam("year") int year, HttpServletResponse response) {

		try {

			if (!SecurityUtil.isAdmin()) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}

			List<PupilParticipation> rep = new ArrayList<PupilParticipation>();
			
			if(users.size()>0){
			
				List<Pupil> pupils = Pupil.findPupilsByOwnerIn(users).getResultList();
				Map<Long, Pupil> pupilIds = new HashMap<Long, Pupil>();
				for(Pupil pupil: pupils){
					pupilIds.put(pupil.getId(), pupil);
				}
				
				if(pupilIds.size() > 0) {
				
					List<Participation> pps = Participation.findParticipationsByPupilIdsAndYear(pupilIds.keySet(), year).getResultList();
					
					for(Participation pp: pps){
						Pupil pupil = pupilIds.get(pp.getPupilId());
						if(pupil!=null){
							rep.add(new PupilParticipation(pupil, pp));
						}
						
					}
				
				}
				
			}
			
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=\"Raport.xlsx\"");
			
			exportService.writePupilParticipations(rep, response.getOutputStream());
			
			response.flushBuffer();

		} catch (IOException e) {
			log.error("Failed to serve report", e);
		} catch (ExportException e){
			log.error("Failed to serve report", e);
		}
	}

	public ReportService getExportService() {
		return exportService;
	}

	public void setExportService(ReportService exportService) {
		this.exportService = exportService;
	}
	
}

package sorinpo.scr.edu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sorinpo.scr.edu.dto.PupilParticipation;
import sorinpo.scr.edu.model.Participation;
import sorinpo.scr.edu.model.Pupil;
import sorinpo.scr.edu.service.ExportService;
import sorinpo.scr.edu.service.ExportService.ExportException;
import sorinpo.scr.edu.util.SecurityUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping(value = "/export")
public class ExportController {

	private static final Logger log = LoggerFactory
			.getLogger(ExportController.class);

	private ExportService exportService;

	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public void createReport(@RequestParam("users") Collection<String> users,
			@RequestParam("year") int year, HttpServletResponse response) {

		try {

			if (!SecurityUtil.isAdmin()) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}

			List<PupilParticipation> rep = new ArrayList<>();
			if(users.size()>0) {
				List<Pupil> pupils = Pupil.findPupilsByOwnerIn(users).getResultList();
				for(Pupil pupil: pupils){
                    Participation pp;
                    try {
                        pp = Participation.findParticipationByPupilIdAndYear(pupil.getId(), year).getSingleResult();
                    } catch (EmptyResultDataAccessException e) {
                        pp = new Participation(pupil.getId(), year);
                        pp.initializeActivityData();
                    }
                    rep.add(new PupilParticipation(pupil, pp));
				}
			}
			
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=\"Export.xlsx\"");
			
			exportService.writePupils(rep, response.getOutputStream());
			
			response.flushBuffer();

		} catch (IOException | ExportException e) {
			log.error("Failed to serve report", e);
		}
	}

    @Autowired
	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}
	
}

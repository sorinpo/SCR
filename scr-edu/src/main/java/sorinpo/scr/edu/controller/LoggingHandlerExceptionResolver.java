package sorinpo.scr.edu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoggingHandlerExceptionResolver implements
		HandlerExceptionResolver, Ordered {

	private static final Logger log = LoggerFactory
			.getLogger(LoggingHandlerExceptionResolver.class);
	
	int order=0;
	
	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int getOrder() {
		return order;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		log.error(ex.getMessage(), ex);
		
		return null;
	}

}

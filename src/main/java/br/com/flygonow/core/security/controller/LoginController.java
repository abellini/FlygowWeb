package br.com.flygonow.core.security.controller;

import br.com.flygonow.core.security.service.SecurityService;
import br.com.flygonow.entities.Attendant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Controller
public class LoginController implements MessageSourceAware{
	
	private static final Logger LOGGER = Logger.getLogger(LoginController.class);
	
	@Autowired
	private SecurityService securityService;
	
	private MessageSource messageSource;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping("/login")
	public String loginPage(@RequestHeader("User-Agent") String userAgent) {
		return "login";
	}
	
	/**
	 * @return
	 */
	private Attendant userInfo() {
		return securityService.getLoggedUserModel();
	}
	
	@RequestMapping("/index")
	public String getIndex(
			HttpServletResponse httpResponse, 
			Model model,
			Locale locale){
		model.addAttribute("LoggedUserInfo", userInfo());
		return "index";
	}
	
	@RequestMapping("/login.action")
	public String doLogin(@RequestParam("response") Integer code,
			HttpServletResponse httpResponse, 
			Model model,
			Locale locale){ 
		String message = "", page = "denied/incorrect";
		switch (code) {
		case 0:
			message = "login.validation.sucess";
			page = "index";
			model.addAttribute("LoggedUserInfo", userInfo());
			model.addAttribute("Wallpaper", messageSource.getMessage("wallpaper.image", null, locale));
			return page; 
		case 1:
			message = "login.validation.account.invalid";
			break;
		case 2:
			message = "login.validation.account.locked";
			break;
		case 3:
			message = "login.validation.account.expired";
			break;
		case 4:
			message = "login.validation.account.pwd.expired";
			break;
		case 5:
			message = "login.validation.account.incorrect";
			break;
		case 6:
			message = "login.validation.account.failure";
			break;
		}
		LOGGER.warn(messageSource.getMessage(message, null, locale));
		model.addAttribute("styleBackground", "styleBg"+code);
		model.addAttribute("AboutUs", messageSource.getMessage("login.interface.aboutus", null, locale));
		model.addAttribute("Service", messageSource.getMessage("login.interface.service", null, locale));
		model.addAttribute("ContactUs", messageSource.getMessage("login.interface.contactus", null, locale));
		model.addAttribute("ReturnMessage", messageSource.getMessage("login.interface.goback", null, locale));
		model.addAttribute("MainMessage", messageSource.getMessage(message, null, locale));
		
		return page;
	}
}

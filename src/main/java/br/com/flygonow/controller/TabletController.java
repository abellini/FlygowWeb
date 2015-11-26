package br.com.flygonow.controller;

import br.com.flygonow.dao.AdvertisementDao;
import br.com.flygonow.dao.AttendantDao;
import br.com.flygonow.dao.CoinDao;
import br.com.flygonow.dao.TabletDao;
import br.com.flygonow.entities.Advertisement;
import br.com.flygonow.entities.Attendant;
import br.com.flygonow.entities.Coin;
import br.com.flygonow.entities.Tablet;
import br.com.flygonow.enums.FetchTypeEnum;
import br.com.flygonow.service.TabletService;
import br.com.flygonow.util.JSONView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/tablet")
public class TabletController implements MessageSourceAware{

	private static final Logger LOGGER = Logger.getLogger(TabletController.class);
	
	private MessageSource messageSource;
	
	@Autowired
	private TabletService tabletService;
	
	@Autowired
	private TabletDao tabletDAO;
	
	@Autowired
	private CoinDao coinDAO;
	
	@Autowired
	private AttendantDao attendantDAO;
	
	@Autowired
	private AdvertisementDao advertisementDAO;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@RequestMapping("/listAll")
	public @ResponseBody String listAll(
			@RequestParam(required = false) String strSearch,
			Locale locale
		){
		try{
			Collection<Tablet> tablets = null;
			if(strSearch != null && !"".equals(strSearch)){
				tablets = tabletDAO.listByNumber(strSearch);
			}else{
				tablets = tabletDAO.getAll(FetchTypeEnum.LEFT.getName(), "advertisements");
			}
			return JSONView.fromTablet(tablets);
		}catch(Exception e){
			String message = messageSource.getMessage("error.list.items", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}
	
	@RequestMapping("/delete")
	public @ResponseBody String delete(
			@RequestParam String ids,
			Locale locale
	){
		try{
			List<Long> toIds = new ArrayList<Long>();
			for (String id : Arrays.asList(ids.split(","))) {
				if (!"".equals(id))
					toIds.add(Long.parseLong(id));
			}
			for (Long id : toIds) {
				tabletService.delete(id);
			}
			return JSONView.getJsonSuccess(true, null);
		}catch(Exception e){
			String message = messageSource.getMessage("error.delete.items", null, locale);
			LOGGER.error(message, e);
			return JSONView.getJsonSuccess(false, message);
		}
	}
	
	@RequestMapping("/saveUpdate")
	public @ResponseBody String saveUpdate(
		@RequestParam(required=false) Long id,
		@RequestParam Short number,
		@RequestParam Long coinId,
		@RequestParam String ip,
		@RequestParam Integer port,
		@RequestParam String serverIp,
		@RequestParam Integer serverPort,
		@RequestParam Long attendantId,
		@RequestParam(required=false) String advertisementId,
		Locale locale
	){
		Tablet tablet = null;
		try {
			tablet = new Tablet(id, number, ip, port, serverIp, serverPort);
		}catch(Exception e1){
			e1.printStackTrace();
		}
		
		Attendant att = attendantDAO.findById(attendantId, null);
		Coin coin = coinDAO.findById(coinId, null);
		Set<Advertisement> advs = new HashSet<Advertisement>();
		if(advertisementId != null){
			for (String advId : Arrays.asList(advertisementId.split(","))) {
				if (!"".equals(advId)){
					Advertisement adv = advertisementDAO.findById(Long.parseLong(advId), null);
					advs.add(adv);
				}	
			}
		}
		if(tablet.getId() == null){
			try{
				tablet.setAttendant(att);
				tablet.setCoin(coin);
				tablet.setAdvertisements(advs);
				tabletDAO.save(tablet);
			}catch(Exception e){
				String message = messageSource.getMessage("error.save.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}else{
			try{
				tablet.setAttendant(att);
				tablet.setCoin(coin);
				tablet.setAdvertisements(advs);
				tabletDAO.update(tablet);
			}catch(Exception e){
				String message = messageSource.getMessage("error.update.item", null, locale);
				LOGGER.error(message, e);
				return JSONView.getJsonSuccess(false, message);
			}
		}
		return JSONView.getJsonSuccess(true, null);
	}
}

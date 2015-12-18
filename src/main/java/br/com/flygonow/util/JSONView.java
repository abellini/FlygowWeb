package br.com.flygonow.util;

import br.com.flygonow.entities.*;
import br.com.flygonow.enums.*;
import br.com.flygonow.model.AttendantChartAlertByTimeModel;
import br.com.flygonow.model.AttendantChartLastAlertsModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JSONView {

	private final static String HAS_PHOTO_VIDEO = "OK";
	private final static String PROMOTION_OPERATIONAL_AREA = "Promos";
	
	public static String getJsonSuccess(boolean state, String message){
		JSONObject obj = new JSONObject();
		obj.put("success", state);
		if(message != null){
			obj.put("message", message);
		}
		return obj.toString();
	}

	public static String fromCoin(Collection<Coin> coins) {
		JSONArray arr = new JSONArray();
		try{	
			for (Coin coin : coins) {
				JSONObject obj = new JSONObject();
				obj.put("id", coin.getId());
				obj.put("name", coin.getName());
				obj.put("symbol", coin.getSymbol());
				obj.put("conversion", coin.getConversion());
				arr.add(obj);
			}
		} catch (Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}

	public static String fromAdvertisement(Collection<Advertisement> advertisements) {
		JSONArray arr = new JSONArray();
		try{
			DateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
			for (Advertisement adv : advertisements) {
				JSONObject obj = new JSONObject();
				obj.put("id", adv.getId());
				obj.put("name", adv.getName());
				obj.put("inicialDate", fm.format(adv.getInicialDate()));
				obj.put("finalDate", fm.format(adv.getFinalDate()));
				obj.put("active", adv.isActive());
				obj.put("photo", adv.getPhotoName() != null ? HAS_PHOTO_VIDEO  : "");
				obj.put("video", adv.getVideoName() != null ? HAS_PHOTO_VIDEO  : "");
				arr.add(obj);
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}
	
	public static String fromCategory(Collection<Category> categories){
		JSONArray arr = new JSONArray();
		try{	
			for (Category cat : categories) {
				JSONObject obj = new JSONObject();
				obj.put("id", cat.getId());
				obj.put("name", cat.getName());
				obj.put("description", cat.getDescription());
				obj.put("photo", cat.getPhotoName() != null ? HAS_PHOTO_VIDEO  : "");
				arr.add(obj);
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}

	public static String fromPaymentForm(Collection<PaymentForm> pms) {
		JSONArray arr = new JSONArray();
		try{
			for (PaymentForm pay : pms) {
				JSONObject obj = new JSONObject();
				obj.put("id", pay.getId());
				obj.put("name", pay.getName());
				obj.put("description", pay.getDescription());
				arr.add(obj);
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}

	public static String fromAttendantAlert(Collection<AttendantAlert> alerts) {
		JSONArray arr = new JSONArray();

			for (AttendantAlert alert : alerts) {
				try{
					JSONObject obj = new JSONObject();
					obj.put("id", alert.getId());
					obj.put("tabletNumber", alert.getTabletNumber());
					obj.put("alertHour", alert.getAlertHour().getTime());
					obj.put("attendantId", alert.getAttendant().getId());
					obj.put("attendantName", alert.getAttendant().getName());
					obj.put("type", AlertMessageTypeEnum.fromId(alert.getType().getId()));
					obj.put("alertStatus", AlertMessageStatusEnum.fromId(alert.getStatus().getId()));
					arr.add(obj);
				}catch(Exception e){
					System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
				}
			}
		return arr.toString();
	}

	public static String fromOperationalArea(Collection<OperationalArea> ops) {
		JSONArray arr = new JSONArray();
		try{	
			for (OperationalArea op : ops) {
				JSONObject obj = new JSONObject();
				obj.put("id", op.getId());
				obj.put("name", op.getName());
				obj.put("description", op.getDescription());
				obj.put("stringRoles", fromRole(op.getRoles()));
				arr.add(obj);
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}	
		return arr.toString();
	}

	public static String fromAccompaniment(Collection<Accompaniment> accs) {
		JSONArray arr = new JSONArray();
		try{
			for (Accompaniment ac : accs) {
				JSONObject obj = new JSONObject();
				obj.put("id", ac.getId());
				obj.put("name", ac.getName());
				obj.put("value", ac.getValue());
				obj.put("photo", ac.getPhotoName() != null ? HAS_PHOTO_VIDEO  : "");
				obj.put("video", ac.getVideoName() != null ? HAS_PHOTO_VIDEO  : "");
				obj.put("description", ac.getDescription());
				obj.put("active", ac.isActive());
				
				Collection<Category> cats = new ArrayList<Category>();
				cats.add(ac.getCategory());
				obj.put("category", ac.getCategory() != null ? fromCategory(cats) : new JSONArray());
				
				Collection<OperationalArea> ops = new ArrayList<OperationalArea>();
				ops.add(ac.getOperationalArea());
				obj.put("operationalArea", ac.getOperationalArea() != null ? fromOperationalArea(ops) : new JSONArray());
				
				arr.add(obj);
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}
	
	public static String fromFood(Collection<Food> foods) {
		JSONArray arr = new JSONArray();
		try{
			for (Food food : foods) {
				JSONObject obj = new JSONObject();
				obj.put("id", food.getId());
				obj.put("name", food.getName());
				obj.put("value", food.getValue());
				obj.put("photo", food.getPhotoName() != null ? HAS_PHOTO_VIDEO  : "");
				obj.put("video", food.getVideoName() != null ? HAS_PHOTO_VIDEO  : "");
				obj.put("description", food.getDescription());
				obj.put("nutritionalInfo", food.getNutritionalInfo());
				obj.put("maxQtdAccompaniments", food.getMaxQtdAccompaniments() != null ? food.getMaxQtdAccompaniments() : "");
				obj.put("active", food.isActive());
				
				
				obj.put("accompaniments", fromAccompaniment(food.getAccompaniments()));
				
				Collection<Category> cats = new ArrayList<Category>();
				cats.add(food.getCategory());
				obj.put("category", fromCategory(cats));
				
				Collection<OperationalArea> ops = new ArrayList<OperationalArea>();
				ops.add(food.getOperationalArea());
				obj.put("operationalArea", fromOperationalArea(ops));
				
				arr.add(obj);
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}

	public static String fromPromotion(Collection<Promotion> promotions) {
		JSONArray arr = new JSONArray();
		try{	
			DateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
			for (Promotion promo : promotions) {
				JSONObject obj = new JSONObject();
				obj.put("id", promo.getId());
				obj.put("name", promo.getName());
				obj.put("value", promo.getValue());
				obj.put("inicialDate", fm.format(promo.getInicialDate()));
				obj.put("finalDate", fm.format(promo.getFinalDate()));
				obj.put("photo", promo.getPhotoName() != null ? HAS_PHOTO_VIDEO  : "");
				obj.put("video", promo.getVideoName() != null ? HAS_PHOTO_VIDEO  : "");
				obj.put("description", promo.getDescription());
				obj.put("active", promo.isActive());
				
				
				obj.put("items", fromFood(promo.getFoods()));
				
				Collection<Category> cats = new ArrayList<Category>();
				cats.add(promo.getCategory());
				obj.put("category", fromCategory(cats));
				
				arr.add(obj);
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}
	
	public static String fromAttendant(Collection<Attendant> attendants) {
		JSONArray arr = new JSONArray();
		DateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
		try{	
			for (Attendant attendant : attendants) {
				if(!UserTypeEnum.ADMIN.getId().equals(attendant.getId())){
					JSONObject obj = new JSONObject();
					obj.put("id", attendant.getId());
					obj.put("name", attendant.getName());
					obj.put("lastName", attendant.getLastName());
					obj.put("address", attendant.getAddress());
					obj.put("birthDate", attendant.getBirthDate() != null ? fm.format(attendant.getBirthDate()) : null);
					obj.put("photo", attendant.getPhotoName() != null ? HAS_PHOTO_VIDEO  : "");
					obj.put("login", attendant.getLogin());
					obj.put("password", attendant.getPassword());
					obj.put("email", attendant.getEmail());
					obj.put("stringRoles", fromRole(attendant.getRoles()));
					arr.add(obj);
				}
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}

	public static String fromRole(Collection<Role> roles) {
		JSONArray arr = new JSONArray();
		try{
			for (Role role : roles) {
				if(!RoleTypeEnum.AUTHENTICATED.getId().equals(role.getId()) && 
						!RoleTypeEnum.ADMIN.getId().equals(role.getId())){
					JSONObject obj = new JSONObject();
					obj.put("id", role.getId());
					obj.put("name", role.getName());
					obj.put("description", role.getDescription());
					obj.put("modules", fromModule(role.getModules()));
					arr.add(obj);
				}
			}			
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}
	
	public static String fromTablet(Collection<Tablet> tablets) {
		JSONArray arr = new JSONArray();
		try{
			for (Tablet tablet : tablets) {
				JSONObject obj = new JSONObject();
				obj.put("id", tablet.getId());
				obj.put("number", tablet.getNumber());
				Coin coin = tablet.getCoin();
				Collection<Coin> coins = new ArrayList<Coin>();
				coins.add(coin);
				obj.put("coinId", fromCoin(coins));
				obj.put("ip", tablet.getIp());
				obj.put("port", tablet.getPort());
				obj.put("serverIp", tablet.getServerIP());
				obj.put("serverPort", tablet.getServerPort());
				Attendant attendant = tablet.getAttendant();
				Collection<Attendant> attendants = new ArrayList<Attendant>();
				attendants.add(attendant);
				obj.put("attendantId", fromAttendant(attendants));
				obj.put("advertisementId", fromAdvertisement(tablet.getAdvertisements()));
				arr.add(obj);
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}	
		return arr.toString();
		
	}

	public static String fromModule(Collection<Module> modules) {
		JSONArray arr = new JSONArray();
		try{
			for (Module module : modules) {
				JSONObject obj = new JSONObject();
				obj.put("id", module.getId());
				obj.put("name", module.getName());
				obj.put("active", module.getActive());
				arr.add(obj);
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}

	public static String getPhotoInfo(boolean success, int width, int height) {
		JSONObject obj = new JSONObject();
		obj.put("success", success);
		obj.put("imageWidth", width);
		obj.put("imageHeight", height);
		return obj.toString();
	}

	public static String fromOrderItems(List<OrderItem> orderItems) {
		JSONArray arr = new JSONArray();
		DateFormat fm = new SimpleDateFormat("dd/MM/yyyy");
		try{
			for (OrderItem orderItem : orderItems) {
				JSONObject obj = new JSONObject();
				obj.put("id", orderItem.getId());
				obj.put("observations", orderItem.getObservations());
				obj.put("quantity", orderItem.getQuantity());
				obj.put("value", orderItem.getValue());
				obj.put("food", orderItem.getFood() != null ? orderItem.getFood().getName() : orderItem.getPromotion() != null ? orderItem.getPromotion().getName() : "");
				obj.put("operationalArea", orderItem.getFood() != null ? orderItem.getFood().getOperationalArea().getName() : orderItem.getPromotion() != null ? PROMOTION_OPERATIONAL_AREA : "");
				obj.put("orderid", orderItem.getOrder().getId());
				obj.put("status", orderItem.getOrderItemStatus().getName());
				obj.put("statusId", orderItem.getOrderItemStatus().getId());
				obj.put("iniorderdate", fm.format(orderItem.getIniOrderDate()));
				obj.put("tabletNumber", orderItem.getOrder().getTablet().getNumber());
				Collection<Accompaniment> accompaniments = orderItem.getAccompaniments();
				if(orderItem.getFood() != null){
					obj.put("accompaniments", fromAccompaniment(accompaniments));
				}else if(orderItem.getPromotion() != null){
					String promotionItems = "";
					int i = 0;
					for(Food food : orderItem.getPromotion().getFoods()){
						if(i != orderItem.getPromotion().getFoods().size() - 1){
							promotionItems+= food.getName() + ", ";
						}else{
							promotionItems+= food.getName();
						}
						i++;
					}
					obj.put("accompaniments", promotionItems);
				}
				arr.add(obj);
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}

	public static String fromAttendantChartModel(List<AttendantChartLastAlertsModel> attendantChartLastAlertsModels) {
		JSONArray arr = new JSONArray();
		try{
			for (AttendantChartLastAlertsModel attendantChartLastAlertsModel : attendantChartLastAlertsModels) {
				JSONObject obj = new JSONObject();
				obj.put("alertType", attendantChartLastAlertsModel.getAlertType());
				obj.put("value", attendantChartLastAlertsModel.getValue());
				obj.put("color", attendantChartLastAlertsModel.getColor());
				arr.add(obj);
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}

	public static String fromAttendantChartAlertByTimeModel(List<AttendantChartAlertByTimeModel> AttendantChartAlertByTimeModels) {
		JSONArray arr = new JSONArray();
		try{
			for (AttendantChartAlertByTimeModel attendantChartLastAlertsModel : AttendantChartAlertByTimeModels) {
				JSONObject obj = new JSONObject();
				obj.put("time", attendantChartLastAlertsModel.getTime());
				obj.put("value", attendantChartLastAlertsModel.getValue());
				arr.add(obj);
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}

	public static String fromOrderItemStatus(OrderItemStatusEnum[] values) {
		JSONArray arr = new JSONArray();
		try{
			for (OrderItemStatusEnum orderItemStatus : values) {
				JSONObject obj = new JSONObject();
				obj.put("id", orderItemStatus.getId());
				obj.put("name", orderItemStatus.getName());
				arr.add(obj);
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}
}

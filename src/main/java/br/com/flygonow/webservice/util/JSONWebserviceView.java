package br.com.flygonow.webservice.util;

import br.com.flygonow.core.security.Base64Coder;
import br.com.flygonow.entities.*;
import br.com.flygonow.enums.UserTypeEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;

public class JSONWebserviceView {

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
			DateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
			for (Advertisement adv : advertisements) {
				JSONObject obj = new JSONObject();
				obj.put("id", adv.getId());
				obj.put("name", adv.getName());
				obj.put("inicialDate", fm.format(adv.getInicialDate()));
				obj.put("finalDate", fm.format(adv.getFinalDate()));
				obj.put("photoName", adv.getPhotoName());
				obj.put("videoName", adv.getVideoName());
				obj.put("active", adv.isActive());
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
				String photo = null;
				try{
					for(CategoryMedia media : cat.getCategoryMedia()){
						photo = Base64Coder.encodeLines(media.getMedia()); 
					}
					obj.put("photo", photo);
				}catch(Exception e){
					obj.put("photo", null);
					System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
				}
				arr.add(obj);
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}
	
	
	public static String fromPromotions(Collection<Promotion> promotions){
		JSONArray arr = new JSONArray();
		try{	
			for (Promotion promo : promotions) {
				JSONObject obj = new JSONObject();
				obj.put("id", promo.getId());
				obj.put("name", promo.getName());
				obj.put("description", promo.getDescription());
				obj.put("value", promo.getValue());
				obj.put("photoName", "");
				obj.put("videoName", "");
				if (promo.getPhotoName() != null) {
					obj.put("photoName", promo.getPhotoName());
				}
				if (promo.getVideoName() != null) {
					obj.put("videoName", promo.getVideoName());
				}
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

	public static String fromFoodAccompaniments(Collection<Accompaniment> accs) {
		JSONArray arr = new JSONArray();
		try{
			for (Accompaniment ac : accs) {
				JSONObject obj = new JSONObject();
				obj.put("id", ac.getId());
				obj.put("name", ac.getName());
				obj.put("value", ac.getValue());
				obj.put("description", ac.getDescription());
				obj.put("active", ac.isActive());
				obj.put("categoryId", ac.getCategory().getId());
				JSONArray foodIds = new JSONArray();
				for(Food food : ac.getFoods()){
					foodIds.add(food.getId());
				}
				obj.put("foodIds", foodIds);
				arr.add(obj);
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}
	
	public static String fromCategoryAccompaniments(Collection<Accompaniment> accs) {
		JSONArray arr = new JSONArray();
		try{
			for (Accompaniment ac : accs) {
				JSONObject obj = new JSONObject();
				obj.put("id", ac.getId());
				obj.put("name", ac.getName());
				obj.put("value", ac.getValue());
				obj.put("description", ac.getDescription());
				obj.put("active", ac.isActive());
				obj.put("categoryId", ac.getCategory().getId());
				
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
				obj.put("description", food.getDescription());
				obj.put("nutritionalInfo", food.getNutritionalInfo());
				obj.put("maxQtdAccompaniments", food.getMaxQtdAccompaniments() != null ? food.getMaxQtdAccompaniments() : 0);
				obj.put("active", food.isActive());
				obj.put("categoryId", food.getCategory().getId());
				obj.put("photoName", "");
				obj.put("videoName", "");
				if (food.getPhotoName() != null) {
					obj.put("photoName", food.getPhotoName());
				}
				if (food.getVideoName() != null) {
					obj.put("videoName", food.getVideoName());
				}
				arr.add(obj);
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}
	
	public static String fromAttendant(Collection<Attendant> attendants) {
		JSONArray arr = new JSONArray();
		DateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		try{	
			for (Attendant attendant : attendants) {
				if(!UserTypeEnum.ADMIN.getId().equals(attendant.getId())){
					JSONObject obj = new JSONObject();
					obj.put("id", attendant.getId());
					obj.put("name", attendant.getName());
					obj.put("lastName", attendant.getLastName());
					obj.put("address", attendant.getAddress());
					obj.put("birthDate", attendant.getBirthDate() != null ? fm.format(attendant.getBirthDate()) : null);
					obj.put("login", attendant.getLogin());
					obj.put("password", attendant.getPassword());
					obj.put("email", attendant.getEmail());
					arr.add(obj);
				}
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}

	public static String fromFoodPromotions(Collection<Food> foods) {
		JSONArray arr = new JSONArray();
		try{
			for (Food food : foods) {
				JSONObject obj = new JSONObject();
				obj.put("id", food.getId());
				obj.put("name", food.getName());
				obj.put("value", food.getValue());
				obj.put("description", food.getDescription());
				obj.put("nutritionalInfo", food.getNutritionalInfo());
				obj.put("active", food.isActive());
				obj.put("categoryId", food.getCategory().getId());
				obj.put("photoName", null);
				if (food.getPhotoName() != null && !food.getPhotoName().equals("")) {
					obj.put("photoName", food.getPhotoName());
				}
				JSONArray promoIds = new JSONArray();
				for(Promotion promo : food.getPromotions()){
					promoIds.add(promo.getId());
				}
				obj.put("promotionIds", promoIds);
				arr.add(obj);
			}
		}catch(Exception e){
			System.out.println("Persistent Bag ERROR ->> " + e.getMessage());
		}
		return arr.toString();
	}
}

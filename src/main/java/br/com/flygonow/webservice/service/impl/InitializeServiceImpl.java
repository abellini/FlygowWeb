package br.com.flygonow.webservice.service.impl;

import br.com.flygonow.dao.*;
import br.com.flygonow.entities.*;
import br.com.flygonow.enums.MediaTypeEnum;
import br.com.flygonow.webservice.service.InitializeService;
import br.com.flygonow.webservice.util.JSONWebserviceView;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitializeServiceImpl implements InitializeService{

	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private CategoryMediaDao categoryMediaDao;
	
	@Autowired
	private AccompanimentDao accompanimentDao;
	
	@Autowired
	private FoodDao foodDao;
	
	@Autowired
	private AdvertisementDao advertisementDao;
	
	@Autowired
	private AdvertisementMediaDao advertisementMediaDao;
	
	@Autowired
	private PaymentFormDao paymentFormDao;
	
	@Autowired
	private PromotionDao promotionDao;
	
	@Autowired
	private PromotionMediaDao promotionMediaDao;
	
	@Autowired
	private FoodMediaDao foodMediaDao;
	
	@Override
	public JSONObject getInitialData() {
		List<Category> categories = getCategories();
		List<Accompaniment> foodAccompamiments = getFoodAccompaniments();
		List<Accompaniment> categoryAccompamiments = getCategoryAccompaniments();
		List<Promotion> promotions = getPromotions();
		List<Food> foodPromotions = getFoodPromotions();
		List<Food> foods = getFoods();
		List<PaymentForm> payments = getPaymentForms();
		return formatInitialData(categories, foodAccompamiments, categoryAccompamiments, foods, promotions, foodPromotions, payments);
	}

	private List<Category> getCategories(){
		List<Category> categories = null;
		categories = categoryDao.getAll(null);
		for(Category cat : categories){
			cat.setCategoryMedia(
				categoryMediaDao.listByCategoryId(cat.getId(), MediaTypeEnum.PHOTO.getId())
			);
		}
		return categories;
	}
	
	private List<Accompaniment> getFoodAccompaniments(){
		List<Accompaniment> accompaniments = null;
		accompaniments = accompanimentDao.listByAllFoods();
		return accompaniments;
	}
	
	private List<Food> getFoodPromotions(){
		List<Food> foodPromotions = null;
		foodPromotions = foodDao.findAllByAllPromotions();
		return foodPromotions;
	}
	
	private List<Accompaniment> getCategoryAccompaniments(){
		List<Accompaniment> accompaniments = null;
		accompaniments = accompanimentDao.listByAllCategories();
		return accompaniments;
	}
	
	private List<Food> getFoods(){
		List<Food> foods = null;
		foods = foodDao.getAllActives();
		return foods;
	}
	
	private List<Promotion> getPromotions(){
		List<Promotion> promotions = null;
		promotions = promotionDao.listByActiveState(true);
		return promotions;
	}
	
	private List<PaymentForm> getPaymentForms(){
		List<PaymentForm> paymentForms = null;
		paymentForms = paymentFormDao.getAll(null);
		return paymentForms;
	}
	
	private JSONObject formatInitialData(List<Category> categories,
			List<Accompaniment> foodAccompamiments,
			List<Accompaniment> categoryAccompamiments, List<Food> foods,
			List<Promotion> promotions,
			List<Food> foodPromotions,
			List<PaymentForm> paymentForms) {
		JSONObject jsonInitialData = new JSONObject();
		jsonInitialData.put("category", JSONWebserviceView.fromCategory(categories));
		jsonInitialData.put("foodAccompaniments", JSONWebserviceView.fromFoodAccompaniments(foodAccompamiments));
		jsonInitialData.put("categoryAccompaniments", JSONWebserviceView.fromCategoryAccompaniments(categoryAccompamiments));
		jsonInitialData.put("promotions", JSONWebserviceView.fromPromotions(promotions));
		jsonInitialData.put("foodPromotions", JSONWebserviceView.fromFoodPromotions(foodPromotions));
		jsonInitialData.put("foods", JSONWebserviceView.fromFood(foods));
		jsonInitialData.put("paymentForms", JSONWebserviceView.fromPaymentForm(paymentForms));
		return jsonInitialData;
	}
}

package br.com.flygonow.enums;

import br.com.flygonow.entities.Accompaniment;
import br.com.flygonow.entities.Advertisement;
import br.com.flygonow.entities.Food;
import br.com.flygonow.entities.Promotion;

public enum EntityType {

	FOOD(Food.class.getSimpleName()),
	ACCOMPANIMENT(Accompaniment.class.getSimpleName()),
	ADVERTISEMENT(Advertisement.class.getSimpleName()),
	PROMOTION(Promotion.class.getSimpleName())
	;

	private String name;
	
	EntityType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static boolean isFood(String entityName){
		return FOOD.getName().toUpperCase().equals(entityName.toUpperCase());
	}
	
	public static boolean isAccompaniment(String entityName){
		return ACCOMPANIMENT.getName().toUpperCase().equals(entityName.toUpperCase());
	}
	
	public static boolean isAdvertisement(String entityName){
		return ADVERTISEMENT.getName().toUpperCase().equals(entityName.toUpperCase());
	}
	
	public static boolean isPromotion(String entityName){
		return PROMOTION.getName().toUpperCase().equals(entityName.toUpperCase());
	}
}

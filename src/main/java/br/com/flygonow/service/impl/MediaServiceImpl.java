package br.com.flygonow.service.impl;

import br.com.flygonow.dao.*;
import br.com.flygonow.enums.EntityType;
import br.com.flygonow.service.MediaService;
import br.com.flygonow.util.MediaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MediaServiceImpl implements MediaService {
	
	@Autowired
	private CategoryDao categoryDAO;

	@Autowired
	private FoodDao foodDAO;
	
	@Autowired
	private FoodMediaDao foodMediaDAO;

	@Autowired
	private AdvertisementDao advertisementDAO;
	
	@Autowired
	private AdvertisementMediaDao advertisementMediaDAO;

	@Autowired
	private AccompanimentDao accompanimentDAO;
	
	@Autowired
	private AccompanimentMediaDao accompanimentMediaDAO;

	@Autowired
	private PromotionDao promotionDAO;
	
	@Autowired
	private PromotionMediaDao promotionMediaDAO;
	
	private final static String VIDEO_EXTENSION = ".mp4";
	
	@Override
	public void runAdvertisementVideo(
			String className,
			String rootPath, 
			Long entityId,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
			
		byte[] videoByte = null;
		String videoName = null;
		videoName = advertisementDAO.getVideoNameByAdvertisement(entityId);
		String videoPath = rootPath + File.separatorChar + 
				className + File.separatorChar + videoName + VIDEO_EXTENSION;
		if(!MediaUtils.verifyFileIfExists(videoPath)){
			videoByte = advertisementMediaDAO.getVideo(entityId);
		}else{
			Path path = Paths.get(videoPath);
			videoByte = Files.readAllBytes(path);
			//Executa Conting�ncia caso o arquivo em disco esteja corrompido. Consulta da base novamente
			if(videoByte.length == 0){
				videoByte = advertisementMediaDAO.getVideo(entityId);
			}
		}
		
		MediaUtils.sendVideo(response, videoByte);
	}
	
	@Override
	public void runPromotionVideo(
			String className,
			String rootPath, 
			Long entityId,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		byte[] videoByte = null;
		String videoName = null;
		videoName = promotionDAO.getVideoNameByPromo(entityId);
		String videoPath = rootPath + File.separatorChar + 
				className + File.separatorChar + videoName + VIDEO_EXTENSION;
		if(!MediaUtils.verifyFileIfExists(videoPath)){
			videoByte = promotionMediaDAO.getVideo(entityId);
		}else{
			Path path = Paths.get(videoPath);
			videoByte = Files.readAllBytes(path);
			//Executa Conting�ncia caso o arquivo em disco esteja corrompido. Consulta da base novamente
			if(videoByte.length == 0){
				videoByte = promotionMediaDAO.getVideo(entityId);
			}
		}

		MediaUtils.sendVideo(response, videoByte);
	}
	
	@Override
	public void runAccompanimentVideo(
			String className,
			String rootPath, 
			Long entityId,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		byte[] videoByte = null;
		String videoName = null;
		videoName = accompanimentDAO.getVideoNameByAcc(entityId);
		String videoPath = rootPath + File.separatorChar + 
				className + File.separatorChar + videoName + VIDEO_EXTENSION;
		if(!MediaUtils.verifyFileIfExists(videoPath)){
			videoByte = accompanimentMediaDAO.getVideo(entityId);
		}else{
			Path path = Paths.get(videoPath);
			videoByte = Files.readAllBytes(path);
			//Executa Conting�ncia caso o arquivo em disco esteja corrompido. Consulta da base novamente
			if(videoByte.length == 0){
				videoByte = accompanimentMediaDAO.getVideo(entityId);
			}
		}

		MediaUtils.sendVideo(response, videoByte);
	}
	
	@Override
	public void runFoodVideo(
			String className,
			String rootPath, 
			Long entityId,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
		byte[] videoByte = null;
		String videoName = null;
		videoName = foodDAO.getVideoNameByFood(entityId);
		String videoPath = rootPath + File.separatorChar + 
				className + File.separatorChar + videoName + VIDEO_EXTENSION;
		if(!MediaUtils.verifyFileIfExists(videoPath)){
			videoByte = foodMediaDAO.getVideo(entityId);
		}else{
			Path path = Paths.get(videoPath);
			videoByte = Files.readAllBytes(path);
			//Executa Conting�ncia caso o arquivo em disco esteja corrompido. Consulta da base novamente
			if(videoByte.length == 0){
				videoByte = foodMediaDAO.getVideo(entityId);
			}
		}

		MediaUtils.sendVideo(response, videoByte);
	}
	
	@Override
	public void getProductPhoto(
			String className,
			Long entityId,
			HttpServletResponse response) throws IOException{
		
		byte[] photoByte = null;
		if(EntityType.isFood(className)){
			photoByte = foodMediaDAO.getPhoto(entityId);
		} else if (EntityType.isAccompaniment(className)){
			photoByte = accompanimentMediaDAO.getPhoto(entityId);
		} else if (EntityType.isAdvertisement(className)) {
			photoByte = advertisementMediaDAO.getPhoto(entityId);
		} else if (EntityType.isPromotion(className)) {
			photoByte = promotionMediaDAO.getPhoto(entityId);
		}
		if(photoByte != null && photoByte.length > 0){
			MediaUtils.sendImage(response, photoByte);			
		}
	}
	
	@Override
	public void getProductVideo(
			String className,
			Long entityId,
			HttpServletResponse response) throws IOException{
		
		byte[] videoByte = null;
		if(EntityType.isFood(className)){
			videoByte = foodMediaDAO.getVideo(entityId);
		} else if (EntityType.isAccompaniment(className)){
			videoByte = accompanimentMediaDAO.getVideo(entityId);
		} else if (EntityType.isAdvertisement(className)) {
			videoByte = advertisementMediaDAO.getVideo(entityId);
		} else if (EntityType.isPromotion(className)) {
			videoByte = promotionMediaDAO.getVideo(entityId);
		} 
		if(videoByte != null && videoByte.length > 0){
			MediaUtils.sendVideo(response, videoByte);			
		}
	}
}

package br.com.flygonow.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MediaService {
	
	public void runAdvertisementVideo(
			String className,
			String rootPath, 
			Long chooseAdvertisementId,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException;
	
	public void runPromotionVideo(
			String className,
			String rootPath, 
			Long entityId,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException;
	
	public void runAccompanimentVideo(
			String className,
			String rootPath, 
			Long entityId,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException;
	
	public void runFoodVideo(
			String className,
			String rootPath, 
			Long entityId,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException;
	
	public void getProductPhoto(
			String className,
			Long entityId,
			HttpServletResponse response) throws IOException;

	public void getProductVideo(
			String className, 
			Long entityId,
			HttpServletResponse response) throws IOException;

}

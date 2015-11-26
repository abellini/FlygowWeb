package br.com.flygonow.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class MediaUtils{

	private final static List<String> VALID_IMAGE_FORMATS = 
			Arrays.asList("jpg", "png");
	private final static List<String> VALID_VIDEO_FORMATS = 
			Arrays.asList("mp4");
	private final static Integer MAX_VIDEO_SIZE = 10024;
	private final static Integer MAX_PHOTO_SIZE = 10024;
	
	private final static Double SCALE = 0.3;
	
	public static Double getScale(){
		return SCALE;
	}
	
	public static boolean validateImageFormat(MultipartFile image){
		if(image != null && image.getSize() > 0){	
			String originalName = image.getOriginalFilename();
			int pointIndex = originalName.lastIndexOf('.');
			String extension = originalName.substring(pointIndex+1);
			if(VALID_IMAGE_FORMATS.contains(extension.toLowerCase()))
				return true;
			else
				return false;
		}
		return true;
	}
	
	public static boolean validateVideoFormat(MultipartFile video){
		if(video != null && video.getSize() > 0){	
			String originalName = video.getOriginalFilename();
			int pointIndex = originalName.lastIndexOf('.');
			String extension = originalName.substring(pointIndex+1);
			if(VALID_VIDEO_FORMATS.contains(extension.toLowerCase()))
				return true;
			else
				return false;
		}
		return true;
	}
	
	public static byte[] convertImageWithOtherDimensions(InputStream is) throws IOException{
		BufferedImage read = ImageIO.read(is);
		int actualW = read.getWidth();
		int actualH = read.getHeight();
		int w = (int) (actualW * SCALE);
		int h = (int) (actualH * SCALE);
		Graphics2D g = read.createGraphics();  
	    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	    RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
	    g.drawImage(read, 0, 0, w, h, 0, 0, actualW, actualH, null);  
	    g.dispose();
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ImageIO.write(read, "jpg", 	baos);
	    baos.flush();
	    byte[] imageInByte = baos.toByteArray();
		baos.close();
	    return imageInByte;
	}
	
	public static void sendVideo(HttpServletResponse response,  byte[] videoByte) throws IOException{
		ByteArrayInputStream bais = new ByteArrayInputStream(videoByte);
		response.setContentType("application/octet-stream");
		response.setContentLength((int) videoByte.length);
         
        // obtains response's output stream
        OutputStream outStream = response.getOutputStream();
         
        byte[] buffer = new byte[MAX_VIDEO_SIZE];
        int bytesRead = -1;
         
        while ((bytesRead = bais.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
         
        bais.close();
        outStream.close(); 
	}
	
	public static void sendImage(HttpServletResponse response,  byte[] photoByte) throws IOException{
		ByteArrayInputStream bais = new ByteArrayInputStream(photoByte);
		response.setContentType("image/png");
		response.setContentLength((int) photoByte.length);
         
        // obtains response's output stream
        OutputStream outStream = response.getOutputStream();
         
        byte[] buffer = new byte[MAX_PHOTO_SIZE];
        int bytesRead = -1;
         
        while ((bytesRead = bais.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
         
        bais.close();
        outStream.close(); 
	}
	
	public static boolean verifyFileIfExists(String path){
		boolean response = false;
		Path p = null;
		try {
			p = Paths.get(path);
			response = Files.exists(p, LinkOption.NOFOLLOW_LINKS);
		} 
		catch (Exception e) {
			response = false;
		}
		return response;
	}
}
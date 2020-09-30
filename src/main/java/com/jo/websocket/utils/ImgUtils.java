package com.jo.websocket.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImgUtils {


	public void convertPng2Gif (String dir) {
		File file = new File(dir);
		File[] fs = file.listFiles();
		for(File f : fs){					//遍历File[]数组
			if(!f.isDirectory()) {
//				System.out.println(f.getPath());
				FileInputStream fin = null;
				try {
					fin = new FileInputStream(f);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				BufferedImage image = null;
				try {
					image = ImageIO.read(fin);
					//now the extension becomes .gif
					String sPath = f.getParentFile().getPath() + File.separator + f.getName().split("\\.")[0] + ".gif";
					//saving as GIF..
					ImageIO.write(image, "GIF", new File(sPath));
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Done saving.");
				f.delete();
			}
		}

	}

	public static void main(String[] args) {
		String dir = "C:\\Users\\Jo\\Pictures\\emoji";
		ImgUtils utils = new ImgUtils();
		utils.convertPng2Gif(dir);
//		File file = new File(dir);
//		File[] fs = file.listFiles();
//		for(File f : fs){
//			if (!f.isDirectory()) {
//				System.out.println(f.getName().split("\\.")[0]);
//			}
//		}
	}


}

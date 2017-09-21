package com.glqdlt.myhome.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadFileUtils {

	static final Logger log = LoggerFactory.getLogger(UploadFileUtils.class);

	private final static String separator = File.separator;

	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) {

		return null;
	}

	private static String calcPath(String uploadPath) {
		Calendar cal = Calendar.getInstance();

		String yearPath = separator + cal.get(Calendar.YEAR);
		String monthPath = yearPath + separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String datePath = monthPath + separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

		makeDir(uploadPath, yearPath, monthPath, datePath);

		log.debug(datePath);

		return datePath;
	}

	/**
	 * 
	 * @param uploadPath
	 * @param paths
	 * @return 1 == dir exit; 2 == dir create; 0 == fail;
	 */
	private static Integer makeDir(String uploadPath, String... paths) {

		if (new File(paths[paths.length - 1]).exists()) {
			return 1;
		}

		for (String path : paths) {
			File dirPath = new File(uploadPath + path);

			if (!dirPath.exists()) {
				dirPath.mkdir();

				return 2;
			}

		}

		return 0;

	}

	private static String makeThumbnail(String uploadPath, String path, String fileName) throws IOException {

		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));

		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);

		String thumbnailName = uploadPath + path + separator + "t_" + fileName;

		File thumbFile = new File(thumbnailName);

		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		ImageIO.write(destImg, formatName.toUpperCase(), thumbFile);
	
		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}

}

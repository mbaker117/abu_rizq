package com.mbaker.abumazrouqdashboard.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class FileUtil {

	public static StreamedContent getImageFromPath(String path) throws FileNotFoundException {
		File file = new File(path);
		InputStream stream = new FileInputStream(file.getAbsoluteFile());
		return DefaultStreamedContent.builder().contentType("image/jpeg").stream(() -> stream).build();
	}

}

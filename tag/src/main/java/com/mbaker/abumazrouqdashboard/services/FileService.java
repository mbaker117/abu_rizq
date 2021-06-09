package com.mbaker.abumazrouqdashboard.services;

import java.io.File;
import java.io.FileNotFoundException;

public interface FileService {

	public String UploadFile(String path,String fileName, File file) throws FileNotFoundException;
}

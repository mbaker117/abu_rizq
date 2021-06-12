package com.mbaker.abumazrouqdashboard.services;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.primefaces.model.file.UploadedFile;

public interface FileService {


	public String UploadFile(String path, String fileName, UploadedFile in) throws FileNotFoundException, IOException;
}

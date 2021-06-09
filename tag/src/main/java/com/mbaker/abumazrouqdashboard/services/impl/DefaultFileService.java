package com.mbaker.abumazrouqdashboard.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbaker.abumazrouqdashboard.services.FileService;
import com.mbaker.abumazrouqdashboard.validator.CommonValidator;

@Service
public class DefaultFileService implements FileService{
	
	private final static Logger LOG = LoggerFactory.getLogger(DefaultAdminService.class);
	private final static String SERVICE_NAME = "FileService";
	private final static String LOG_MSG = "[FileService]: {}";
	
	@Autowired
	private CommonValidator commonValidator;
	
	

	@Override
	public String UploadFile(String path, String fileName, File file) throws FileNotFoundException {
		commonValidator.validateEmptyObject(file, "file", SERVICE_NAME);
		commonValidator.validateEmptyString(path, "path", SERVICE_NAME);
		commonValidator.validateEmptyString(fileName, "fileName", SERVICE_NAME);
		  FileOutputStream fout=new FileOutputStream(path+fileName);   
		
		return null;
	}

}

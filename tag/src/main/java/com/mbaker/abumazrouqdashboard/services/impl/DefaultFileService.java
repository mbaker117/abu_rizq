package com.mbaker.abumazrouqdashboard.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletContext;

import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbaker.abumazrouqdashboard.services.FileService;
import com.mbaker.abumazrouqdashboard.validator.CommonValidator;

@Service
public class DefaultFileService implements FileService {

	private final static Logger LOG = LoggerFactory.getLogger(DefaultAdminService.class);
	private final static String SERVICE_NAME = "FileService";
	private final static String LOG_MSG = "[FileService]: {}";

	@Autowired
	private CommonValidator commonValidator;

	@Autowired
	ServletContext context;

	@Override
	public String UploadFile(String path, String fileName, UploadedFile file) throws IOException {
		commonValidator.validateEmptyObject(file, "file", SERVICE_NAME);
		commonValidator.validateEmptyString(path, "path", SERVICE_NAME);
		commonValidator.validateEmptyString(fileName, "fileName", SERVICE_NAME);

		final Path root = Paths.get(path);
		
		Files.copy(file.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

		return fileName;

	}

}

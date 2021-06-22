package com.mbaker.abumazrouqdashboard.morpheus.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;



@Named
@ApplicationScoped
public class ImageView {

	public StreamedContent getImage() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			
			return new DefaultStreamedContent();
		} else {
			
			String filename = context.getExternalContext().getRequestParameterMap().get("imageUrl");
			File file = new File(filename);
			InputStream stream = new FileInputStream(file.getAbsoluteFile());
			return DefaultStreamedContent.builder().contentType("image/jpeg").stream(() -> stream).build();
		}
	}
}

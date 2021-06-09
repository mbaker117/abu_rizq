package com.mbaker.abumazrouqdashboard.validator;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


import org.primefaces.validate.ClientValidator;
import org.springframework.beans.factory.annotation.Autowired;

import com.mbaker.abumazrouqdashboard.beans.model.User;
import com.mbaker.abumazrouqdashboard.services.UserService;



public class UsernameValidator implements Validator,ClientValidator {
	
	@Autowired
	private UserService userService;

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		if(Objects.isNull(value) || !(value instanceof User)) {
            throw new ValidatorException(new FacesMessage("User is invalid!"));
		}
		User user = (User) value;
		Optional<User> byUsername =userService.getByUsername(user.getUsername());

		if (byUsername.isPresent()) {
			 throw new ValidatorException(new FacesMessage("Username is invalid!"));
		}	
}

	@Override
	public Map<String, Object> getMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValidatorId() {
		return "usernameValidator";
	}

}

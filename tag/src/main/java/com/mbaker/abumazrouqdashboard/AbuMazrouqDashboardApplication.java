package com.mbaker.abumazrouqdashboard;

import java.util.Collections;
import java.util.EnumSet;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.servlet.RewriteFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.ServletContextAware;

import com.mbaker.abumazrouqdashboard.morpheus.filter.AuthenticationFilter;
import com.mbaker.abumazrouqdashboard.morpheus.filter.AuthorizationFilter;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class AbuMazrouqDashboardApplication extends SpringBootServletInitializer implements ServletContextAware {

	public static void main(String[] args) {
		SpringApplication.run(AbuMazrouqDashboardApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.mbaker.abumazrouqdashboard.controller")).build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {

		return new ApiInfo("Abu Mazruoq ", " backend api.", null, null, null, "Author : Mohammed Baker ", "  ",
				Collections.emptyList());
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Amman")); // It will set UTC timezone
	}

	@Bean
	public ServletRegistrationBean facesServlet() {
		FacesServlet servlet = new FacesServlet();
		ServletRegistrationBean registration = new ServletRegistrationBean(servlet, "*.xhtml");

		registration.setName("Faces Servlet");
		registration.setLoadOnStartup(1);
		registration.setMultipartConfig(new MultipartConfigElement((String) null));
		return registration;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());

	}

	@Bean
	public FilterRegistrationBean rewriteFilter() {
		FilterRegistrationBean rwFilter = new FilterRegistrationBean(new RewriteFilter());
		rwFilter.setDispatcherTypes(
				EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST, DispatcherType.ASYNC, DispatcherType.ERROR));
		rwFilter.addUrlPatterns("/*");

		return rwFilter;
	}

	@Bean
	public ServletContextInitializer servletContextInitializer() {
		return servletContext -> {

			servletContext.setInitParameter("javax.faces.STATE_SAVING_METHOD", "client");

			servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
			servletContext.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "0");
			servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
			servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", Boolean.TRUE.toString());
			servletContext.setInitParameter("primefaces.FONT_AWESOME", Boolean.TRUE.toString());
			servletContext.setInitParameter("primefaces.UPLOADER", "commons");
			servletContext.setInitParameter("facelets.DEVELOPMENT", "true");
			servletContext.setInitParameter("primefaces.THEME", "morpheus-blue");
			servletContext.setInitParameter("org.apache.myfaces.FLASH_SCOPE_DISABLED", Boolean.TRUE.toString());
			servletContext.setInitParameter("org.apache.myfaces.FACES_FLOW_CLIENT_WINDOW_IDS_IN_SESSION", "1");

		};
	}

	@Bean
	public FilterRegistrationBean<AuthenticationFilter> auttenticationFilter() {
		FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new AuthenticationFilter());
		registrationBean.addUrlPatterns("/userpages/*");
		/*
		 * registrationBean.addUrlPatterns("/users.xhtml");
		 * registrationBean.addUrlPatterns("/main.xhtml");
		 * registrationBean.addUrlPatterns("/categories.xhtml");
		 * registrationBean.addUrlPatterns("/items.xhtml");
		 */

		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<AuthorizationFilter> authorizationFilter() {
		FilterRegistrationBean<AuthorizationFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new AuthorizationFilter());

		registrationBean.addUrlPatterns("/userpages/users.xhtml");
		registrationBean.addUrlPatterns("/userpages/advanceReports.xhtml");
		registrationBean.addUrlPatterns("/userpages/addEditItem.xhtml");
		/*
		 * registrationBean.addUrlPatterns("/users.xhtml");
		 * registrationBean.addUrlPatterns("/main.xhtml");
		 * registrationBean.addUrlPatterns("/categories.xhtml");
		 * registrationBean.addUrlPatterns("/items.xhtml");
		 */

		return registrationBean;
	}

	/*
	 * @Bean public ResourceBundle resourceBundle() { return
	 * ResourceBundle.getBundle(
	 * "com.mbaker.abumazrouqdashboard.morpheus.resource.messages.Messages"); }
	 */

}

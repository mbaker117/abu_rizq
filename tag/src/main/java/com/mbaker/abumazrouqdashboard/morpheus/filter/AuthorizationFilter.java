/*
   Copyright 2009-2021 PrimeTek.

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.mbaker.abumazrouqdashboard.morpheus.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mbaker.abumazrouqdashboard.beans.model.User;
import com.mbaker.abumazrouqdashboard.enums.UserType;

public class AuthorizationFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) req).getSession();
		if (session.getAttribute("user") == null) {
			((HttpServletResponse) resp).sendRedirect("/login.xhtml");
			return;
		}
		User user = (User) session.getAttribute("user");

		if (user.getUserType() != UserType.ADMIN) {
			((HttpServletResponse) resp).sendRedirect("/userpages/main.xhtml");
			return;
		}
		

		chain.doFilter(req, resp);

	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void destroy() {

	}

}

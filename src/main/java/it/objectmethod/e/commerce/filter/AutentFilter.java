package it.objectmethod.e.commerce.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.objectmethod.e.commerce.controller.service.JWTService;

@Component
public class AutentFilter implements Filter {

	@Autowired
	private JWTService jwtSer;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String url = req.getRequestURI();

		if (url.endsWith("login")) {
			chain.doFilter(request, response);
		} else {
			String token = req.getHeader("authentificationToken");
			if (token != null) {
				if (jwtSer.verifyToken(token)) {
					chain.doFilter(request, response);
				} else {
					System.out.println("Token scaduto");
					resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
				}
			} else {
				System.out.println("Token nullo");
				resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
		}
	}
}
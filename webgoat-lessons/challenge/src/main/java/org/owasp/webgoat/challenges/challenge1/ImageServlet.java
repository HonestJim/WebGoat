package org.owasp.webgoat.challenges.challenge1;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;

@WebServlet(name = "ImageServlet", urlPatterns = "/challenge/logo")
public class ImageServlet extends HttpServlet {
	
	private static final long serialVersionUID = 9132775506936676850L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		byte[] in = new ClassPathResource("images/webgoat2.png").getInputStream().readAllBytes();
		
		int pincode = new SecureRandom().nextInt(10000);
		String pincodeStr = String.format("%04d", pincode);
		
		in[81216]=(byte) pincodeStr.charAt(0);
		in[81217]=(byte) pincodeStr.charAt(1);
		in[81218]=(byte) pincodeStr.charAt(2);
		in[81219]=(byte) pincodeStr.charAt(3);
		
	    response.setContentType(MediaType.IMAGE_PNG_VALUE);
	    FileCopyUtils.copy(in, response.getOutputStream());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
package org.owasp.webwolf.jwt;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class JWTController {

    // Server-side secret and header constants
    public static final String SERVER_SECRET = "SuperSecretServerKey123!"; // Should be securely stored
    public static final String SERVER_JWT_HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";

    @GetMapping("/WebWolf/jwt")
    public ModelAndView jwt() {
        return new ModelAndView("jwt");
    }

    @PostMapping(value = "/WebWolf/jwt/decode", consumes = APPLICATION_FORM_URLENCODED_VALUE, produces = APPLICATION_JSON_VALUE)
    public JWTToken decode(@RequestBody MultiValueMap<String, String> formData) {
        var jwt = formData.getFirst("token");
        // Use a server-side secretKey, do not accept from user input
        String secretKey = JWTController.SERVER_SECRET;
        return JWTToken.decode(jwt, secretKey);
    }

    @PostMapping(value = "/WebWolf/jwt/encode", consumes = APPLICATION_FORM_URLENCODED_VALUE, produces = APPLICATION_JSON_VALUE)
    public JWTToken encode(@RequestBody MultiValueMap<String, String> formData) {
        var payload = formData.getFirst("payload");
        // Only allow secure header/algorithm set server-side
        String header = JWTController.SERVER_JWT_HEADER; // e.g., {"alg":"HS256"}
        String secretKey = JWTController.SERVER_SECRET;
        return JWTToken.encode(header, payload, secretKey);
    }

}
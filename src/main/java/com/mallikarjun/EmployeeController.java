package com.mallikarjun;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.InetAddress;
import java.util.Date;

@RestController
public class EmployeeController {

    // సరళమైన @Value అనోటేషన్
    @Value("${APP_WELCOME_MESSAGE:నమస్కారం!}")
    private String welcomeMessage;
	
	// Secret నుండి వచ్చే పాస్‌వర్డ్
    @Value("${DB_PASSWORD:default_pass}")
    private String dbPassword;

    @GetMapping("/")
    public String getStatus() {
        try {
            String hostName = InetAddress.getLocalHost().getHostName();
            // మీరు కోరినట్లుగా సింపుల్ అవుట్‌పుట్
           //old return welcomeMessage + " | Pod: " + hostName + " | Time: " + new Date();
			return welcomeMessage + " | Pod: " + hostName + 
                   " | DB Password: " + dbPassword + // ఇది టెస్టింగ్ కోసం మాత్రమే
                   " | Time: " + new Date();
        } catch (Exception e) {
            return "Error getting hostname";
        }
    }
}
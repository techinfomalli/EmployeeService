package com.mallikarjun;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/api/info")
    public Map<String, Object> getAppDetailedStatus() {
        Map<String, Object> details = new HashMap<>();
        try {
            InetAddress ip = InetAddress.getLocalHost();
            details.put("Status", "Running");
            details.put("Pod_Name", ip.getHostName());
            details.put("Pod_IP", ip.getHostAddress());
            details.put("Java_Version", System.getProperty("java.version"));
            details.put("OS", System.getProperty("os.name"));

            // Memory details (Architect Level Monitoring)
            long totalMemory = Runtime.getRuntime().totalMemory() / (1024 * 1024);
            long freeMemory = Runtime.getRuntime().freeMemory() / (1024 * 1024);
            details.put("Total_Memory_MB", totalMemory);
            details.put("Free_Memory_MB", freeMemory);

            details.put("DB_Password_Configured", (dbPassword != null && !dbPassword.isEmpty()));
            details.put("Timestamp", new Date().toString());
        } catch (Exception e) {
            details.put("Error", "Could not retrieve system details: " + e.getMessage());
        }
        return details;
    }

}
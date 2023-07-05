package com.innogrid.tabcloudit.terraform.api;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Slf4j
@Component
public class RestfulClient {
    public JSONObject JsonApiWithGet(String uri, String option, String token) {
        try {
            URL url = new URL(uri + option);
            if (url.getProtocol().equals("https"))
                ignoreSsl();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            conn.addRequestProperty("Authorization", "Bearer " + token);
            conn.addRequestProperty("Content-Type", "application/vnd.api+json");
            conn.setDoOutput(true);

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                JSONParser parser = new JSONParser();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String inputLine = "";
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return (JSONObject) parser.parse(response.toString());
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public JSONObject JsonApiWithPayload(String uri, String option, String token, JSONObject jsonBody) {
        try {
            URL url = new URL(uri + option);
            if (url.getProtocol().equals("https"))
                ignoreSsl();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.addRequestProperty("Authorization", "Bearer " + token);
            conn.addRequestProperty("Content-Type", "application/vnd.api+json");
            conn.setDoOutput(true);

            String json =  jsonBody.toJSONString();

            try (DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream())) {
                byte[] payload = json.getBytes(StandardCharsets.UTF_8);
                outputStream.write(payload, 0, payload.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                JSONParser parser = new JSONParser();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String inputLine = "";
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return (JSONObject) parser.parse(response.toString());
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private static void ignoreSsl() throws Exception {
        HostnameVerifier verifier = (urlHostName, session) -> true;
        trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(verifier);
    }

    private static void trustAllHttpsCertificates() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager trustManager = new miTM();
        trustAllCerts[0] = trustManager;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    private static class miTM implements TrustManager, X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() { return null; }
        public boolean isServerTrusted(X509Certificate[] certs) { return true; }
        public boolean isClientTrusted(X509Certificate[] certs) { return true; }
        public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {}
        public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {}
    }

}

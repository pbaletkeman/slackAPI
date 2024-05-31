package org.example;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

public class Slack {

    private static final String USER_AGENT = "Mozilla/5.0";

    private static final String GET_URL = "https://slack.com/api/chat.postMessage";

    private static final String POST_URL = "https://slack.com/api/users.lookupByEmail";

    private static final String POST_PARAMS = "email=pete.letkeman@rakuten.com";

    private static final String TOKEN = "Bearer xoxb-7227327005184-7197918145526-JcZAixmi2VCATVwr19PW345p";

    public static void sendGET(String channel, String text) throws IOException {

        String stringBuilder = GET_URL + "?channel=" +
                URLEncoder.encode(channel, StandardCharsets.UTF_8) + "&text=" +
                URLEncoder.encode(text, StandardCharsets.UTF_8);

        URL obj = new URL(stringBuilder);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Authorization",TOKEN);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request did not work.");
        }

    }

    public static String sendPOST() throws IOException {
        final ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        URL obj = new URL(POST_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Authorization",TOKEN);
        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());

            SlackRresponse s = om.readValue(response.toString(), SlackRresponse.class);
            if (s.getUser() == null){
                throw new RuntimeException("\"unable to process request, check token\"");
            } else {
                return s.user.getId();
            }

        } else {
            return "error";
        }
    }

}
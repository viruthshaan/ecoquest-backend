package com.ecoquest.game.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Locale;

public class HttpService {
    public static String SendRequest(String token, int year, String month) {
        String endpointUrl = "http://20.15.114.131:8080/api/power-consumption/month/daily/view";

        try {
            // Encode the month parameter
            String encodedMonth = URLEncoder.encode(month.toUpperCase(Locale.ENGLISH), "UTF-8");

            // Build the URL with parameters
            String fullUrl = endpointUrl + "?year=" + year + "&month=" + encodedMonth;

            URL url = new URL(fullUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method to GET
            connection.setRequestMethod("GET");

            // Set authorization header with the token
            connection.setRequestProperty("Authorization", "Bearer " + token);

            // Get response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                // Close the connection
                connection.disconnect();
                // Return the JSON response
                return response.toString();
            } else {
                System.out.println("Failed to get a successful response. Response code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return null in case of failure
        return null;
    }
}

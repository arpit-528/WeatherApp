package org.example;

import org.apache.hc.client5.http.fluent.Request;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherService {
    private static final String API_KEY = "61673e84db15476885970025251602"; // Replace with your actual API key
    private static final String BASE_URL = "https://api.weatherapi.com/v1/current.json?key=";

    public static String getWeather(String city) {
        try {
            String response = Request.get(BASE_URL + API_KEY + "&q=" + city)
                    .execute().returnContent().asString();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);

            String condition = rootNode.get("current").get("condition").get("text").asText();
            double tempC = rootNode.get("current").get("temp_c").asDouble();
            int humidity = rootNode.get("current").get("humidity").asInt();
            double windSpeed = rootNode.get("current").get("wind_kph").asDouble();
            String windDirection = rootNode.get("current").get("wind_dir").asText();
            String lastUpdated = rootNode.get("current").get("last_updated").asText();

            return "Weather in " + city + ":\n" +
                    "Condition: " + condition + "\n" +
                    "Temperature: " + tempC + "°C\n" +
                    "Humidity: " + humidity + "%\n" +
                    "Wind Speed: " + windSpeed + " km/h\n" +
                    "Wind Direction: " + windDirection + "\n" +
                    "Last Updated: " + lastUpdated;
        } catch (Exception e) {
            return "Error fetching weather data: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        System.out.println(getWeather("London")); // Test with a city
    }
}

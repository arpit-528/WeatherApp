package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.apache.hc.client5.http.fluent.Request;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherAppGUI extends JFrame {
    private JTextField cityField;
    private JLabel resultLabel;

    private static final String API_KEY = "61673e84db15476885970025251602";
    private static final String BASE_URL = "https://api.weatherapi.com/v1/current.json?key=";

    public WeatherAppGUI() {
        setTitle("Weather App");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel cityLabel = new JLabel("Enter City: ");
        cityField = new JTextField(15);
        JButton getWeatherButton = new JButton("Get Weather");
        resultLabel = new JLabel("Weather info will be displayed here.");

        getWeatherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = cityField.getText().trim();
                if (!city.isEmpty()) {
                    String weatherInfo = getWeather(city);
                    resultLabel.setText(weatherInfo);
                } else {
                    resultLabel.setText("Please enter a city name.");
                }
            }
        });

        add(cityLabel);
        add(cityField);
        add(getWeatherButton);
        add(resultLabel);

        setVisible(true);
    }

    public static String getWeather(String city) {
        try {
            String response = Request.get(BASE_URL + API_KEY + "&q=" + city)
                    .execute().returnContent().asString();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);

            String condition = rootNode.get("current").get("condition").get("text").asText();
            double tempC = rootNode.get("current").get("temp_c").asDouble();
            return "Weather in " + city + ": " + condition + ", " + tempC + "°C";
        } catch (Exception e) {
            return "Error fetching weather: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WeatherAppGUI());
    }
}

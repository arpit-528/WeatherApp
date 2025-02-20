import org.example.WeatherService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherApp {
    public static void main(String[] args) {
        // Create JFrame (Window)
        JFrame frame = new JFrame("Weather App");
        frame.setSize(400, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(8, 1));

        // Input field for city
        JLabel cityLabel = new JLabel("Enter City Name:");
        JTextField cityField = new JTextField();

        // Button to fetch weather
        JButton getWeatherButton = new JButton("Get Weather");

        // Labels to display weather info
        JLabel conditionLabel = new JLabel("Condition: ");
        JLabel tempLabel = new JLabel("Temperature: ");
        JLabel feelsLikeLabel = new JLabel("Feels Like: ");
        JLabel humidityLabel = new JLabel("Humidity: ");
        JLabel windLabel = new JLabel("Wind Speed: ");
        JLabel pressureLabel = new JLabel("Pressure: ");

        // Add ActionListener to button
        getWeatherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = cityField.getText();
                if (!city.isEmpty()) {
                    String weatherInfo = WeatherService.getWeather(city);
                    updateWeatherLabels(weatherInfo, conditionLabel, tempLabel, feelsLikeLabel, humidityLabel, windLabel, pressureLabel);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a city name.");
                }
            }
        });

        // Add components to frame
        frame.add(cityLabel);
        frame.add(cityField);
        frame.add(getWeatherButton);
        frame.add(conditionLabel);
        frame.add(tempLabel);
        frame.add(feelsLikeLabel);
        frame.add(humidityLabel);
        frame.add(windLabel);
        frame.add(pressureLabel);

        // Set frame visible
        frame.setVisible(true);
    }

    private static void updateWeatherLabels(String weatherInfo, JLabel conditionLabel, JLabel tempLabel, JLabel feelsLikeLabel, JLabel humidityLabel, JLabel windLabel, JLabel pressureLabel) {
        String[] lines = weatherInfo.split("\n");

        if (lines.length >= 6) {
            conditionLabel.setText(lines[1]);  // Condition
            tempLabel.setText(lines[2]);      // Temperature
            feelsLikeLabel.setText(lines[3]); // Feels Like
            humidityLabel.setText(lines[4]);  // Humidity
            windLabel.setText(lines[5]);      // Wind Speed
            pressureLabel.setText(lines[6]);  // Pressure
        } else {
            JOptionPane.showMessageDialog(null, "Error fetching weather data!");
        }
    }
}

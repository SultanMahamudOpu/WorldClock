package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class TemperatureController {

    @FXML
    private TextField temperatureInput;

    @FXML
    private ComboBox<String> inputScaleComboBox;

    @FXML
    private ComboBox<String> outputScaleComboBox;

    @FXML
    private Button convertButton;

    @FXML
    private Label resultLabel;

    @FXML
    public void initialize() {
        // Initialize ComboBoxes
        inputScaleComboBox.getItems().addAll("Celsius (°C)", "Fahrenheit (°F)", "Kelvin (K)");
        inputScaleComboBox.setValue("Celsius (°C)");
        outputScaleComboBox.getItems().addAll("Celsius (°C)", "Fahrenheit (°F)", "Kelvin (K)");
        outputScaleComboBox.setValue("Fahrenheit (°F)");

        // Handle conversion logic
        convertButton.setOnAction(event -> convertTemperature());
    }

    private void convertTemperature() {
        try {
            double inputTemp = Double.parseDouble(temperatureInput.getText());
            String resultText;

            // Convert from input scale to Celsius first
            double tempInCelsius = switch (inputScaleComboBox.getValue()) {
                case "Celsius (°C)" -> inputTemp;
                case "Fahrenheit (°F)" -> (inputTemp - 32) * 5 / 9;
                case "Kelvin (K)" -> inputTemp - 273.15;
                default -> 0;
            };

            // Convert from Celsius to the selected output scale
            resultText = switch (outputScaleComboBox.getValue()) {
                case "Celsius (°C)" -> String.format("%.2f °C", tempInCelsius);
                case "Fahrenheit (°F)" -> String.format("%.2f °F", (tempInCelsius * 9 / 5) + 32);
                case "Kelvin (K)" -> String.format("%.2f K", tempInCelsius + 273.15);
                default -> "";
            };

            resultLabel.setText("Result: " + resultText);
        } catch (NumberFormatException e) {
            resultLabel.setText("Please enter a valid number.");
            resultLabel.setStyle("-fx-text-fill: red;");
        }
    }
}

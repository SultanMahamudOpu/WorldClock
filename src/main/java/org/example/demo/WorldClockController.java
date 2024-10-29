package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class WorldClockController {

    @FXML
    private ComboBox<String> fromCountryComboBox;

    @FXML
    private ComboBox<String> toCountryComboBox;

    @FXML
    private TextField timeInput;

    @FXML
    private Label resultLabel;

    private final Map<String, String> timeZones;

    public WorldClockController() {
        timeZones = new HashMap<>();
        timeZones.put("USA (New York)", "America/New_York");
        timeZones.put("UK (London)", "Europe/London");
        timeZones.put("India (New Delhi)", "Asia/Kolkata");
        timeZones.put("Japan (Tokyo)", "Asia/Tokyo");
        timeZones.put("Australia (Sydney)", "Australia/Sydney");
        timeZones.put("Brazil (Sao Paulo)", "America/Sao_Paulo");
        timeZones.put("China (Beijing)", "Asia/Shanghai");
        timeZones.put("Russia (Moscow)", "Europe/Moscow");
        timeZones.put("South Africa (Johannesburg)", "Africa/Johannesburg");
        timeZones.put("Germany (Berlin)", "Europe/Berlin");
        timeZones.put("Canada (Toronto)", "America/Toronto");
        timeZones.put("France (Paris)", "Europe/Paris");
        timeZones.put("Italy (Rome)", "Europe/Rome");
        timeZones.put("Spain (Madrid)", "Europe/Madrid");
        timeZones.put("Mexico (Mexico City)", "America/Mexico_City");
        timeZones.put("Argentina (Buenos Aires)", "America/Argentina/Buenos_Aires");
        timeZones.put("South Korea (Seoul)", "Asia/Seoul");
        timeZones.put("Bangladesh (Dhaka)", "Asia/Dhaka");
        timeZones.put("Egypt (Cairo)", "Africa/Cairo");
        timeZones.put("Turkey (Istanbul)", "Europe/Istanbul");
    }

    @FXML
    public void initialize() {
        fromCountryComboBox.getItems().addAll(timeZones.keySet());
        fromCountryComboBox.setValue("Bangladesh (Dhaka)");

        toCountryComboBox.getItems().addAll(timeZones.keySet());
        toCountryComboBox.setValue("India (New Delhi)");
    }

    @FXML
    public void handleConvert(ActionEvent event) {
        String fromZone = timeZones.get(fromCountryComboBox.getValue());
        String toZone = timeZones.get(toCountryComboBox.getValue());

        try {
            LocalTime inputTime = LocalTime.parse(timeInput.getText());
            ZonedDateTime fromDateTime = ZonedDateTime.of(inputTime.atDate(java.time.LocalDate.now()), ZoneId.of(fromZone));
            ZonedDateTime toDateTime = fromDateTime.withZoneSameInstant(ZoneId.of(toZone));
            resultLabel.setText("Time in " + toCountryComboBox.getValue() + ": " + toDateTime.toLocalTime().toString());
        } catch (Exception ex) {
            resultLabel.setText("Invalid input time.");
        }
    }
}

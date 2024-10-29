package org.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class WorldClock extends Application {

    private final Map<String, String> timeZones;

    public WorldClock() {
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

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("World Clock");

        // UI Elements
        ComboBox<String> fromCountry = new ComboBox<>();
        fromCountry.getItems().addAll(timeZones.keySet());
        fromCountry.setValue("USA (New York)");
        setFontSize(fromCountry, 20);

        ComboBox<String> toCountry = new ComboBox<>();
        toCountry.getItems().addAll(timeZones.keySet());
        toCountry.setValue("UK (London)");
        setFontSize(toCountry, 20);

        TextField timeInput = new TextField();
        timeInput.setPromptText("HH:MM");
        setFontSize(timeInput, 20);

        Label resultLabel = new Label();
        setFontSize(resultLabel, 20);

        Button convertButton = new Button("Convert Time");
        convertButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        setFontSize(convertButton, 20);

        convertButton.setOnAction(e -> {
            String fromZone = timeZones.get(fromCountry.getValue());
            String toZone = timeZones.get(toCountry.getValue());

            try {
                LocalTime inputTime = LocalTime.parse(timeInput.getText());
                ZonedDateTime fromDateTime = ZonedDateTime.of(inputTime.atDate(java.time.LocalDate.now()), ZoneId.of(fromZone));
                ZonedDateTime toDateTime = fromDateTime.withZoneSameInstant(ZoneId.of(toZone));
                resultLabel.setText("Time in " + toCountry.getValue() + ": " + toDateTime.toLocalTime().toString());
            } catch (Exception ex) {
                resultLabel.setText("Invalid input time.");
            }
        });

        // Layout
        VBox vBox = new VBox(20);
        vBox.setPadding(new Insets(100));
        vBox.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: #A569BD; -fx-border-color: #ecf0f1; -fx-border-width: 2px; -fx-border-radius: 5px;");
        vBox.getChildren().addAll(
                createStyledLabel("From Country: "),
                fromCountry,
                createStyledLabel("Input Time (HH:MM): "),
                timeInput,
                createStyledLabel("To Country: "),
                toCountry,
                convertButton,
                resultLabel
        );

        Scene scene = new Scene(vBox, 600, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: #17202A;");
        setFontSize(label, 20);
        return label;
    }

    private void setFontSize(Control control, double fontSize) {
        control.setStyle("-fx-font-size: " + fontSize + "px;");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
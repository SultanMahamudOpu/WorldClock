package org.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalTime;

public class AnalogClock extends Application {

    @Override
    public void start(Stage primaryStage) {
        ClockPane clock = new ClockPane();
        Scene scene = new Scene(clock, 400, 400);
        primaryStage.setTitle("Analog Clock");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Create a timeline to update the clock every second
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> clock.update()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class ClockPane extends Pane {
    private Circle clockFace;
    private Line hourHand, minuteHand, secondHand;
    private double centerX, centerY, radius;

    public ClockPane() {
        setClockSize(200); // Set clock size (radius)
        drawClock();
        update(); // Initialize clock hands
    }

    private void setClockSize(double size) {
        this.radius = size;
        this.centerX = size;
        this.centerY = size;
    }

    private void drawClock() {
        // Add dynamic gradient background for the clock face
        Stop[] stops = new Stop[] {
                new Stop(0, Color.web("#1e1e1e")),
                new Stop(1, Color.web("#34495e"))
        };
        RadialGradient gradient = new RadialGradient(0, 0, centerX, centerY, radius, false, null, stops);

        // Draw the clock face with a gradient background and a border
        clockFace = new Circle(centerX, centerY, radius, gradient);
        clockFace.setStroke(Color.web("#ecf0f1"));
        clockFace.setStrokeWidth(3);

        // Add a drop shadow to the clock face
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(5);
        shadow.setOffsetY(5);
        shadow.setColor(Color.GRAY);
        clockFace.setEffect(shadow);

        // Draw the hands with shadows for a 3D effect
        hourHand = createHand(Color.web("#ecf0f1"), 6, radius * 0.5);
        minuteHand = createHand(Color.web("#95a5a6"), 4, radius * 0.75);
        secondHand = createHand(Color.RED, 2, radius * 0.85);

        getChildren().addAll(clockFace, hourHand, minuteHand, secondHand);

        // Add a small circle at the center of the clock
        Circle centerCircle = new Circle(centerX, centerY, 5, Color.web("#ecf0f1"));
        getChildren().add(centerCircle);

        // Add hour numbers (1-12) with shadow effect
        for (int i = 1; i <= 12; i++) {
            double angle = Math.toRadians(i * 30); // 30 degrees between each hour mark
            double x = centerX + (radius - 40) * Math.sin(angle);
            double y = centerY - (radius - 40) * Math.cos(angle);

            Text number = new Text(x - 10, y + 10, String.valueOf(i));
            number.setFill(Color.web("#ecf0f1"));
            number.setFont(Font.font(20));

            // Add drop shadow to the hour numbers
            DropShadow numberShadow = new DropShadow();
            numberShadow.setOffsetX(2);
            numberShadow.setOffsetY(2);
            numberShadow.setColor(Color.GRAY);
            number.setEffect(numberShadow);

            getChildren().add(number);
        }

        // Add minute marks (5-minute intervals) with glow effect
        for (int i = 0; i < 60; i++) {
            double angle = Math.toRadians(i * 6); // 6 degrees per minute mark
            double innerX = centerX + (radius - 15) * Math.sin(angle);
            double innerY = centerY - (radius - 15) * Math.cos(angle);
            double outerX = centerX + radius * Math.sin(angle);
            double outerY = centerY - radius * Math.cos(angle);

            Line tick = new Line(innerX, innerY, outerX, outerY);
            tick.setStroke(i % 5 == 0 ? Color.web("#ecf0f1") : Color.web("#7f8c8d"));
            tick.setStrokeWidth(i % 5 == 0 ? 3 : 1);

            // Apply a glow effect to the ticks
            Glow glow = new Glow();
            tick.setEffect(glow);

            getChildren().add(tick);
        }
    }

    // Method to create clock hands with a shadow effect
    private Line createHand(Color color, double width, double length) {
        Line hand = new Line(centerX, centerY, centerX, centerY - length);
        hand.setStroke(color);
        hand.setStrokeWidth(width);

        DropShadow handShadow = new DropShadow();
        handShadow.setOffsetX(2);
        handShadow.setOffsetY(2);
        handShadow.setColor(Color.GRAY);
        hand.setEffect(handShadow);

        return hand;
    }

    public void update() {
        LocalTime currentTime = LocalTime.now();

        // Calculate the angles for each hand
        double secondAngle = currentTime.getSecond() * 6; // 360 / 60 = 6 degrees per second
        double minuteAngle = (currentTime.getMinute() + currentTime.getSecond() / 60.0) * 6; // 6 degrees per minute
        double hourAngle = (currentTime.getHour() % 12 + currentTime.getMinute() / 60.0) * 30; // 30 degrees per hour

        // Position the hour hand
        hourHand.setEndX(centerX + (radius * 0.5) * Math.sin(Math.toRadians(hourAngle)));
        hourHand.setEndY(centerY - (radius * 0.5) * Math.cos(Math.toRadians(hourAngle)));

        // Position the minute hand
        minuteHand.setEndX(centerX + (radius * 0.75) * Math.sin(Math.toRadians(minuteAngle)));
        minuteHand.setEndY(centerY - (radius * 0.75) * Math.cos(Math.toRadians(minuteAngle)));

        // Position the second hand
        secondHand.setEndX(centerX + (radius * 0.85) * Math.sin(Math.toRadians(secondAngle)));
        secondHand.setEndY(centerY - (radius * 0.85) * Math.cos(Math.toRadians(secondAngle)));
    }
}

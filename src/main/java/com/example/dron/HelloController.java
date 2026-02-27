package com.example.dron;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class HelloController {
    @FXML private Label lblHlinik, lblPlast, lblCipy;
    @FXML private Label lblRam, lblVrtule, lblDeska;
    @FXML private Label lblKity;
    @FXML private Button btnPause;

    private boolean paused = false;
    private Sklad sklad;
    private Vyrobce v1, v2, v3;
    private Sestavitel s1, s2;
    private Timeline timeline;

    @FXML
    public void start() {

        System.out.println("[SYSTEM] START");

        sklad = new Sklad();

        v1 = new Vyrobce(sklad, "RAM");
        v2 = new Vyrobce(sklad, "VRTULE");
        v3 = new Vyrobce(sklad, "DESKA");

        s1 = new Sestavitel(sklad, 1);
        s2 = new Sestavitel(sklad, 2);

        v1.start(); v2.start(); v3.start();
        s1.start(); s2.start();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> aktualizujGUI()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void stop() {
        System.out.println("[USER] STOP");
        System.out.println("[SYSTEM] STOP");

        v1.stopRunning();
        v2.stopRunning();
        v3.stopRunning();
        s1.stopRunning();
        s2.stopRunning();

        timeline.stop();
    }

    private void aktualizujGUI() {
            Platform.runLater(() -> {
            lblHlinik.setText("Hliník: " + sklad.getHlinik());
            lblPlast.setText("Plast: " + sklad.getPlast());
            lblCipy.setText("Čipy: " + sklad.getCipy());

            lblRam.setText("Rám: " + sklad.getRam());
            lblVrtule.setText("Vrtule: " + sklad.getVrtule());
            lblDeska.setText("Deska: " + sklad.getDeska());

            lblKity.setText(String.valueOf(sklad.getHotoveKity()));
        });
    }

    public void pauseResume(ActionEvent actionEvent) {
        paused = !paused;
        sklad.setPaused(paused);

        if (paused) {
            System.out.println("[SYSTEM] PAUSE");
            btnPause.setText("Resume");
        } else {
            System.out.println("[SYSTEM] RESUME");
            btnPause.setText("Pause");
        }
    }
}

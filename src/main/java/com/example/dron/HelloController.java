package com.example.dron;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class HelloController {
    @FXML private TextField Id_spinner;
    @FXML private Label lblHlinik, lblPlast, lblCipy;
    @FXML private Label lblRam, lblVrtule, lblDeska;
    @FXML private Label lblKity;
    @FXML private Button btnPause;

    private boolean paused = false;
    private Sklad sklad;
    private Vyrobce v1, v2, v3,v4;
    private Sestavitel s1, s2;
    private Timeline timeline;



    @FXML
    public void start() {
        String maximum= Id_spinner.getText();
        if (!maximum.isEmpty()) {
            sklad = new Sklad();
            v1 = new Vyrobce(sklad, "RAM", Integer.parseInt(maximum));
            v2 = new Vyrobce(sklad, "VRTULE",Integer.parseInt(maximum));
            v3 = new Vyrobce(sklad, "DESKA",Integer.parseInt(maximum));
            v4= new Vyrobce(sklad,"Skladnik",Integer.parseInt(maximum));

            s1 = new Sestavitel(sklad, 1,Integer.parseInt(maximum));
            s2 = new Sestavitel(sklad, 2,Integer.parseInt(maximum));


            v1.start();
            v2.start();
            v3.start();
            v4.start();
            s1.start();
            s2.start();

            timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> aktualizujGUI()));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
        else {
            System.out.println("Nezadali jste číslo");
        }
    }

    @FXML
    public void stop() {
        String maximum= Id_spinner.getText();
        if (!maximum.isEmpty()) {
            System.out.println("[USER] STOP");
            System.out.println("[SYSTEM] STOP");

            v1.stopRunning();
            v2.stopRunning();
            v3.stopRunning();
            v4.stopRunning();
            s1.stopRunning();
            s2.stopRunning();

            timeline.stop();

        }
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
        String maximum= Id_spinner.getText();
        if (!maximum.isEmpty()) {

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
}

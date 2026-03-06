package com.example.dron;

import java.util.Objects;

public class Vyrobce extends Thread {

    private final Sklad sklad;
    private final String typ;
    private volatile boolean running = true;
    private int vyrobeno = 0;
    private int maxvyrobeno = 0;

    public Vyrobce(Sklad sklad, String typ, int maxvyrobeno) {
        super("VYROBCE-" + typ);
        this.sklad = sklad;
        this.typ = typ;
        this.maxvyrobeno = maxvyrobeno;
    }

    public void stopRunning() {
        running = false;
    }

    public int getVyrobeno() {
        return vyrobeno;
    }

    @Override
    public void run() {

        while (running) {
            if (sklad.isPaused()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                continue;
            }

            switch (typ) {
                case "RAM":
                    if (sklad.getRam() > 30) {
                        Log.println("[MANAGER] pozastavil výrobu: RAM");
                        sleep1s();
                        continue;
                    }
                    break;

                case "VRTULE":
                    if (sklad.getVrtule() > 30) {
                        Log.println("[MANAGER] pozastavil výrobu: VRTULE");
                        sleep1s();
                        continue;
                    }
                    break;

                case "DESKA":
                    if (sklad.getDeska() > 30) {
                        Log.println("[MANAGER] pozastavil výrobu: DESKA");
                        sleep1s();
                        continue;
                    }
                    break;
            }

            boolean uspech = false;

            switch (typ) {

                case "RAM":
                    if (sklad.getHotoveKity() >= maxvyrobeno) {
                        System.out.println("[System] Stop");
                        stopRunning();
                    }
                    uspech = sklad.vyrobRam();
                    break;
                case "VRTULE":
                    if (sklad.getHotoveKity() >= maxvyrobeno) {
                        System.out.println("[System] Stop");
                        stopRunning();
                    }
                    uspech = sklad.vyrobVrtule();
                    break;
                case "DESKA":
                    if (sklad.getHotoveKity() >= maxvyrobeno) {
                        System.out.println("[System] Stop");
                        stopRunning();
                        sleep1s();
                    }
                    uspech = sklad.vyrobDesku();
                    break;
                case "Skladnik":
                    if (sklad.getHotoveKity() >= maxvyrobeno) {
                        System.out.println("[System] Stop");
                        stopRunning();
                    }
                    uspech= sklad.Skladnik();
                    break;
            }

            if (uspech) {
                vyrobeno++;
                Log.println("[" + getName() + "] vyrobil KOMPONENTU: "
                        + typ + " (celkem = " + vyrobeno + ")");
            } else {
                Log.println("[" + getName()
                        + "] čeká na materiál pro: " + typ);
            }

            sleep1s();
        }
        sleep1s();
        if (!Objects.equals(getName(), "Skladnik")) {
           Log.println("["+getName()+"] Vytvořil "+getVyrobeno()+" "+typ);
        }
    }

    private void sleep1s() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
    }
}
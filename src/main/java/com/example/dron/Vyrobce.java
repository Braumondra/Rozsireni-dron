package com.example.dron;

public class Vyrobce extends Thread {

    private final Sklad sklad;
    private final String typ;
    private volatile boolean running = true;
    private int vyrobeno = 0;

    public Vyrobce(Sklad sklad, String typ) {
        super("VYROBCE-" + typ);
        this.sklad = sklad;
        this.typ = typ;
    }

    public void stopRunning() {
        running = false;
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
                    uspech = sklad.vyrobRam();
                    break;
                case "VRTULE":
                    uspech = sklad.vyrobVrtule();
                    break;
                case "DESKA":
                    uspech = sklad.vyrobDesku();
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
    }

    private void sleep1s() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
    }
}
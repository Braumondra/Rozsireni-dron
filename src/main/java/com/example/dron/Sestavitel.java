package com.example.dron;

public class Sestavitel extends Thread {

    private final Sklad sklad;
    private volatile boolean running = true;

    public Sestavitel(Sklad sklad, int id) {
        super("SESTAVITEL-" + id);
        this.sklad = sklad;
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
            boolean uspech = sklad.sestavKit();

            if (uspech) {
                int cislo = sklad.getHotoveKity();
                Log.println("[" + getName()
                        + "] sestavil KIT # " + cislo);
            } else {
                Log.println("[" + getName()
                        + "] čeká na komponenty");
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
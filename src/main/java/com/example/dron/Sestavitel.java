package com.example.dron;

public class Sestavitel extends Thread {

    private final Sklad sklad;
    private volatile boolean running = true;
    private int maximum;
    private int vyrobeno=0;

    public Sestavitel(Sklad sklad, int id,int maximum) {
        super("SESTAVITEL-" + id);
        this.sklad = sklad;
        this.maximum = maximum;
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
            if (sklad.getHotoveKity()>=maximum){
                stopRunning();
                System.out.println("[System] Stop");
            }
            boolean uspech = sklad.sestavKit();

            if (uspech) {
                vyrobeno++;
                int cislo = sklad.getHotoveKity();
                Log.println("[" + getName()
                        + "] sestavil KIT # " + cislo);
            } else {
                Log.println("[" + getName()
                        + "] čeká na komponenty");
            }


            sleep1s();
        }
        Log.println("[Tvůrce "+getName()+"] Vytvořil "+vyrobeno+" dronů");
    }

    private void sleep1s() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
    }
}
package com.example.dron;

public class Sklad {


    private int hlinik = 10000;
    private int plast = 10000;
    private int cipy = 1000;


    private int ram = 0;
    private int vrtule = 0;
    private int deska = 0;
    private volatile boolean paused = false;


    private int hotoveKity = 0;



    public synchronized boolean vyrobRam() {
        if (hlinik >= 60) {
            hlinik -= 60;
            ram++;
            return true;
        }
        return false;
    }

    public synchronized boolean vyrobVrtule() {
        if (plast >= 30) {
            plast -= 30;
            vrtule++;
            return true;
        }
        return false;
    }

    public synchronized boolean vyrobDesku() {
        if (cipy >= 2 && hlinik >= 10) {
            cipy -= 2;
            hlinik -= 10;
            deska++;
            return true;
        }
        return false;
    }


    public synchronized boolean sestavKit() {
        if (ram >= 1 && vrtule >= 1 && deska >= 1) {
            ram--;
            vrtule--;
            deska--;
            hotoveKity++;
            return true;
        }
        return false;
    }

    public synchronized void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }

    public synchronized int getHlinik() { return hlinik; }
    public synchronized int getPlast() { return plast; }
    public synchronized int getCipy() { return cipy; }

    public synchronized int getRam() { return ram; }
    public synchronized int getVrtule() { return vrtule; }
    public synchronized int getDeska() { return deska; }



    public synchronized int getHotoveKity() { return hotoveKity; }
}
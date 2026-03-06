package com.example.dron;

import java.util.Random;

public class Sklad {


    private int hlinik = 10000;
    private int plast = 10000;
    private int cipy = 1000;


    private int ram = 0;
    private int vrtule = 0;
    private int deska = 0;
    private volatile boolean paused = false;


    private int hotoveKity = 0;


    public synchronized void vypisStav() {
        System.out.println("=== Stav skladu ===");
        System.out.println("Hlinik: " + hlinik);
        System.out.println("Plast: " + plast);
        System.out.println("Cipy: " + cipy);

        System.out.println("Ram: " + ram);
        System.out.println("Vrtule: " + vrtule);
        System.out.println("Deska: " + deska);

        System.out.println("Hotove kity: " + hotoveKity);
        System.out.println("-------------------");
    }

    public synchronized boolean vyrobRam() {
        if (hlinik >= 60) {
            hlinik -= 60;
            ram++;

            vypisStav();
            return true;
        }
        return false;
    }

    public synchronized boolean vyrobVrtule() {
        if (plast >= 30) {
            plast -= 30;
            vrtule++;

            vypisStav();
            return true;
        }
        return false;
    }

    public synchronized boolean vyrobDesku() {
        if (cipy >= 2 && hlinik >= 10 && plast>=5) {
            cipy -= 2;
            hlinik -= 10;
            plast -= 5;
            deska++;

            vypisStav();

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

            vypisStav();
            return true;
        }
        return false;
    }

    public synchronized boolean Skladnik() {
        Random rand = new Random();
        double pridejcip=rand.nextInt(1000)+1;
        double pridejplast=rand.nextInt(1000)+1;
        double pridejhlinik=rand.nextInt(100)+1;

        setCipy((int) (getCipy()+pridejcip));
        setPlast((int) (getPlast()+pridejplast));
        setHlinik((int) (getHlinik()+pridejhlinik));
        System.out.println("[SKLADNIk] doplnil: Hliník+"+pridejhlinik+"g, Plast+"+pridejplast+" Čipy+"+pridejcip+"ks");
        setCipy(getCipy());
        return true;
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


    public void setHlinik(int hlinik) {
        this.hlinik = hlinik;
    }

    public void setPlast(int plast) {
        this.plast = plast;
    }

    public void setCipy(int cipy) {
        this.cipy = cipy;
    }

    public synchronized int getHotoveKity() { return hotoveKity; }
}
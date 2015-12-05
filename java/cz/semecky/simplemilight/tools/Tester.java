package cz.semecky.simplemilight.tools;

import cz.semecky.simplemilight.core.RGBW;

import java.io.IOException;

/**
 * This class is not core part of the Simple Mylight library, it only shows sample usage of it.
 */
public class Tester {

    private static final String ADDRESS = "192.168.0.102";

    public static void main(String[] args) throws IOException, InterruptedException {
        reset();
        cycleColors();
        fadeOut();
        fadeIn();
        reset();
        disco();
        reset();
//        scanAddressses(RGBW.all.off(), 102, 102);
    }

    private static void disco() throws IOException, InterruptedException {
        RGBW.discoMode().send(ADDRESS);
        Thread.sleep(3_000);
        RGBW.speedUp().send(ADDRESS);
        RGBW.speedUp().send(ADDRESS);
        Thread.sleep(3_000);
    }

    private static void reset() throws IOException, InterruptedException {
        RGBW.all.on().send(ADDRESS);
        RGBW.all.white().send(ADDRESS);
        RGBW.pause();
    }

    private static void cycleColors() throws InterruptedException, IOException {
        RGBW.Zone.ZONE_1.on().send(ADDRESS);
        for (byte i = -128; i < 127; i++) {
            System.out.println("COLOR: " + i);
            RGBW.zone(1).color(i).send(ADDRESS);
            Thread.sleep(10);
        }
    }

    private static void fadeIn() throws IOException, InterruptedException {
        RGBW.Zone zone = RGBW.zone(1);
        for (byte brightness = 2; brightness < 27; brightness++) {
            zone.brightness(brightness).send(ADDRESS);
            Thread.sleep(50);
        }
    }

    private static void fadeOut() throws InterruptedException, IOException {
        RGBW.Zone zone = RGBW.zone(1);
        for (byte brightness = 27; brightness > 2; brightness--) {
            zone.brightness(brightness).send(ADDRESS);
            Thread.sleep(50);
        }
    }
}

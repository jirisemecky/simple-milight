package cz.semecky.simplemilight;

import java.io.IOException;

/**
 * This class is not core part of the Simple Mylight library, it only shows sample usage of it.
 */
public class Tester {

    private static final String ADDRESS = "192.168.0.101";

    public static void main(String[] args) throws IOException, InterruptedException {
        // Sets all zones to warm white.
        RGBW.all.on().send(ADDRESS);
        RGBW.pause();

        /* Cycles through all the available colors. */
        cycleColors();
        RGBW.pause();

        RGBW.zone(1).white().send(ADDRESS);
        RGBW.pause();

        /* Fades out the brightness. */
        fadeOut();

        /* Fades in the brightness. */
        fadeIn();

        RGBW.all.off().send(ADDRESS);

        /* Scans addresses in the range of 192.168.0.100 - 192.168.0.200 (for searching for the bridge IP). */
        // scanAddressses(RGBWCommands.ALL_ON, 100, 200);
    }

    private static void cycleColors() throws InterruptedException, IOException {
        RGBW.Zone.ZONE_1.on().send(ADDRESS);
        for (byte i = -128; i < 127; i++) {
            System.out.println("COLOR: " + i);
            RGBW.zone(1).color(i).send(ADDRESS);
            Thread.sleep(20);
        }
    }

    private static void fadeIn() throws IOException, InterruptedException {
        RGBW.Zone zone = RGBW.zone(1);
        for (byte brightness = 2; brightness < 27; brightness++) {
            zone.brightness(brightness).send(ADDRESS);
            Thread.sleep(200);
        }
    }

    private static void fadeOut() throws InterruptedException, IOException {
        RGBW.Zone zone = RGBW.zone(1);
        for (byte brightness = 27; brightness > 2; brightness--) {
            zone.brightness(brightness).send(ADDRESS);
            Thread.sleep(200);
        }
    }

    private static void scanAddressses(Command command, int from, int to) throws IOException, InterruptedException {
        for (int i = from; i < to; i++) {
            String address = "192.168.0." + i;
            command.send(address);
        }
    }
}

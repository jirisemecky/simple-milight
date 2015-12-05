package cz.semecky.simplemilight.tools;

import cz.semecky.simplemilight.core.Command;
import cz.semecky.simplemilight.core.RGBW;

import java.io.IOException;
import java.util.Scanner;

/**
 * Tool for scanning IP addresses in a local network to find which IP has the milight-hub.
 */
public class IpScanner {

    public static final String BASE_IP = "192.168.0.";
    public static final RGBW.Zone ZONE = RGBW.all;
    public static final int FROM_IP = 0;
    public static final int TO_IP = 255;

    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner keyboardScanner = new Scanner(System.in);
        int fromIP = FROM_IP;
        int toIP = TO_IP;

        System.out.println("We will ask a couple of times whether you saw the light blinking.");
        while (fromIP != toIP) {
            int middlePoint = (fromIP + toIP) / 2;
            blinkLights(fromIP, middlePoint);
            System.out.print("Did the light blink? [Y | N]: ");
            String input = keyboardScanner.next();
            if ("Y".equals(input.toUpperCase())) {
                toIP = middlePoint;
            } else if ("N".equals(input.toUpperCase())) {
                fromIP = middlePoint + 1;
            } else {
                System.out.println("Input was not understood, only enter 'Y' or 'N'. Start again.");
                return;
            }
        }

        assert fromIP == toIP;
        System.out.println("The IP address of the lights (if any) is '"
            + BASE_IP + toIP + "'.");
        rainbow(BASE_IP + toIP);
    }

    private static void blinkLights(int fromIP, int toIP) throws IOException, InterruptedException {
        scanAddressses(ZONE.off(), fromIP, toIP);
        Thread.sleep(500);
        scanAddressses(ZONE.on(), fromIP, toIP);
    }

    private static void rainbow(String ip) throws IOException, InterruptedException {
        ZONE.color(RGBW.COLOR_RED).send(ip);
        Thread.sleep(500);
        ZONE.color(RGBW.COLOR_GREEN).send(ip);
        Thread.sleep(500);
        ZONE.color(RGBW.COLOR_ROYAL_BLUE).send(ip);
        Thread.sleep(500);
        ZONE.white().send(ip);
    }

    private static void scanAddressses(Command command, int from, int to) throws IOException, InterruptedException {
        for (int i = from; i <= to; i++) {
            command.send(BASE_IP + i);
        }
    }
}

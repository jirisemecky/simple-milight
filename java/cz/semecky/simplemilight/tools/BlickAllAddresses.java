package cz.semecky.simplemilight.tools;

import cz.semecky.simplemilight.core.RGBW;

import java.io.IOException;

public class BlickAllAddresses {

    public static void main(String[] args) throws InterruptedException, IOException {
        String address = "192.168.0.";
        System.out.print("Starting ");
        for (int i = 0; i < 255; i++) {
            RGBW.ZONE_1.off().send(address + i);
            System.out.print(".");
        }
        Thread.sleep(3000);
        for (int i = 0; i < 255; i++) {
            RGBW.ZONE_1.on().send(address + i);
            System.out.print(".");
        }
        System.out.println(" done.");
    }
}

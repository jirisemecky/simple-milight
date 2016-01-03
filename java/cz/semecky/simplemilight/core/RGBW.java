package cz.semecky.simplemilight.core;


/**
 * Instruction set for the RGB+W LimitlessLED light bulbs.
 *
 * @author Jiri Semecky (jiri.semecky@gmail.com)
 */
public class RGBW {

    private static final int PAUSE_DELAY_MS = 200;

    // Zone commands are returned by the methods of the enum.
    public static class Zone {

        private Instruction onInstruction;
        private Instruction offInstruction;
        private Instruction whiteInstruction;

        private Zone(Instruction onInstruction, Instruction offInstruction, Instruction whiteInstruction) {
            this.onInstruction = onInstruction;
            this.offInstruction = offInstruction;
            this.whiteInstruction = whiteInstruction;
        }

        public Command white() {
            return new Command(onInstruction, whiteInstruction);
        }

        public Command on() {
            return new Command(onInstruction);
        }

        public Command off() {
            return new Command(offInstruction);
        }

        /** @param brightnessValue range is 0x02 to 0x1B (2 - 27). */
        public Command brightness(byte brightnessValue) {
            return new Command(onInstruction, brightnessInstruction(brightnessValue));
        }

        /** @param colorValue range is 0 to 255 (0x00 to 0xFF). */
        public Command color(byte colorValue) {
            return new Command(onInstruction, colorInstruction(colorValue));
        }
    }

    /** Constant for the ALL zone, setting all zones together. */
    public static final Zone ALL    = new Zone(new Instruction("42 00 55"), new Instruction("41 00 55"), new Instruction("C2 00 55"));
    public static final Zone ZONE_1 = new Zone(new Instruction("45 00 55"), new Instruction("46 00 55"), new Instruction("C5 00 55"));
    public static final Zone ZONE_2 = new Zone(new Instruction("47 00 55"), new Instruction("48 00 55"), new Instruction("C7 00 55"));
    public static final Zone ZONE_3 = new Zone(new Instruction("49 00 55"), new Instruction("4A 00 55"), new Instruction("C9 00 55"));
    public static final Zone ZONE_4 = new Zone(new Instruction("4B 00 55"), new Instruction("4C 00 55"), new Instruction("CB 00 55"));

    // Color constants.
    public static final byte COLOR_VIOLET = 0x00;
    public static final byte COLOR_ROYAL_BLUE = 0x10;
    public static final byte COLOR_BABY_BLUE = 0x20;
    public static final byte COLOR_AQUA = 0x30;
    public static final byte COLOR_MINT = 0x40;
    public static final byte COLOR_SEAFOAM_GREEN = 0x50;
    public static final byte COLOR_GREEN = 0x60;
    public static final byte COLOR_LIME_GREEN = 0x70;
    public static final byte COLOR_YELLOW = 0x80 - 0xFF;
    public static final byte COLOR_YELLOW_ORANGE = 0x90 - 0xFF;
    public static final byte COLOR_ORANGE = 0xA0 - 0xFF;
    public static final byte COLOR_RED = 0xB0 - 0xFF;
    public static final byte COLOR_PINK = 0xC0 - 0xFF;
    public static final byte COLOR_FUSIA = 0xD0 - 0xFF;
    public static final byte COLOR_LILAC = 0xE0 - 0xFF;
    public static final byte COLOR_LAVENDAR = 0xF0 - 0xFF;

    // Global commands.
    public static Command discoMode() {
        return new Command(new Instruction("4D 00 55"));
    }

    public static Command speedUp() {
        return new Command(new Instruction("44 00 55"));
    }

    public static Command speedDown() {
        return new Command(new Instruction("43 00 55"));
    }

    /** Method returning context of zone 1 through 4. */
    public static final Zone zone(int zoneNumber) {
        switch (zoneNumber) {
            case 1: return ZONE_1;
            case 2: return ZONE_2;
            case 3: return ZONE_3;
            case 4: return ZONE_4;
            default: throw new IllegalArgumentException("Allowed zones are only from 1 to 4.");
        }
    }

    /** Pause between the commands prevents confusing the bridge. */
    public static void pause() throws InterruptedException {
        Thread.sleep(PAUSE_DELAY_MS);
    }


    // Private methods.

    /**
     * Returns the instruction for setting the color.
     * The colorValue range is 0 to 255 (0x00 to 0xFF).
     * Note that byte variable is always signed.
     */
    private static final Instruction colorInstruction(byte colorValue) {
        return new Instruction((byte) 0x40, colorValue, (byte) 0x55);
    }

    /** @param brightnessValue range is 0x02 to 0x1B (2 - 27). */
    private static final Instruction brightnessInstruction(byte brightnessValue) {
        if (brightnessValue < 2 || brightnessValue > 27) {
            throw new IllegalArgumentException("The brightness value has to be between 2 and 27.");
        }
        return new Instruction((byte) 0x4E, (byte) brightnessValue, (byte) 0x55);
    }
}

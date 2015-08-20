package cz.semecky.simplemilight;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Commands for the Milight bridge to modify the state of Limitless LED bulbs.
 * Each command contains one or more instructions which are sent one by one.
 *
 * @author Jiri Semecky (jiri.semecky@gmail.com)
 */
class Command {

    private static final int DEFAULT_PORT = 8899;

    private final List<Instruction> instructions = new ArrayList<>();
    /** Delay between sending two instructions. */
    private int delayMs = 100;

    /**
     * Creates command from the provided instructions.
     * @param instr List of instructions
     */
    public Command(Instruction... instr) {
        for (Instruction instruction : instr) {
            this.instructions.add(instruction);
        }
    }

    public void setDelay(int delayMs) {
        this.delayMs = delayMs;
    }

    /** Sends command to the provided address and port. */
    public void send(String inetAddress, int port) throws InterruptedException, IOException {
        send(InetAddress.getByName(inetAddress), port);
    }

    /** Sends command to the provided address and port. */
    public void send(InetAddress inetAddress, int port) throws InterruptedException, IOException {
        boolean first = true;
        for (Instruction instruction : instructions) {
            if (!first) {
                Thread.sleep(delayMs);
            }
            instruction.send(inetAddress, port);
            first = false;
        }
    }

    /** Sends command to the provided address and the default port. */
    public void send(String inetAddress) throws IOException, InterruptedException {
        send(inetAddress, DEFAULT_PORT);
    }

    /** Sends command to the provided address and the default port. */
    public void send(InetAddress inetAddress) throws IOException, InterruptedException {
        send(inetAddress, DEFAULT_PORT);
    }
}

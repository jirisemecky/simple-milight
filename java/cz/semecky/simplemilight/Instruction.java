package cz.semecky.simplemilight;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Single instruction of the command that can be sent to Milight bridge.
 *
 * @author Jiri Semecky (jiri.semecky@gmail.com)
 */
public class Instruction {
    private final byte[] data;

    public Instruction(byte... data) {
        this.data = data;
    }

    public Instruction(String data) {
        String[] bytes = data.split(" ");
        this.data = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            this.data[i] = (byte) (Integer.parseInt(bytes[i],16) & 0xFF);
        }
    }

    public byte[] getData() {
        return data;
    }

    public int getLength() {
        return data.length;
    }

    void send(InetAddress inetAddress, int port) throws IOException, InterruptedException {
        DatagramPacket packet = new DatagramPacket(data, data.length, inetAddress, port);
        DatagramSocket socket = new DatagramSocket();
        socket.send(packet);
        socket.close();
    }
}

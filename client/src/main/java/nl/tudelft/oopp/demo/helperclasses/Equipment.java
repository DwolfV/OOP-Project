package nl.tudelft.oopp.demo.helperclasses;

public class Equipment {

    int pc;
    int whiteboard;
    int beamer;
    int microphone;

    /**
     * Create a new Equipment instance.
     *
     * @param pc A counter for available pc.Caused by: java.net.ConnectException: Connection refused: no further information
     * @param whiteboard A counter for available whiteboards.
     * @param beamer A counter for available beamers.
     * @param microphone A counter for available microphones.
     */

    public Equipment(int pc, int whiteboard, int beamer, int microphone) {
        this.pc = pc;
        this.whiteboard = whiteboard;
        this.beamer = beamer;
        this.microphone = microphone;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getWhiteboard() {
        return whiteboard;
    }

    public void setWhiteboard(int whiteboard) {
        this.whiteboard = whiteboard;
    }

    public int getBeamer() {
        return beamer;
    }

    public void setBeamer(int beamer) {
        this.beamer = beamer;
    }

    public int getMicrophone() {
        return microphone;
    }

    public void setMicrophone(int microphone) {
        this.microphone = microphone;
    }
}

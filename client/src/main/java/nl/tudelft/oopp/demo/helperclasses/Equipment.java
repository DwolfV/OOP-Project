package nl.tudelft.oopp.demo.helperclasses;

public class Equipment {

    int pc, whiteboard, beamer, microhpone;

    public Equipment(int pc, int whiteboard, int beamer, int microhpone) {
        this.pc = pc;
        this.whiteboard = whiteboard;
        this.beamer = beamer;
        this.microhpone = microhpone;
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

    public int getMicrohpone() {
        return microhpone;
    }

    public void setMicrohpone(int microhpone) {
        this.microhpone = microhpone;
    }
}

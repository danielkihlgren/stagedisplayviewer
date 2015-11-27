package se.pingstteknik.propresenter.stagedisplayviewer.util;

import se.pingstteknik.propresenter.stagedisplayviewer.config.Property;

import javax.sound.midi.*;

/**
 * Midi module to support midi commands to be extracted from propresenter slides
 * @author Daniel Kihlgren
 * @version 1.1.0
 * @since 1.1.0
 */
public class MidiModule {

    private final Receiver receiver;
    private String lastMessage;

    public MidiModule() {
        Receiver tempReceiver = null;

        if (Property.MIDI.isTrue()) {
            try {
                tempReceiver = MidiSystem.getReceiver();
            } catch (MidiUnavailableException e) {
                System.out.println("Could not load midi system");
            }
        }
        receiver = tempReceiver;
    }

    public void handleMessage(String message) {
        if (receiver !=null && message.matches("Midi \\d* \\d* \\d*") && !message.equals(lastMessage)) {
            lastMessage = message;
            String[] split = message.split(" ");
            try {
                ShortMessage shortMessage = new ShortMessage(ShortMessage.NOTE_ON, Integer.valueOf(split[1]), Integer.valueOf(split[2]), Integer.valueOf(split[3]));
                receiver.send(shortMessage, -1);
            } catch (InvalidMidiDataException e) {
                e.printStackTrace();
            }
        }
    }

    public void terminate() {
        if (receiver != null) {
            receiver.close();
        }
    }
}

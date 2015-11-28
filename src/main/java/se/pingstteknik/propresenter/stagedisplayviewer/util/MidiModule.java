package se.pingstteknik.propresenter.stagedisplayviewer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.pingstteknik.propresenter.stagedisplayviewer.config.Property;

import javax.sound.midi.*;

/**
 * Midi module to support midi commands to be extracted from propresenter slides
 * @author Daniel Kihlgren
 * @version 1.2.0
 * @since 1.1.0
 */
public class MidiModule {

    private static final Logger log = LoggerFactory.getLogger(MidiModule.class);
    private final Receiver receiver;
    private String lastMessage;

    public MidiModule() {
        Receiver tempReceiver = null;

        if (Property.MIDI.isTrue()) {
            try {
                tempReceiver = MidiSystem.getReceiver();
                log.info("Midi module loaded");
            } catch (MidiUnavailableException e) {
                log.error("Midi module failed to load", e);
            }
        }
        receiver = tempReceiver;
    }

    public void handleMessage(String message) {
        if (receiver !=null && message.matches("Midi \\d* \\d* \\d*") && !message.equals(lastMessage)) {
            String[] split = message.split(" ");
            try {
                ShortMessage shortMessage = new ShortMessage(ShortMessage.NOTE_ON, Integer.valueOf(split[1]), Integer.valueOf(split[2]), Integer.valueOf(split[3]));
                receiver.send(shortMessage, -1);
                log.debug("Midi message sent: {}", message);
            } catch (InvalidMidiDataException e) {
                log.warn("Midi message failed: {}", message, e);
            }
        }
        lastMessage = message;
    }

    public void terminate() {
        if (receiver != null) {
            receiver.close();
        }
    }
}

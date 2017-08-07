package se.pingstteknik.propresenter.stagedisplayviewer.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;

import se.pingstteknik.propresenter.stagedisplayviewer.config.Property;

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
    private static final Pattern MIDI_COMMAND_REGEX = Pattern.compile("(?i)Midi(?-i) (\\d+) (\\d+) (\\d+)");

    public MidiModule() {
        Receiver tempReceiver = null;

        if (Property.MIDI.isTrue()) {
            try {
                tempReceiver = MidiSystem.getReceiver();
                log.info("Midi module loaded");
            } catch (MidiUnavailableException e) {
                log.error("Midi module failed to load {}", e);
            }
        }
        receiver = tempReceiver;
    }

    public void handleMessage(String message) {
    	Matcher m = MIDI_COMMAND_REGEX.matcher(message);
        if (receiver !=null && m.find() && !message.equals(lastMessage)) {
            try {
                ShortMessage shortMessage = new ShortMessage(
            		ShortMessage.NOTE_ON, 
            		Integer.valueOf(m.group(1)), 
            		Integer.valueOf(m.group(2)), 
            		Integer.valueOf(m.group(3))
        		);
                receiver.send(shortMessage, -1);
                log.debug("Midi message sent: {}", message);
            } catch (InvalidMidiDataException e) {
                log.warn("Midi message failed: {} {}", message, e);
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

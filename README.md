Stage Display Viewer
==================

Stage display viewer in java for propresenter.

The output text is shown in a lower key fashion, i.e. it's shown in the lower part of the screen. It also compactifies rows by removing every second line break, i.e. 4 rows will be shown as 2 rows.

It should work in Windows/MacOS/Linux and other *nix systems

# Usage
Propresenter must be running when running stage display viewer.

Either just double click the jar file or, if your OS is not configured for starting jar files, start program as following:

Windows:

    run.bat

MacOS/Linux/*nix

    ./run.sh

## Transform text
Text transform support: compactify rows by removing every second line break, i.e. 4 rows will be shown as 2 rows.
Active module in properies file config.properties

    TEXT_TRANSLATOR_ACTIVE = true

## Preserve two lines
This setting will preserve two lines and not convert that into one line when text_translator_active is true

    PRESERVE_TWO_LINES = true  
   
## Custom New Line Insertion
Makes it possible to explicitly place each new line in the slide.
In a slide that should have custom new lines, add commands line this to the Stage Display Notes:

    NEWLINE x 
   
Where x is the position of the word the newline should be inserted after. You can insert multiple newlines by 
adding more numbers to the command separated by a space.
Example:

    NEWLINE 1 4

inserts newlines after the first and fourth word.

All whitespace other than what is specified in the NEWLINE command is removed.

Can be used on the same slide as a Midi command, just separate them with a space:

    NEWLINE 1 4 Midi 0 60 92

## Multi language support
Removes lines after an empty line
This is useful if multiple languages are shown separated with a line break and only the first language are to be shown

    REMOVE_LINES_AFTER_EMPTY_LINE = true
    
## Change margin below text
This setting sets the margin below the text

    MARGIN_BOTTOM = 30


## Midi module
Midi module makes it possible to send midi commands from propresenter.

Activate midi module in properties file config.properties

    MIDI = true
    
In propresenter on a slide that should send a midi command should add commands like this to Stage Display Notes:

    Midi 0 60 92

Where

    Midi channel note velocity

The 0 indicates the channel, 60 indicates the note Middle C and the 92 is an arbitrary key-down velocity value

# [Releases](https://github.com/danielkihlgren/stagedisplayviewer/releases)

[v1.6.0](https://github.com/danielkihlgren/stagedisplayviewer/releases/tag/v1.6.0) [download](https://github.com/danielkihlgren/stagedisplayviewer/releases/download/v1.6.0/StageDisplayViewer-v1.6.0.zip)
Fixed Issues #15: Enhancements Request
Fixed Issues #17: Enhancements Request
Fixed Issues #18: Fix fading issues when font size changes
Fixed Issues #19: Add window mode support

[v1.5.0](https://github.com/danielkihlgren/stagedisplayviewer/releases/tag/v1.5.0) [download](https://github.com/danielkihlgren/stagedisplayviewer/releases/download/v1.5.0/StageDisplayViewer.zip)
Make it more resilience by adding auto-reconnect

[v1.4.0](https://github.com/danielkihlgren/stagedisplayviewer/releases/tag/v1.4.0) [download](https://github.com/danielkihlgren/stagedisplayviewer/releases/download/v1.4.0/StageDisplayViewer.zip)
Add feature #9 to make it possible to change margin below text

[v1.3.0](https://github.com/danielkihlgren/stagedisplayviewer/releases/tag/v1.3.0) [download](https://github.com/danielkihlgren/stagedisplayviewer/releases/download/v1.3.0/StageDisplayViewer.zip)
Add feature #8 to make it possible to preserve two rows
Bug fixes #1, #4

[v1.2.0](https://github.com/danielkihlgren/stagedisplayviewer/releases/tag/v1.2.0) [download](https://github.com/danielkihlgren/stagedisplayviewer/releases/download/v1.2.0/StageDisplayViewer.zip)
Add support for propresenter 6 for windows

[v1.1.0](https://github.com/danielkihlgren/stagedisplayviewer/releases/tag/v1.1.0) [download](https://github.com/danielkihlgren/stagedisplayviewer/releases/download/v1.1.0/StageDisplayViewer.zip)
Add midi module support. This makes it possible to send midi command from propresenter.

[v1.0.0](https://github.com/danielkihlgren/stagedisplayviewer/releases/tag/v1.0.0) [download](https://github.com/danielkihlgren/stagedisplayviewer/releases/download/v1.0.0/StageDisplayViewer.zip)
First release.

# Known problems
* Propresenter 5 for Windows does not support UTF-8 correctly which makes international characters to be shown incorrectly, e.g. Swedish characters ÅÄÖ are shown as ???. This is fixed in propresenter 6.
* [Starting on secondary screen is not correctly implemented](https://github.com/danielkihlgren/stagedisplayviewer/issues/1)

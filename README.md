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
   
## Multi language support
Removes lines after an empty line
This is useful if multiple languages are shown separated with a line break and only the first language are to be shown

    REMOVE_LINES_AFTER_EMPTY_LINE = true

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
[v1.2.0](https://github.com/danielkihlgren/stagedisplayviewer/releases/tag/v1.2.0) [download](https://github.com/danielkihlgren/stagedisplayviewer/releases/download/v1.2.0/StageDisplayViewer.zip)
Add support for propresenter 6 for windows

[v1.1.0](https://github.com/danielkihlgren/stagedisplayviewer/releases/tag/v1.1.0) [download](https://github.com/danielkihlgren/stagedisplayviewer/releases/download/v1.1.0/StageDisplayViewer.zip)
Add midi module support. This makes it possible to send midi command from propresenter.

[v1.0.0](https://github.com/danielkihlgren/stagedisplayviewer/releases/tag/v1.0.0) [download](https://github.com/danielkihlgren/stagedisplayviewer/releases/download/v1.0.0/StageDisplayViewer.zip)
First release.

# Known problems
* Propresenter 5 for Windows does not support UTF-8 correctly which makes international characters to be shown incorrectly, e.g. Swedish characters ÅÄÖ are shown as ???. This is fixed in propresenter 6.
* [Starting on secondary screen is not correctly implemented](https://github.com/danielkihlgren/stagedisplayviewer/issues/1)

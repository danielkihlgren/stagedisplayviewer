stagedisplayviewer
==================

Stage display viewer in java for propresenter
The output text is shown in a lower key fashion, i.e. it's shown in the lower part of the screen. It also compactifies rows by removing every second line break, i.e. 4 rows will be shown as 2 rows.

It should work in Windows/MacOS/Linux and other *nix systems

# Usage
Either just double click the jar file or, if your OS is not configured for starting jar files, start program as following:

Windows:

run.bat

MacOS/Linux/*nix

./run.sh

# Known problems
* Propresenter for Windows does not support UTF-8 correctly which makes international characters to be shown incorrectly, e.g. Swedish characters ÅÄÖ are shown as ???.
* Starting on secondary screen is not correctly implemented

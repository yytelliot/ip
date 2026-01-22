#!/usr/bin/env bash

# create bin directory if it doesn't exist
if [ ! -d "../bin" ]
then
    mkdir ../bin
fi

# delete output from previous run
if [ -e "./ACTUAL.TXT" ]
then
    rm -f ACTUAL.TXT SOURCES.TXT EXPECTED-UNIX.TXT
fi

# collect all java files under src/main/java into SOURCES.TXT
find ../src/main/java -name "*.java" > SOURCES.TXT

# compile the code into the bin folder, terminates if error occurred
if ! javac -Xlint:none -d ../bin -sourcepath ../src/main/java @SOURCES.TXT
then
  echo "********** BUILD FAILURE **********"
  rm -f SOURCES.TXT
  exit 1
fi

# clean up temporary file
rm -f SOURCES.TXT

# run the program, feed commands from input.txt file and redirect the output to ACTUAL.TXT
java -classpath ../bin pookie.Pookie < input.txt > ACTUAL.TXT

# convert to UNIX format
cp EXPECTED.TXT EXPECTED-UNIX.TXT
dos2unix ACTUAL.TXT EXPECTED-UNIX.TXT

# compare the output to the expected output
diff ACTUAL.TXT EXPECTED-UNIX.TXT
if [ $? -eq 0 ]
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi
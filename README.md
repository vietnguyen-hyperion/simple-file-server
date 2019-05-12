# simple-file-server
Simple network file server

Steps to start Server by command line
  1. mvn install (current using Java 8)
  2. execute jar file to start server with specified folder

    java -cp fileserver-1.0-SNAPSHOT.jar com.exercise.fileserver.FileServer D:\test

  3. start client to test: 

    java -cp fileserver-1.0-SNAPSHOT.jar com.exercise.fileserver.FileClient


Notes:

- Server folder only support text file
- Server folder does not support sub folder
- Open client for each command

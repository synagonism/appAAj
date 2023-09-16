del ..\*.class
del ..\pk_Html\*.class
del ..\pk_XKBEditor\*.class
del ..\pk_XKBManager\*.class
del ..\pk_SSemasia\*.class
del ..\pk_Logo\*.class
del ..\pk_Util\*.class

javac -Xlint *.java
javac -Xlint pk_Html\*.java
javac -Xlint pk_XKBEditor\*.java
javac -Xlint pk_XKBManager\*.java
javac -Xlint pk_SSemasia\*.java
javac -Xlint pk_Logo\*.java
javac -Xlint pk_Util\*.java

move *.class ..
move pk_Html\*.class ..\pk_Html
move pk_XKBEditor\*.class ..\pk_XKBEditor
move pk_XKBManager\*.class ..\pk_XKBManager
move pk_SSemasia\*.class ..\pk_SSemasia
move pk_Logo\*.class ..\pk_Logo
move pk_Util\*.class ..\pk_Util



.SUFFIXES: .java .class

.java.class:
	javac -g *.java
	set CLASSPATH=%CLASSPATH%

CLASSES = \
	risingCity.java
default: classes

classes: $(CLASSES:.java=.class)

clean:
	rm -f *.class

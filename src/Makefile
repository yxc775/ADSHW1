
.SUFFIXES: .java .class

.java.class:
	javac -g *.java
	set CLASSPATH=%CLASSPATH%

CLASSES = \
	BuildingInfo.java \
	MinHeap.java \
    RBNode.java \
    RBTree.java \
    risingCity.java
default: classes

classes: $(CLASSES:.java=.class)

clean:
	rm -f *.class

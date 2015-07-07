all: org.tuc.wmg.ServerUI.class

org.tuc.wmg.GameMsg.class: org/tuc/wmg/GameMsg.java
	javac $<

org.tuc.wmg.ServerUI.class: org/tuc/wmg/ServerUI.java org/tuc/wmg/GameMsg.java org/tuc/wmg/GameLevel.java org/tuc/wmg/GameStatusPane.java org/tuc/wmg/GameThread.java org/tuc/wmg/ServerController.java org/tuc/wmg/menu/AboutDialog.java org/tuc/wmg/menu/ControlActions.java org/tuc/wmg/menu/ControlMenuBar.java org/tuc/wmg/menu/ControlToolBar.java
	javac $^

org/tuc/wmg/GameMsg.java: ./WMGMessages.h
	mig java -target=null -java-classname=org.tuc.wmg.GameMsg $< GameMsg -o $@

clean:
	rm -f *.class ./org/tuc/wmg/GameMsg.java

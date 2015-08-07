all: org.tuc.wmg.ServerUI.class

org.tuc.wmg.GameMsg.class: org/tuc/wmg/GameMsg.java
	javac $<

org.tuc.wmg.ServerUI.class: org/tuc/wmg/ServerUI.java org/tuc/wmg/GameMsg.java org/tuc/wmg/GameLevel.java org/tuc/wmg/GameStatusPane.java org/tuc/wmg/GameRankPane.java org/tuc/wmg/GamePlayWindow.java org/tuc/wmg/GameThread.java org/tuc/wmg/menu/AboutDialog.java org/tuc/wmg/menu/ControlActions.java org/tuc/wmg/menu/ControlMenuBar.java org/tuc/wmg/menu/ControlToolBar.java org/tuc/wmg/menu/OptionsDialog.java org/tuc/wmg/utils/CheckMolesUtil.java org/tuc/wmg/utils/USBDetectionUtil.java
	javac $^

org/tuc/wmg/GameMsg.java: WMGMessages.h
	nescc-mig java -java-classname=org.tuc.wmg.GameMsg $< WMGMsg -o $@

clean:
	rm -f *.class ./org/tuc/wmg/GameMsg.java

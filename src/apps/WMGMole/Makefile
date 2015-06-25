COMPONENT=WMGMoleAppC
#CFLAGS += -DCC2420_DEF_CHANNEL=25
#CFLAGS += -I$(TOSDIR)/lib/printf
#CFLAGS += -DNEW_PRINTF_SEMANTICS
BUILD_EXTRA_DEPS = WMGMoleMsg.py WMGMoleMsg.class
CLEAN_EXTRA = WMGMoleMsg.py WMGMoleMsg.class WMGMoleMsg.java

WMGMoleMsg.py: WMGMole.h
	mig python -target=$(PLATFORM) $(CFLAGS) -python-classname=WMGMoleMsg WMGMole.h radio_count_msg -o $@

WMGMoleMsg.class: WMGMoleMsg.java
	javac WMGMoleMsg.java

WMGMoleMsg.java: WMGMole.h
	mig java -target=$(PLATFORM) $(CFLAGS) -java-classname=WMGMoleMsg WMGMole.h radio_count_msg -o $@


include $(MAKERULES)


COMPONENT=WMGMasterAppC
CFLAGS += -DCC2420_DEF_CHANNEL=25
CFLAGS += -I$(TOSDIR)/lib/printf
CFLAGS += -DNEW_PRINTF_SEMANTICS
BUILD_EXTRA_DEPS = WMGMasterMsg.py WMGMasterMsg.class
CLEAN_EXTRA = WMGMasterMsg.py WMGMasterMsg.class WMGMasterMsg.java

WMGMasterMsg.py: WMGMaster.h
	mig python -target=$(PLATFORM) $(CFLAGS) -python-classname=WMGMasterMsg WMGMaster.h radio_count_msg -o $@

WMGMasterMsg.class: WMGMasterMsg.java
	javac WMGMasterMsg.java

WMGMasterMsg.java: WMGMaster.h
	mig java -target=$(PLATFORM) $(CFLAGS) -java-classname=WMGMasterMsg WMGMaster.h radio_count_msg -o $@


include $(MAKERULES)


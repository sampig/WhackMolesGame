COMPONENT=WMGBaseStationAppC
TINYOS_ROOT_DIR?=../..
#CFLAGS += -DCC2420_DEF_CHANNEL=25
#CFLAGS += -I$(TOSDIR)/lib/printf
#CFLAGS += -DNEW_PRINTF_SEMANTICS
BUILD_EXTRA_DEPS = WMGBaseStationMsg.py WMGBaseStationMsg.class
CLEAN_EXTRA = WMGBaseStationMsg.py WMGBaseStationMsg.class WMGBaseStationMsg.java

WMGBaseStationMsg.py: WMGBaseStation.h
	mig python -target=$(PLATFORM) $(CFLAGS) -python-classname=WMGBaseStationMsg WMGBaseStation.h radio_count_msg -o $@

WMGBaseStationMsg.class: WMGBaseStationMsg.java
	javac WMGBaseStationMsg.java

WMGBaseStationMsg.java: WMGBaseStation.h
	mig java -target=$(PLATFORM) $(CFLAGS) -java-classname=WMGBaseStationMsg WMGBaseStation.h radio_count_msg -o $@


include $(MAKERULES)


// $Id: WMGBaseStationAppC.nc,v 1.0 2015-06-23 $

/*									tab:2
 * Copyright (c) 2015
 */
 
#include "WMGBaseStation.h"

/**
 * Configuration for the WMGBaseStation application.
 *
 * @author Chenfeng ZHU
 * @date   June 23 2015
 */

configuration WMGBaseStationAppC {}
implementation {
  components MainC, WMGBaseStationC as App, LedsC;
  components new AMSenderC(AM_RADIO_COUNT_MSG);
  components new AMReceiverC(AM_RADIO_COUNT_MSG);
  components new TimerMilliC();
  components ActiveMessageC;
  
  App.Boot -> MainC.Boot;
  
  App.Receive -> AMReceiverC;
  App.AMSend -> AMSenderC;
  App.AMControl -> ActiveMessageC;
  App.Leds -> LedsC;
  App.MilliTimer -> TimerMilliC;
  App.Packet -> AMSenderC;
}



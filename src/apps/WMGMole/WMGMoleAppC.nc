// $Id: WMGMoleAppC.nc,v 1.0 2015-06-22 $

/*									tab:2
 * Copyright (c) 2015
 */
 
#include "WMGMole.h"

/**
 * Configuration for the WMGMole application.
 *
 * @author Chenfeng ZHU
 * @date   June 22 2015
 */

configuration WMGMoleAppC {}
implementation {
  components MainC, WMGMoleC as App, LedsC;
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



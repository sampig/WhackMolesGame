// $Id: WMGMasterAppC.nc,v 1.0 2015-06-30 $

/*									tab:2
 * Copyright (c) 2015
 */
 
#include "WMGMaster.h"

/**
 * Configuration for the WMGMaster application.
 *
 * @author Chenfeng ZHU
 * @date   June 30 2015
 */

configuration WMGMasterAppC {}
implementation {
  components MainC, WMGMasterC as App, LedsC;
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
  App.AMPacket -> AMSenderC;

  // math
  components RandomC;
  App.Random -> RandomC; 

  // user button
  components UserButtonC;
  App.Notify -> UserButtonC;

  // for test.
  components SerialStartC, PrintfC; 
}



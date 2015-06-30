// $Id: WMGMasterC.nc,v 1.0 2015-06-30 $

/*									tab:2
 * Copyright (c) 2015
 */
 
#include "Timer.h"
#include "WMGMaster.h"
#include "UserButton.h"
#include "printf.h"
 
/**
 * Implementation of the WMGMaster application.
 *
 * @author Chenfeng ZHU
 * @date   June 30 2015
 */

module WMGMasterC @safe() {
  uses {
    interface Leds;
    interface Boot;
    interface Receive;
    interface AMSend;
    interface Timer<TMilli> as MilliTimer;
    interface SplitControl as AMControl;
    interface Packet;
    interface AMPacket;
    interface Notify<button_state_t>;
    interface Random; //call Random.rand16()
  }
}
implementation {

  message_t packet;

  bool locked;
  bool isGameRunning = FALSE;
  uint16_t type = 0;
  uint16_t data = 0;

  uint8_t level = 1;

  // all moles
  am_addr_t moles[9];
  // number of moles
  uint8_t numMoles = 2;
  uint8_t ackMoles = 0;
  // current mole
  uint8_t randomMole = 0;
  
  event void Boot.booted() {
    call Notify.enable();
    call AMControl.start();
  }

  task void sendGameStart() {
    if (locked) {
      return;
    }
    else {
      radio_count_msg_t* rcm = (radio_count_msg_t*) call Packet.getPayload(&packet, sizeof(radio_count_msg_t));
      isGameRunning = TRUE;
      rcm->type = 0x12; // ACKStart
      if (call AMSend.send(AM_BROADCAST_ADDR, &packet, sizeof(radio_count_msg_t)) == SUCCESS) {
        locked = TRUE;
      }
      printf("Game starts.\n");
      printfflush();
    }
  }

  task void showLevel() {
    switch(level) {
      case 1:
        call Leds.led0On();
        call Leds.led1Off();
        call Leds.led2Off();
        break;
      case 2:
        call Leds.led0Off();
        call Leds.led1On();
        call Leds.led2Off();
        break;
      case 3:
        call Leds.led0Off();
        call Leds.led1On();
        call Leds.led2Off();
        break;
      default:
        call Leds.led0On();
        call Leds.led1Off();
        call Leds.led2Off();
    }
  }

  event void AMControl.startDone(error_t err) {
    if (err == SUCCESS) {
      // turn on the red LED.
      call Leds.led0On();
      call Leds.led1On();
      call Leds.led2On();
      // reset the values of number of responders and the next responder.
      numMoles = 2;
      printf("Preparing the Whack Moles Game.\n");
      printfflush();
      // start the game at first, then don't need to restart the game by pressing user button for over 2s.
      call MilliTimer.startOneShot(500);
    }
    else {
      call AMControl.start();
    }
  }

  event void AMControl.stopDone(error_t err) {
    // do nothing
  }
  
  event void MilliTimer.fired() {
    if (locked) {
      return;
    }
    else {
      radio_count_msg_t* rcm = (radio_count_msg_t*)call Packet.getPayload(&packet, sizeof(radio_count_msg_t));
      if (rcm == NULL) {
        return;
      }

      // restart the game
      post showLevel();
      numMoles = 2;
      ackMoles = 0;
      printf("Configuring the Whack Moles Game. Level is %d.\n", level);
      printfflush();

      rcm->type = 0x01; // 
      rcm->data = 3;
      if (call AMSend.send(AM_BROADCAST_ADDR, &packet, sizeof(radio_count_msg_t)) == SUCCESS) {
        locked = TRUE;
      }
    }
  }

  event message_t* Receive.receive(message_t* bufPtr, 
				   void* payload, uint8_t len) {
    if (len != sizeof(radio_count_msg_t)) {return bufPtr;}
    else {
      radio_count_msg_t* rcm = (radio_count_msg_t*)payload;
      if (rcm->type == 0x11) { // ACKReady
        // check the acknowledge
        uint8_t i = 0;
        if(ackMoles==0) {
          call MilliTimer.startOneShot(5000);
        }
        for(i=0;i<ackMoles;i++) {
          if (call AMPacket.source(bufPtr) == moles[i]) {
            break;
          }
        }
        if (i >= ackMoles) {
          moles[ackMoles] = call AMPacket.source(bufPtr);
          ackMoles++;
          printf("Mole (%d) is ready.\n",moles[ackMoles-1]);
          printfflush();
        }
        if(ackMoles>=numMoles && call MilliTimer.isRunning()) {
          call MilliTimer.stop();
          post sendGameStart();
        }
      }
      else {
        call Leds.led2Off();
      }
      return bufPtr;
    }
  }

  event void AMSend.sendDone(message_t* bufPtr, error_t error) {
    if (&packet == bufPtr) {
      locked = FALSE;
    }
  }

  event void Notify.notify(button_state_t state) {
    // press the user button, start the Timer.fired() after 2 seconds.
    if (state == BUTTON_PRESSED) {
      call Leds.led0Off();
      call Leds.led1Off();
      call Leds.led2Off();
      call MilliTimer.startOneShot(2000);
    }
    // if releasing the button in 2 seconds, stop the timer and change the level.
    if (state == BUTTON_RELEASED && call MilliTimer.isRunning() && !isGameRunning) {
      call MilliTimer.stop();
      level++;
      level = (level%3)+1;
      printf("Change the level. Level is %d.\n", level);
      printfflush();
      post showLevel();
    }
  }

}





/*
 * Copyright (c) 2015 Chenfeng ZHU
 * All rights reserved

 */

/**
 * @author Chenfeng ZHU
 */

#ifndef WMGMESSAGES_H__
#define WMGMESSAGES_H__

enum {
  AM_WMGMSG = 6
};

typedef nx_struct WMGMsg{
  nx_uint16_t type;
  nx_uint16_t data;
} WMGMsg;

#endif //WMGMESSAGES_H__

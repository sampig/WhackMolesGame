/*
 * Copyright (c) 2015
 */

#ifndef WMG_MOLE_H
#define WMG_MOLE_H

typedef nx_struct radio_count_msg {
  nx_uint16_t counter;
} radio_count_msg_t;

enum {
  AM_RADIO_COUNT_MSG = 6,
};

#endif

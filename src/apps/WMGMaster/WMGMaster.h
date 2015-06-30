/*
 * Copyright (c) 2015
 */

#ifndef WMG_MASTER_H
#define WMG_MASTER_H

typedef nx_struct radio_count_msg {
  nx_uint16_t type;
  nx_uint16_t data;
} radio_count_msg_t;

enum {
  AM_RADIO_COUNT_MSG = 6,
};

#endif

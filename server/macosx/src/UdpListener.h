/*
 * UdpListener.h
 *
 *  Created on: Jul 14, 2012
 *
 * Copyright (C) 2012 V.Shcryabets (vshcryabets@gmail.com)
 * Author:  Vladimir Shcryabets <vshcryabets@gmail.com>
 * This file is part of DNServer.
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 3 of the GNU General
 * Public License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

#ifndef UDPLISTENER_H_
#define UDPLISTENER_H_
#include "AppSettings.h"

namespace dnremote {
class UdpListener {
private:
    static const char* INPUT_REQUEST;
    static const char* KEY_SERVERTYPE;

    bool mStopUDPThread;
    AppSettings *mSettings;

public:
    UdpListener(AppSettings *settings);
    virtual ~UdpListener();
    // start listen UDP port
    void start();
    // stop listen UDP port
    void stop();
    void run();
};
}
void* listenUDP(void* arg);

#endif /* UDPLISTENER_H_ */

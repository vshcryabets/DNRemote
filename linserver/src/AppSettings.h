/*
 * AppSettings.h
 *
 *  Created on: Jul 15, 2012
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

#ifndef APPSETTINGS_H_
#define APPSETTINGS_H_

#include "uuid/uuid.h"
#include "stdlib.h"
#include <string>

namespace dnremote {
class AppSettings {
private:
    uuid_t	mServerId;
    int		mPort;
    const char* mConfFilePath;
    std::string mHostname;
public:
    static const char* KEY_ID;
    static const char* KEY_PORT;
    static const char* KEY_NAME;

    AppSettings(const char* confpath);
    virtual ~AppSettings();
    /**
     * Write configuration to file
     */
    void write();
    // returns the server ID
    uuid_t* getServerID();
    // returns server port
    int getPort();
    // returns host name
    std::string getHostName();
};
}
#endif /* APPSETTINGS_H_ */

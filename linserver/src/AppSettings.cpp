/*
 * AppSettings.cpp
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
#include "AppSettings.h"
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include "json/json.h"
#include <fstream>

using namespace dnremote;

const char* AppSettings::KEY_ID = "id";
const char* AppSettings::KEY_PORT = "port";
const char* AppSettings::KEY_NAME = "name";

AppSettings::AppSettings(const char* confpath) {
    mConfFilePath = confpath;
	Json::Value root;   // will contains the root value after parsing.
	Json::Reader reader;
	std::ifstream fin(confpath);
    char hostNameBuffer[1024];
    gethostname(hostNameBuffer, sizeof(hostNameBuffer));

	if ( !fin.is_open() ) {
		// set default values
	    mHostname = hostNameBuffer;
		mPort = 8080;
		uuid_generate(this->mServerId);
		return;
	}
	bool parsingSuccessful = reader.parse(fin, root, false);
	if ( !parsingSuccessful )
	{
	    // report to the user the failure and their locations in the document.
	    std::cout  << "Failed to parse configuration\n";
	    return;
	}
	fin.close();
	Json::Value value = root.get(KEY_PORT, 8080);
	mPort = value.asInt();
	value = root.get(KEY_ID, "");
	uuid_parse(value.asCString(), mServerId);
    value = root.get(KEY_NAME, hostNameBuffer);
    mHostname = value.asCString();
}

AppSettings::~AppSettings() {
}
/**
 * Write configuration to file
 */
void AppSettings::write() {
    Json::Value root;
    char uuidBuffer[128];
    uuid_unparse(mServerId, uuidBuffer);
    root[KEY_PORT] = mPort;
    root[KEY_ID]= uuidBuffer;
    root[KEY_NAME]= mHostname.c_str();
	std::ofstream fout;
	fout.open (mConfFilePath);
	Json::StyledWriter writer;
	fout << writer.write(root);
	fout.close();
}

// returns the server ID
uuid_t* AppSettings::getServerID() {
    return &mServerId;
}
// returns server port
int AppSettings::getPort() {
    return mPort;
}
// returns host name
std::string AppSettings::getHostName() {
    return mHostname;
}

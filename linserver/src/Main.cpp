/*
 * Main.cpp
 * Created on: May 20, 2012
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
#include <string>
#include "StyxServerManager.h"
#include "StyxLibraryException.h"
#include "vfs/MemoryStyxDirectory.h"
#include "MouseEventFiles.h"
#include "stdio.h"
using namespace std;

int main(int argc, char **argv) {
	try {
		string serveraddr = "0.0.0.0";
		MemoryStyxDirectory root ("root");
		string protocol = "9P2000";
		root.addFile(new dnremote::MouseEventFiles());
		StyxServerManager manager(serveraddr, 8080, &root, protocol);
		manager.start();
	} catch (const char *e) {
		printf("Exception: %s \n", e);
	} catch (StyxLibraryException *e) {
		printf("Exception: %s (ac:%d)\n", e->getMessage().c_str(), e->getAdditionalCode());
	}
}

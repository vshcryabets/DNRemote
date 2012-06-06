/*
 * Main.cpp
 *
 *  Created on: May 20, 2012
 *      Author: mrco
 */
#include <string>
#include "StyxServerManager.h"
#include "vfs/MemoryStyxDirectory.h"
#include "MouseEventFiles.h"
#include "stdio.h"
using namespace std;

int main(int argc, char **argv) {
	try {
		string serveraddr = "127.0.0.1";
		MemoryStyxDirectory root ("root");
		string protocol = "9P2000";
		root.addFile(new MouseEventFiles());
		StyxServerManager manager(serveraddr, 8080, &root, &protocol);
		manager.start();
	} catch (const char *e) {
		printf("Exception: %s \n", e);
	}
}

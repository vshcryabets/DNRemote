/*
 * MouseEventFiles.cpp
 *
 *  Created on: Jun 6, 2012
 *      Author: mrco
 */

#include "MouseEventFiles.h"

#include "string.h"

MouseEventFiles::MouseEventFiles() :
	MemoryStyxFile("mouse"){
	mDisplay = XOpenDisplay(0);
	mRootWindow = DefaultRootWindow(mDisplay);
}

MouseEventFiles::~MouseEventFiles() {
	XCloseDisplay(mDisplay);
}
/**
 * Write data to file
 * @param client
 * @param data
 * @param offset
 * @return
 */
size_t MouseEventFiles::write(ClientState *client, uint8_t* data, uint64_t offset, size_t count) {
//	XEvent event;
//	memset(&event, 0, sizeof(event));
	XWarpPointer(mDisplay, None, mRootWindow, 0, 0, 0, 0, 100, 100);
	XFlush(mDisplay);
	return  count;
}

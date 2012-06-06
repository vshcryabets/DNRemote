/*
 * MouseEventFiles.cpp
 *
 *  Created on: Jun 6, 2012
 *      Author: mrco
 */

#include "MouseEventFiles.h"
#include "string.h"
#include "stdio.h"

MouseEventFiles::MouseEventFiles() :
	MemoryStyxFile("mouse"){
	mDisplay = XOpenDisplay(0);
	mRootWindow = DefaultRootWindow(mDisplay);
	XWindowAttributes attrs;
	XGetWindowAttributes(mDisplay, mRootWindow, &attrs);
	mWidth = attrs.width;
	mHeight = attrs.height;
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
	if ( count >= 4 ) {
//		printf("1: %p, 2: %p, 3: %p, 4: %p",
//				data[0], data[1],
//				data[2], data[3]);
		uint16_t xpos = data[1]<<8 | data[0];
		uint16_t ypos = data[3]<<8 | data[2];
		int newX = mWidth*xpos/10000;
		int newY = mHeight*ypos/10000;
//		printf("DX: %p, DY: %p, RX: %d, RY: %d",
//				xpos, ypos,
//				newX, newY);
		XWarpPointer(mDisplay, None, mRootWindow, 0, 0, 0, 0, newX, newY);
		XFlush(mDisplay);
	}
	return  count;
}

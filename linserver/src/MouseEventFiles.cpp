/*
 * MouseEventFiles.cpp
 *
 *  Created on: Jun 6, 2012
 *      Author: vshcryabets@gmail.com
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
		uint16_t xpos = data[1]<<8 | data[0];
		uint16_t ypos = data[3]<<8 | data[2];
		if ( xpos <= 10000 ) {
			int newX = mWidth*xpos/10000;
			int newY = mHeight*ypos/10000;
			XWarpPointer(mDisplay, None, mRootWindow, 0, 0, 0, 0, newX, newY);
			XFlush(mDisplay);
		} else if ( xpos == 20000 ) {
			::printf("Click \n");
			// LMB click
			XEvent event;
			memset(&event, 0x00, sizeof(event));
			event.type = ButtonPress;
			event.xbutton.type = ButtonPress;
			event.xbutton.button = Button1;
			event.xbutton.same_screen = true;

			XQueryPointer(mDisplay,
					mRootWindow,
					&event.xbutton.root,
					&event.xbutton.window,
					&event.xbutton.x_root,
					&event.xbutton.y_root,
					&event.xbutton.x,
					&event.xbutton.y,
					&event.xbutton.state);
			event.xbutton.subwindow = event.xbutton.window;

			/* walk down through window hierachy to find youngest child */
			while (event.xbutton.subwindow) {
				event.xbutton.window = event.xbutton.subwindow;
				XQueryPointer(mDisplay, event.xbutton.window,
						&event.xbutton.root, &event.xbutton.subwindow,
						&event.xbutton.x_root, &event.xbutton.y_root,
						&event.xbutton.x, &event.xbutton.y,
						&event.xbutton.state);
			}

			/* send ButtonPress event to youngest child */
			if (XSendEvent(mDisplay, PointerWindow, True, 0xfff, &event) == 0)
				printf("XSendEvent Failed\n");
			XFlush(mDisplay);


			/* sleep for a little while */
			usleep(50000);


			/* set up ButtonRelease event */
			event.type = ButtonRelease;
			event.xbutton.type = ButtonRelease;
			event.xbutton.state = 0x100;

			/* send ButtonRelease event to youngest child */
			if (XSendEvent(mDisplay, PointerWindow, True, 0xfff, &event) == 0)
				printf("XSendEvent Failed\n");
			XFlush(mDisplay);

		}
	}
	return  count;
}

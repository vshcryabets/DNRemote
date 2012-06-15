/*
 * MouseEventFiles.cpp
 *
 *  Created on: Jun 6, 2012
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

#include "MouseEventFiles.h"
#include "string.h"
#include "stdio.h"
#include "EventStruct.h"
#include "StyxErrorMessageException.h"

using namespace dnremote;

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
		EventTypeEnum type = (EventTypeEnum)data[0];
//		printf("Got event %d, %d\n", type, offset);
		switch (type) {
		case POINTER_EVENT:{
//			printf("Pointer event\n");
			PointerEventStruct event;
			loadPointerEvent(data+1, count-1, &event);
			processPointerEvent(&event);
		}
		break;
		case HOT_KEY_COMMAND:
			break;
		}
	}
	return  count;
}

/**
 * Process pointer event
 */
void MouseEventFiles::loadPointerEvent(uint8_t* data, size_t count, PointerEventStruct *event) {
	if ( count != 9 ) {
		// wrong event message
		throw new StyxErrorMessageException(
				"Wrong event message size. PointerEvent size should be 9 bytes.");
	}
	event->mPointerID = data[0];
	uint8_t type = data[1];
	PointerEventTypeEnum eventType = (PointerEventTypeEnum)type;
	event->mPointerEventType = eventType;
	event->mRelative = data[2];
	event->mX = (data[3] << 8) | data[4];
	event->mY = (data[5] << 8) | data[6];
//	printf("Event %dx%d\n", event->mX, event->mY);
	event->mButtonID = (data[7] << 8) | data[8];
	return;
}

/**
 * Handle pointer event
 */
void MouseEventFiles::processPointerEvent(PointerEventStruct *event) {
	XEvent xevent;
	memset(&xevent, 0x00, sizeof(xevent));
	switch (event->mPointerEventType ) {
	case MOVE:

		if ( !event->mRelative) {
//			printf("Abs.Move event %dx%d\n", event->mX, event->mY);
			int newX = mWidth*event->mX/10000;
			int newY = mHeight*event->mY/10000;
			XWarpPointer(mDisplay, None, mRootWindow, 0, 0, 0, 0, newX, newY);
			XFlush(mDisplay);
		} else {
			// relative move
		}
		break;
	case POINTER_DOWN:
		::printf("Down \n");
		// LMB click
		xevent.type = ButtonPress;
		xevent.xbutton.type = ButtonPress;
		xevent.xbutton.button = Button1;
		xevent.xbutton.same_screen = true;

		XQueryPointer(mDisplay,
				mRootWindow,
				&xevent.xbutton.root,
				&xevent.xbutton.window,
				&xevent.xbutton.x_root,
				&xevent.xbutton.y_root,
				&xevent.xbutton.x,
				&xevent.xbutton.y,
				&xevent.xbutton.state);
		xevent.xbutton.subwindow = xevent.xbutton.window;

		// walk down through window hierachy to find youngest child
		while (xevent.xbutton.subwindow) {
			xevent.xbutton.window = xevent.xbutton.subwindow;
			XQueryPointer(mDisplay, xevent.xbutton.window,
					&xevent.xbutton.root, &xevent.xbutton.subwindow,
					&xevent.xbutton.x_root, &xevent.xbutton.y_root,
					&xevent.xbutton.x, &xevent.xbutton.y,
					&xevent.xbutton.state);
		}

		// send ButtonPress event to youngest child
		if (XSendEvent(mDisplay, PointerWindow, True, 0xfff, &xevent) == 0)
			printf("XSendEvent Failed\n");
		XFlush(mDisplay);
		break;
	case POINTER_UP:
		::printf("Up \n");
		// LMB click

		xevent.type = ButtonRelease;
		xevent.xbutton.type = ButtonRelease;
		xevent.xbutton.button = Button1;
		xevent.xbutton.same_screen = true;
		xevent.xbutton.state = 0x100;

		XQueryPointer(mDisplay,
				mRootWindow,
				&xevent.xbutton.root,
				&xevent.xbutton.window,
				&xevent.xbutton.x_root,
				&xevent.xbutton.y_root,
				&xevent.xbutton.x,
				&xevent.xbutton.y,
				&xevent.xbutton.state);
		xevent.xbutton.subwindow = xevent.xbutton.window;

		// walk down through window hierachy to find youngest child
		while (xevent.xbutton.subwindow) {
			xevent.xbutton.window = xevent.xbutton.subwindow;
			XQueryPointer(mDisplay, xevent.xbutton.window,
					&xevent.xbutton.root, &xevent.xbutton.subwindow,
					&xevent.xbutton.x_root, &xevent.xbutton.y_root,
					&xevent.xbutton.x, &xevent.xbutton.y,
					&xevent.xbutton.state);
		}

		// send ButtonPress event to youngest child
		if (XSendEvent(mDisplay, PointerWindow, True, 0xfff, &xevent) == 0)
			printf("XSendEvent Failed\n");
		XFlush(mDisplay);		break;
	case POINTER_CLICK:
		break;
	}
}

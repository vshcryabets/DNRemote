/*
 * MouseEventFiles.cpp
 *
 *  Created on: Jul 3, 2012
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

MouseEventFiles::MouseEventFiles() : MemoryStyxFile("mouse"){
}

MouseEventFiles::~MouseEventFiles() {
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
			processPointerEventWin32(&event);
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
	event->mX = (short)(data[3] << 8) | data[4];
	event->mY = (short)(data[5] << 8) | data[6];
	//	printf("Event %dx%d\n", event->mX, event->mY);
	event->mButtonID = (data[7] << 8) | data[8];
	return;
}

/**
 * Handle pointer event
 */
void MouseEventFiles::processPointerEventWin32(PointerEventStruct *event) {
	switch (event->mPointerEventType ) {
	case MOVE:
		if ( !event->mRelative) {
			int newX = mWidth*event->mX/10000;
			int newY = mHeight*event->mY/10000;
			//XWarpPointer(mDisplay, None, mRootWindow, 0, 0, 0, 0, newX, newY);
		} else {
			long fScreenWidth	    = GetSystemMetrics( SM_CXSCREEN ) - 1; 
			long fScreenHeight	    = GetSystemMetrics( SM_CYSCREEN ) - 1; 
			// http://msdn.microsoft.com/en-us/library/ms646260(VS.85).aspx
			// If MOUSEEVENTF_ABSOLUTE value is specified, dx and dy contain normalized absolute coordinates between 0 and 65,535.
			// The event procedure maps these coordinates onto the display surface.
			// Coordinate (0,0) maps onto the upper-left corner of the display surface, (65535,65535) maps onto the lower-right corner.
			//float fx		        = mp.x * ( 65535.0f / fScreenWidth  );
			//float fy		        = mp.y * ( 65535.0f / fScreenHeight );		  
				
			INPUT Input             = { 0 };			
			Input.type		        = INPUT_MOUSE;
			Input.mi.dwFlags	    = MOUSEEVENTF_MOVE;
			Input.mi.dx		        = event->mX;
			Input.mi.dy		        = event->mY;

			SendInput(1,&Input,sizeof(INPUT));
			// relative move
			//mouse_event(MOUSEEVENTF_MOVE, event->mX, event->mY, 0, 0);
		}
		break;
	case POINTER_DOWN:
		mouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0);
		break;
	case POINTER_UP:
		mouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0);
		break;
	case POINTER_CLICK:
		break;
	}
}

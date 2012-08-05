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
#include "ApplicationServices/ApplicationServices.h"

using namespace dnremote;

MouseEventFiles::MouseEventFiles() : MemoryStyxFile("mouse"){
	//  TODO get display size
	mWidth = 1000;
	mHeight = 1000;
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
			processPointerEventGC(&event);
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
void MouseEventFiles::processPointerEventGC(PointerEventStruct *event) {

	switch (event->mPointerEventType ) {
	case MOVE:
		if ( !event->mRelative) {
			int newX = mWidth*event->mX/10000;
			int newY = mHeight*event->mY/10000;
			// TODO implement this
		} else {
			// relative move
		    CGEventRef move = CGEventCreateMouseEvent(
		        NULL, kCGEventMouseMoved,
		        CGPointMake(event->mX, event->mY),
		        kCGMouseButtonLeft // ignored
		    );
		    CGEventPost(kCGHIDEventTap, move);
		    CFRelease(move);
		}
		break;
	case POINTER_DOWN:
		// TODO implement this
		break;
	case POINTER_UP:
		// TODO implement this
		break;
	case POINTER_CLICK:
		// TODO implement this
		break;
	}
}

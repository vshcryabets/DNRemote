/*
 * MouseEventFiles.cpp
 *
 *  Created on: Jun 13, 2012
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

#ifndef EVENTSTRUCT_H_
#define EVENTSTRUCT_H_
#include <stdint.h>

namespace dnremote {

enum EventTypeEnum {
	POINTER_EVENT = 1,
	KEYBOARD_EVENT = 2,
	SPECIAL_KEY = 3,
	HOT_KEY_COMMAND = 4
};

enum PointerEventTypeEnum {
	MOVE = 1,
	POINTER_DOWN = 2,
	POINTER_UP = 3,
	POINTER_CLICK = 4
};

enum KeyEventTypeEnum {
	KEY_CLICK = 1,
	KEY_DOWN = 2,
	KEY_UP = 3
};

struct PointerEventStruct {
	uint8_t mPointerID;
	PointerEventTypeEnum mPointerEventType;
	bool mRelative;
	int16_t mX, mY;
	uint16_t mButtonID;
};

struct KeyboardEventStruct {
	KeyEventTypeEnum mKeyEventType;
	uint32_t mKeyID;
};


union EventStruct {
	EventTypeEnum	mEventType;
	PointerEventStruct mPointerEvent;
	KeyboardEventStruct mKeyEvent;
	KeyboardEventStruct mHotKeyEvent;
	KeyboardEventStruct mSpecialKeyEvent;
};

} /* namespace dnremote */
#endif /* EVENTSTRUCT_H_ */

/*
 * EventStruct.h
 *
 *  Created on: Jun 13, 2012
 *      Author: mrco
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
	uint16_t mX, mY;
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

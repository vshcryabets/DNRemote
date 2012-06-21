/*
 * MouseEventFiles.h
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

#ifndef MOUSEEVENTFILES_H_
#define MOUSEEVENTFILES_H_

#include "vfs/MemoryStyxFile.h"
#include "X11/Xlib.h"
#include "X11/Xutil.h"
#include "EventStruct.h"

namespace dnremote {
class MouseEventFiles: public MemoryStyxFile {
private:
	Display *mDisplay;
	Window mRootWindow;
	int mWidth, mHeight;
	/**
	 * Load pointer event
	 */
	void loadPointerEvent(uint8_t* data, size_t count, PointerEventStruct *event);
	/**
	 * Handle pointer event
	 */
	void processPointerEventXLIB(PointerEventStruct *event);
public:
	MouseEventFiles();
	virtual ~MouseEventFiles();
	/**
	 * Write data to file
	 * @param client
	 * @param data
	 * @param offset
	 * @return
	 */
	virtual size_t write(ClientState *client, uint8_t* data, uint64_t offset, size_t count);
};
}
#endif /* MOUSEEVENTFILES_H_ */

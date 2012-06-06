/*
 * MouseEventFiles.h
 *
 *  Created on: Jun 6, 2012
 *      Author: mrco
 */

#ifndef MOUSEEVENTFILES_H_
#define MOUSEEVENTFILES_H_

#include "./vfs/MemoryStyxFile.h"
#include "X11/Xlib.h"
#include "X11/Xutil.h"

class MouseEventFiles: public MemoryStyxFile {
private:
	Display *mDisplay;
	Window mRootWindow;
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

#endif /* MOUSEEVENTFILES_H_ */

/*
 * UdpListener.cpp
 * Created on: July 13, 2012
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
#include "stdio.h"
#include "unistd.h"
#include "sys/types.h"
#include "sys/socket.h"
#include "string.h"
#include "netinet/in.h"
#include "netinet/ip.h"
#include "errno.h"
#include "unistd.h"
bool stopUDPThread;
const char* gRequest = "DNRemoteServer?";

void stopListenUDP() {
    stopUDPThread = true;
}

void* listenUDP(void* arg) {
    stopUDPThread = false;
    char answerBuffer[1024];
    char preBuffer[1024];
    gethostname(preBuffer, sizeof(preBuffer));
    sprintf(answerBuffer,"{\"server\":\"DNR\", \"hostname\":\"%s\"}", preBuffer);
    int sockFD = -1;
    try {
	printf("Answer: %s\n", answerBuffer);
	int requestLength = strlen(gRequest);
	int answerLength = strlen(answerBuffer);
	// create UDP socket
	sockFD = socket(AF_INET, SOCK_DGRAM, 0);
	if ( sockFD < 0 ) {
	    throw "Can't create UDP socket";
	}
	struct sockaddr_in serv;
	memset(&serv, 0, sizeof(serv));
	serv.sin_family = AF_INET;
	serv.sin_addr.s_addr = htonl(INADDR_ANY);
	serv.sin_port = htons(1235);
	// bind
	int res = bind(sockFD, (struct sockaddr*)&serv, sizeof(serv));
	if ( res != 0 ) {
	    throw "Can't bind socket";
	}
	socklen_t sock_len = sizeof(serv);
	while ( !stopUDPThread ) {
	    char buffer[1024];
	    // read client
	    ssize_t read = recvfrom(sockFD, buffer, sizeof(buffer)-1, 0, 
		(struct sockaddr*) &serv, &sock_len);
	    // answer
	    buffer[read] = 0;
	    printf("Recv=%s, %d, %d\n", buffer, read, requestLength);
	    if ( read == requestLength && ( strncmp(buffer, gRequest, requestLength) == 0 ) ) {
		printf("Send answer\n");
		sendto(sockFD, answerBuffer, answerLength, 0, (struct sockaddr*) &serv, sock_len );
	    }
	}
    } catch (const char *e) {
    	printf("Exception: %s (%d)\n", e, errno);
    }
    if ( sockFD != -1 ) {
	close(sockFD);
    }
}
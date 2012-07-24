CMAKE_MINIMUM_REQUIRED(VERSION 2.6)

PROJECT(DNRemoteServer)

INCLUDE_DIRECTORIES( ../server/libs/V2StyxLib/native/V2StyxLib/include ../server/libs/jsoncpp/include )

ADD_DEFINITIONS(-g -O0 -fsigned-char -freg-struct-return -Wall)

ADD_SUBDIRECTORY( src bin )
ADD_SUBDIRECTORY( ../server/libs/V2StyxLib/native/V2StyxLib/lib bin )
ADD_SUBDIRECTORY( ../server/libs/jsoncpp/src/lib_json bin )

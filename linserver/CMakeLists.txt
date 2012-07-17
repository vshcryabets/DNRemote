CMAKE_MINIMUM_REQUIRED(VERSION 2.6)

PROJECT(DNRemoteServer)

INCLUDE_DIRECTORIES( libs/V2StyxLib/native/V2StyxLib/include libs/jsoncpp/include )

ADD_DEFINITIONS(-g -O0 -fsigned-char -freg-struct-return -Wall)

ADD_SUBDIRECTORY( src bin )
ADD_SUBDIRECTORY( libs/V2StyxLib/native/V2StyxLib/lib bin )
ADD_SUBDIRECTORY( libs/jsoncpp/src/lib_json bin )

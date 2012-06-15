CMAKE_MINIMUM_REQUIRED(VERSION 2.6)

PROJECT(DNRemoteServer)
INCLUDE_DIRECTORIES( V2StyxLib/native/V2StyxLib/include lib) 
ADD_DEFINITIONS(-g -O0 -fsigned-char -freg-struct-return -Wall)
# -W -Wshadow -Wpointer-arith -Wcast-qual -Winline -Werror)

ADD_DEFINITIONS(-g -O2 -Wall)
ADD_SUBDIRECTORY( src bin )
ADD_SUBDIRECTORY( V2StyxLib/native/V2StyxLib/lib bin )

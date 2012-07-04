CMAKE_MINIMUM_REQUIRED(VERSION 2.6)

PROJECT(DNRemoteServer)
INCLUDE_DIRECTORIES( V2StyxLib/native/V2StyxLib/include lib) 
ADD_DEFINITIONS()
# -W -Wshadow -Wpointer-arith -Wcast-qual -Winline -Werror)

ADD_DEFINITIONS()
ADD_SUBDIRECTORY( src bin )
ADD_SUBDIRECTORY( V2StyxLib/native/V2StyxLib/lib bin )

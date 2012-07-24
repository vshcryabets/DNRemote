CMAKE_MINIMUM_REQUIRED(VERSION 2.6)

PROJECT(jsoncpp)
INCLUDE_DIRECTORIES(include) 
ADD_DEFINITIONS(-g -O2 -fsigned-char -freg-struct-return -Wall)

ADD_SUBDIRECTORY( src/lib_json bin )

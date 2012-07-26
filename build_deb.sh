#!/bin/bash

cd linserver
make clean
cmake . -DCMAKE_INSTALL_PREFIX=/usr
make
fakeroot debian/rules clean
debian/rules build
fakeroot debian/rules binary

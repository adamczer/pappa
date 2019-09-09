#!/usr/bin/env bash
cd target/classes
javah ub.cse.juav.ardupilot.ArdupilotBridge
mkdir ../../native || true
mkdir ../../native/includes || true
cp *.h ../../native/includes/

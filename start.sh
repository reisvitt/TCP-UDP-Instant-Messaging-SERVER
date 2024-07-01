#!/bin/bash

find ./ -type f -name "*.class" -exec rm -f {} +

javac Principal.java

java Principal;find ./ -type f -name "*.class" -exec rm -f {} +

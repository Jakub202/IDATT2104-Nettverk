#!/bin/bash

# Compile the source code passed as an argument to the g++ command
echo "$1" > main.cpp
g++ main.cpp -o main

# Execute the compiled executable
./main

#!/bin/bash

# This script needs two arguments:
# The directory to be processed
# The directory where to send your results

function traverse() {
mkdir -p "$2/$1"
echo "Creating directory: $2/$1"
echo
for file in "$1"/*
do
    if [ ! -d "${file}" ] ; then
        echo "${file} is a file"
        echo "Saving to $2/${file}"
        cat "${file}" | ./text-cleaner | ./stop-word-remover | ./stem-remover > "$2/${file}"
        echo
    else
        echo "entering recursion with: ${file}"
        traverse "${file}" "$2"
    fi
done
}

function main() {
    traverse "$1" "$2"
}

main "$1" "$2"


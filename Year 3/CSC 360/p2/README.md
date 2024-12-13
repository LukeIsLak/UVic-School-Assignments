## P2
This is the gitlab repository for P2 for UVic CSC 360 Fall 2024 

## Description
This is a project that allows us to simulate scheduling by using multithreading and the pthread library.

As outlined in the P2 Assignment Description, this simulate a automated control system for a railway track.

From the P2 Description:
"There are two stations on each side of the main track. The trains on the main track have two priorities: high and low.
At each station, one or more trains are loaded with commodities. Each train starts its loading process at a common start time 0 of the simulation program. Only one train can be on the main track at any given time. After a train finishes crossing, it magically disappears."

## Cloning the Repo

To clone the repo, you need to execute the following commands

```
cd existing_repo
git remote add origin https://gitlab.csc.uvic.ca/courses/2024091/CSC360_COSI/assignments/lukekuligowicz/p2.git
git branch -M main
git push -uf origin main
```

## Usage
To initialize the project, use a MAKE commander
```
make
```

You will generate a mts.o file. To run this file, you should have a filepath to a .txt file.
For example, if you had a file called input.txt, you should run the following command.

```
./mts input.txt
```

MTS is the main executable, it takes a filepath to a .txt file and while it does not output anything to the standard output, it
does output to a "output.txt"

To clean the project, run the following command in your terminal
```
make clean
```

## Project status
MTS is complete

## Sample Input Output
The following input.txt uses this input case (found from tutorial 7 slides)

```
e 5 1
w 1 6
W 2 1
W 3 1
```

The following output of the code will look like if the command
```
./mts input.txt
```
was run

```
00:00:00.1 Train  1 is ready to go West
00:00:00.1 Train  1 is ON the main track going West
00:00:00.2 Train  2 is ready to go West
00:00:00.3 Train  3 is ready to go West
00:00:00.5 Train  0 is ready to go East
00:00:00.7 Train  1 is OFF the main track after going West
00:00:00.7 Train  2 is ON the main track going West
00:00:00.8 Train  2 is OFF the main track after going West
00:00:00.8 Train  0 is ON the main track going East
00:00:00.9 Train  0 is OFF the main track after going East
00:00:00.9 Train  3 is ON the main track going West
00:00:01.0 Train  3 is OFF the main track after going West
```

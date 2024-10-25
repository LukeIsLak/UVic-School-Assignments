#!/bin/bash
cat pa-tests/input01.txt | ./pa-tester.py | diff pa-tests/output01.txt -
cat pa-tests/input02.txt | ./pa-tester.py | diff pa-tests/output02.txt -
cat pa-tests/input03.txt | ./pa-tester.py | diff pa-tests/output03.txt -
cat pa-tests/input04.txt | ./pa-tester.py | diff pa-tests/output04.txt -
cat pa-tests/input05.txt | ./pa-tester.py | diff pa-tests/output05.txt -
cat pa-tests/input06.txt | ./pa-tester.py | diff pa-tests/output06.txt -
cat pa-tests/input07.txt | ./pa-tester.py | diff pa-tests/output07.txt -
cat pa-tests/input08.txt | ./pa-tester.py | diff pa-tests/output08.txt -


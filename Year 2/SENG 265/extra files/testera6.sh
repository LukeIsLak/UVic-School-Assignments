#!/bin/bash

echo -ne "Test 1 [\033[1;97msearch.py(input01/query01.txt)\033[0m and \033[1;97moutput01/ranking01.txt\033[0m]:  "
if diff -y search-tests/output01/ranking01.txt <(cat search-tests/input01/query01.txt | ./search.py search-tests/index01) >temp; then
  echo -e "\033[1;92m10 points - PASS\033[0m"
else
  echo -e "\033[1;91m10 points - FAIL\033[0m"
  cat temp
fi

echo -ne "Test 2 [\033[1;97msearch.py(input01/query02.txt)\033[0m and \033[1;97moutput01/ranking02.txt\033[0m]:  "
if diff -y search-tests/output01/ranking02.txt <(cat search-tests/input01/query02.txt | ./search.py search-tests/index01) >temp; then
  echo -e "\033[1;92m10 points - PASS\033[0m"
else
  echo -e "\033[1;91m10 points - FAIL\033[0m"
  cat temp
fi

echo -ne "Test 3 [\033[1;97msearch.py(input01/query03.txt)\033[0m and \033[1;97moutput01/ranking03.txt\033[0m]:  "
if diff -y search-tests/output01/ranking03.txt <(cat search-tests/input01/query03.txt | ./search.py search-tests/index01) >temp; then
  echo -e "\033[1;92m10 points - PASS\033[0m"
else
  echo -e "\033[1;91m10 points - FAIL\033[0m"
  cat temp
fi

echo -ne "Test 4 [\033[1;97msearch.py(input02/query04.txt)\033[0m and \033[1;97moutput02/ranking04.txt\033[0m]:  "
if diff -y search-tests/output02/ranking04.txt <(cat search-tests/input02/query04.txt | ./search.py search-tests/index02) >temp; then
  echo -e "\033[1;92m10 points - PASS\033[0m"
else
  echo -e "\033[1;91m10 points - FAIL\033[0m"
  cat temp
fi

echo -ne "Test 5 [\033[1;97msearch.py(input02/query05.txt)\033[0m and \033[1;97moutput02/ranking05.txt\033[0m]:  "
if diff -y search-tests/output02/ranking05.txt <(cat search-tests/input02/query05.txt | ./search.py search-tests/index02) >temp; then
  echo -e "\033[1;92m10 points - PASS\033[0m"
else
  echo -e "\033[1;91m10 points - FAIL\033[0m"
  cat temp
fi

echo -ne "Test 6 [\033[1;97msearch.py(input02/query06.txt)\033[0m and \033[1;97moutput02/ranking06.txt\033[0m]:  "
if diff -y search-tests/output02/ranking06.txt <(cat search-tests/input02/query06.txt | ./search.py search-tests/index02) >temp; then
  echo -e "\033[1;92m10 points - PASS\033[0m"
else
  echo -e "\033[1;91m10 points - FAIL\033[0m"
  cat temp
fi

echo -ne "Test 7 [\033[1;97msearch.py(input02/query07.txt)\033[0m and \033[1;97moutput02/ranking07.txt\033[0m]:  "
if diff -y search-tests/output02/ranking07.txt <(cat search-tests/input02/query07.txt | ./search.py search-tests/index02) >temp; then
  echo -e "\033[1;92m10 points - PASS\033[0m"
else
  echo -e "\033[1;91m10 points - FAIL\033[0m"
  cat temp
fi

rm temp

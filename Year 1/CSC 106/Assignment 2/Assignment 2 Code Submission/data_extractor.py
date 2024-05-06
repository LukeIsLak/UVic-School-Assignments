import csv
fileName = "data.csv"

#grabs all elements contained within a number; might possibly make more efficient the larger it goes up
def elementData(num):
    #creates an array, and adds the current number to it
    allDataPoints = []
    allDataPoints.append(str(num))

    #loops though the currElement
    currElement = num
    while currElement != 1:
        #we want to see if it is even, if it is, we divide it by 2
        if (currElement % 2) == 0:
            currElement = currElement // 2

        #if the number is odd, we apply the formula 3x + 1
        else:
            currElement = 3*currElement + 1

        #append the new element
        allDataPoints.append(str(currElement))
    #return the list of elements we created
    return allDataPoints

#create a csv file for our data
def createElementsArray(totalNums):

    allElementsArray = []

    #creates new datasets for each number we want to do
    for index in range(totalNums):
        allElementsArray.append(elementData(index + 1))

    #open a file
    file = open(fileName, 'w')

    #for each of our data sets, we want to set a row to the elements
    for row in allElementsArray:
        dataSetText = ",".join(row) + "\n"
        file.write(dataSetText)

    #close the file
    file.close()

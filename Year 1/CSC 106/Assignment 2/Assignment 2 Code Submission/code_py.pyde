import data_extractor as de


#I know this goes against all coding conventions, although it was easier to manage in my case
filesToEv = ['data.csv']

#values related to the screen size
wHeight = 900
wWidth = 1600
leftMargin = 100
rightMargin = 50
bottomMargin = 100
topMargin = 50

#values related to the graph size
graphWidth = wWidth - leftMargin - rightMargin
graphHeight = wHeight - bottomMargin - topMargin

#values for relativity
widthRelativity = 0;
heightRelativity = 0;
txSize = 13

#how fast do we want to draw the screen
fps = 10
mouseButtonPressed = False

#data regarding our information
data = []
avg = []
avgIt = []

#I really like 26, 27, 100, and 50 as examples
dataIterations = 20

#set up the screen
def setup ():
    #set up the screen screen
    size (wWidth, wHeight)
    background(30, 30, 75)
    smooth()
    
    #draw the lines for the graph
    line(leftMargin, wHeight - bottomMargin, wWidth - rightMargin, wHeight - bottomMargin)
    line(leftMargin, topMargin, leftMargin, wHeight - bottomMargin)
    
    stroke(40, 40, 85)
    fill(40, 40, 85)
    rect(leftMargin, topMargin, wWidth-rightMargin, wHeight - bottomMargin-topMargin)
    
    textSize(txSize+4)
    fill(200, 200, 255, 255)
    text(str(dataIterations), leftMargin / 2, leftMargin / 2)
    
    
    #now initialize the data
    initializeData(filesToEv)

#reads and converts the file into an array
def evaluateData(filename):
    #open the file
    file = open(filename, 'r')
    #for each line, if its not empty
    fileLine = file.readline()
    listDataElements = []
    while fileLine != '':
        #grab all the cells and put them into an array
        element = fileLine[:len(fileLine)].split(',')
        dataElements = []
        
        #for each element, turn it into an number and add it to a array
        index = 0
        for number in element:
            dataElements.append(int(number))
            global avg
            if (len(avg) <= index):
                avg.append(int(number))
                avgIt.append(1)
            else:
                avg[index] += int(number)
                avgIt[index] += 1
            index += 1
        #add that array to a larger array
        listDataElements.append(dataElements)
        
        #continue to the next line
        fileLine = file.readline()
    
    #close the file and return the data
    file.close()
    print('test')
    return listDataElements

#evalute how the data should be condensed into the graph
def evaluateRelativity(dataPoints):
    maxWidth = 0;
    maxHeight = 0;
    
    #for each point of data, grab the largest length and the largest stand alone value
    for i in range(len(dataPoints)):
        if len(dataPoints[i]) > maxWidth:
            maxWidth = len(dataPoints[i])
        for x in range(len(dataPoints[i])):
            if dataPoints[i][x] > maxHeight:
                maxHeight = dataPoints[i][x]
    
    return([maxWidth, maxHeight])

#draw all of the lines
def drawElements(elements):
    
    # "global" value for the index, and previous values to connect the line
    prevX = leftMargin
    prevY = topMargin+graphHeight
    index = 0
    
    #for each element for the starting number
    for element in elements:
        
        #figure out where it should be on screen
        posX = leftMargin+(index*widthRelativity)
        posY = wHeight-((element*heightRelativity))-bottomMargin
        
        #processing jargon to make it give me the values I want 
        ellipseMode(CENTER)
        textSize(txSize - 1)
        
        #for the line and point   
        stroke(255, 255, 255, 35)
        fill(255, 255, 255, 80)
        line(prevX, prevY, posX, posY)
        ellipse(posX, posY, 4, 4)
        
        #for the text
        fill(255, 255, 255, 100)
        text(str(index), posX, (topMargin + graphHeight+15))
        fill(30, 200, (element*heightRelativity), (element*heightRelativity)/1.4)
        text(str(element) + ' (' + str(elements[0]) + ',' + str(index) + ')', posX, posY)
        
        #update the "global" values
        index += 1
        prevX = posX
        prevY = posY

def drawAVG():
    
    #processing jargon to make it give me the values I want 
    ellipseMode(CENTER)
    textSize(txSize + 2)
    
    # "global" value for the index, and previous values to connect the line
    prevX = leftMargin
    prevY = topMargin+graphHeight
    index = 0
    #for the line and point   
    stroke(255, 0, 0, 255)
    fill(255, 0, 0, 150)
    
    #for each element for the starting number
    for element in avg:
        
        #figure out where it should be on screen
        posX = leftMargin+(index*widthRelativity)
        posY = wHeight-(((element/avgIt[index])*heightRelativity))-bottomMargin
        
        #for the line and point   
        line(prevX, prevY, posX, posY)
        ellipse(posX, posY, 4, 4)
        
        #for the text
        text(str(element), posX, posY, 20)
        
        #update the "global" values
        index += 1
        prevX = posX
        prevY = posY


#initialize the data
def initializeData(filenames):
    global widthRelativity
    global heightRelativity
    
    #create the data using the other script
    if ('data.csv' in filenames):
        de.createElementsArray(dataIterations)
    
    #for all the data files we want, read the data
    for filename in filenames:
        data.append(evaluateData(filename))
    
    #represents that it is running
    print(data)
    print(avg)
    
    #evauluate the relativity
    relativity = evaluateRelativity(data[0])
    widthRelativity = float(graphWidth)/float(relativity[0])
    heightRelativity = float(graphHeight)/float(relativity[1])

def draw():
    
    #will check to see if the left mouse button is pressed
    if (mousePressed == True and mouseButtonPressed == False):
        if (mouseButton == LEFT):
            global mouseButtonPressed
            mouseButtonPressed = True
    
    frameRate(fps)
  
    #draws the data              
    if (len(data[0]) > 0 and mouseButtonPressed):
        drawElements(data[0][0])
        data[0].remove(data[0][0])
        
    #draws the average and log if the right mouse button is pressed
    elif(len(avg) != 0 and mouseButton == RIGHT and mouseButtonPressed):
        drawAVG();
        global avg
        avg = []


         
         
    

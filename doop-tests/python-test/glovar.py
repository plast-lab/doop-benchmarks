globVar = "Foo"
globVar2 = int(3)

def printVar():
    print globVar + globVar2

def setGlob(x):
    global globVar2
    globVar2 = x

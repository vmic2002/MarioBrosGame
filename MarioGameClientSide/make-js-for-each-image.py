import os
x=100
i=1 
for image in os.listdir("Images"):
    print("{ id: '"+image[:len(image)-4]+"', path: 'Images/"+image+"', x: 100, y: "+str(x)+" },")
print(len(os.listdir("Images")))

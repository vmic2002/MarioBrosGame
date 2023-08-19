#python script to automate structural code of importing new images in
#mario game project
import os
 
# assign directory
directory = 'Images'
 
# iterate over files in
# that directory
for filename in os.scandir(directory):
    if filename.is_file():
        name=os.path.basename(filename.path)
        #print("String "+name[:len(name)-4]+'Path = prefix+imageDirectory+"/'+name+'";')
        #print("MyImage "+name[:len(name)-4]+" = null;")
        print(name[:len(name)-4]+" = new MyImage(ImageIO.read(new File("+name[:len(name)-4]+'Path)), "'+name[:len(name)-4]+'");');

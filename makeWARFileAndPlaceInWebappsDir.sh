#!/bin/bash
#to automate process of making .war file and putting it in webapps directory (script works!)
#this script removes all .java and .class files from MarioGameServerSide/src and MarioGameServerSide/WEB-INF/classes and replaces them with .java and .class files from src and bin. Then a .war file is created in the MarioGameServerSide directory which replaces the previous .war file. The new .war file replaces the one in the webapps directory of apache tomcat (if one exists)
#MarioGameServerSide is used to create the .war file
WEBAPPS_DIR="/Users/victormicha/Desktop/apache-tomcat-10.1.11/webapps"


echo "Making sure that MarioGameServerSide dir has the same number of images and sound effects as MarioBrosGame dir..."
x1=$(ls SoundEffects | wc -l)
x2=$(ls MarioGameServerSide/SoundEffects | wc -l)

y1=$(ls Images | wc -l)
y2=$(ls MarioGameServerSide/Images | wc -l)

if [ "$x1" != "$x2" ] || [ "$y1" != "$y2" ]; then
    echo "Please make sure that MarioGameServerSide dir has the same number of images and sound effects as MarioBrosGame dir!"
    exit
fi
echo "All good!"

rm MarioGameServerSide/src/*
rm MarioGameServerSide/WEB-INF/classes/*
cp src/* MarioGameServerSide/src
cp bin/* MarioGameServerSide/WEB-INF/classes
cd MarioGameServerSide
rm MarioGameServerSide.war
jar -cvf MarioGameServerSide.war *
cd ..
printf "\n-----------------------------------------------\n\n"
echo "MarioGameServerSide.war file created"

if [ -d $WEBAPPS_DIR ] 
then
    echo "Directory $WEBAPPS_DIR exists." 
else
    echo "Error: Directory $WEBAPPS_DIR does not exists."
    exit 1
fi

if [ -f "$WEBAPPS_DIR/MarioGameServerSide.war" ]
then
    echo ".war file already exists in webapps directory"
    rm $WEBAPPS_DIR/MarioGameServerSide.war
    rm -rf $WEBAPPS_DIR/MarioGameServerSide
    echo "Previous .war file replaced with new one"
else
    echo "No previous .war file exists in webapps directory"
    echo "New .war file copied in webapps directory"
fi
cp MarioGameServerSide/MarioGameServerSide.war $WEBAPPS_DIR
echo "$WEBAPPS_DIR contains new .war file!"

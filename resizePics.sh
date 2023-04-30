echo "Let's resize the pics!"
for i in Images/*.png; do
    name=$(basename $i)
    echo Resizing $name...
    convert $i -resize 200% ScaledImages/$name
done
echo Done! 

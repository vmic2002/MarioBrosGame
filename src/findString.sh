#this script prints out in which file(s) the
#inputed string is within the src dir
#example: ./findString.sh "new Thread" to know which files contain this string
if [ "$#" -ne 1 ]; then
    echo "One argument required";
    exit 1;
fi
for FILE in *; do
    x=$(cat $FILE | grep "$1" | wc -l);
    if [ $x -gt 0 ]; then
        echo $FILE;
    fi
done

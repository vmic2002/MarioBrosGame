#must be within MarioBrosGame directory
#this script prints out in which file(s) the
#inputed string is within the src dir
#with line numbers to make lines easier to find
#example: ./findString.sh "new Thread" to know which files contain this string
cd src
if [ "$#" -ne 1 ]; then
    echo "One argument required";
    exit 1;
fi
echo "Searching for all files in ./src containing \"$1\"...";
for FILE in *; do
    linesContainingString=$(cat $FILE | grep -n "$1");#contains line number, ex:"24:"
    numLinesContainingString=$(cat $FILE | grep "$1" | wc -l);
    if [ $numLinesContainingString -gt 0 ]; then
        echo "Filename: $FILE";#prints filename that contains string $1
        echo "_______________________________________\n";
        echo "$linesContainingString";
        echo "\n_______________________________________\n\n";
    fi
done
cd ..

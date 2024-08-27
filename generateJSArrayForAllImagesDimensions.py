#this file is to create a hardcoded dictionary that contains all the dimensions (width and height) of every image in Images directory so that the Javascript does not have to wait for an image to load to get its dimensions, instead it can just check the dictionary (total size of dictionary is ~1 to 2 KB)
# CREATES imageDimensions.js
#!!!!!!!!!!!!!!!!!REMEMBER TO REMOVE THE EXTRA , ON THE LAST LINE
from PIL import Image
import os

# Directory containing the images
image_dir = 'Images'

# Function to get image dimensions
def get_image_dimensions(filepath):
    with Image.open(filepath) as img:
        return img.width, img.height

# Function to generate JavaScript dictionary code
def generate_js_dictionary_code(image_dimensions):
    js_code = 'const imageDimensions = {\n'
    for filename, (width, height) in image_dimensions.items():
        filename = filename[:len(filename)-4]
        js_code += f'    "{filename}": {{ width: {width}, height: {height} }},\n'
    js_code += '};'
    return js_code

# Main function to scan the directory and print JavaScript code
def main():
    image_dimensions = {}
    
    for filename in os.listdir(image_dir):
        filepath = os.path.join(image_dir, filename)
        if os.path.isfile(filepath) and filename.lower().endswith(('.png', '.jpg', '.jpeg', '.gif')):
            width, height = get_image_dimensions(filepath)
            image_dimensions[filename] = (width, height)
    
    js_code = generate_js_dictionary_code(image_dimensions)
    print(js_code)

if __name__ == '__main__':
    main()


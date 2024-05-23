import random
import string
import os
import cv2
import numpy as np
from captcha.image import ImageCaptcha

# Define the path where CAPTCHA images will be saved
PATH = '/home/hr/Documents/Projects/TP Projects/deleteit/'

# Check if the directory exists, and create it if it doesn't
if not os.path.exists(PATH):
    os.makedirs(PATH)

def createImage(kn, wid, hei):
    random_string = ''.join(random.choices(string.digits + string.ascii_lowercase, k=kn))
    
    # Creating a CAPTCHA Image
    image_captcha = ImageCaptcha(width=wid, height=hei)
    image_generated = image_captcha.generate(random_string)
    token = ''.join(random.choices(string.ascii_lowercase + string.digits, k=16))
    
    # Saves the CAPTCHA
    captcha_filename = f"{random_string}_{token}.png"
    captcha_path = os.path.join(PATH, captcha_filename)
    image_captcha.write(random_string, captcha_path)
    print(f"Generated CAPTCHA saved to: {captcha_path}")

try:
    num = int(input("Number of CAPTCHAs to generate: "))
    kn = int(input("Number of characters in CAPTCHA: "))
    hei = int(input("Height of the image: "))
    wid = int(input("Width of the image: "))
except ValueError:
    print("Please enter valid integer values.")
    exit(1)

for i in range(num):
    createImage(kn, wid, hei)

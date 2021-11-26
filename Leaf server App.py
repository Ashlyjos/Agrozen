import numpy as np
import pandas as pd
import requests
import cv2
import datetime
from sklearn.preprocessing import LabelBinarizer
from keras.preprocessing.image import img_to_array
import urllib.request
import time
#load_model = keras.models.load_model('model_new.h5')
from keras.models import load_model
model = load_model('molel_new.h5')

default_image_size = tuple((256, 256))
image_size = 0
width=256
height=256
depth=3

label_list=['Pepper__bell___Bacterial_spot','Pepper__bell___healthy',
            'Potato___Early_blight','Potato___healthy','Potato___Late_blight',
            'Tomato_Bacterial_spot','Tomato_Early_blight','Tomato_healthy',
            'Tomato_Late_blight','Tomato_Leaf_Mold','Tomato_Septoria_leaf_spot',
            'Tomato_Spider_mites_Two_spotted_spider_mite','Tomato__Target_Spot',
            'Tomato__Tomato_mosaic_virus','Tomato__Tomato_YellowLeaf__Curl_Virus']


def convert_image_to_array(image_dir):
    try:
        image = cv2.imread(image_dir)
        if image is not None :
            image = cv2.resize(image, default_image_size)   
            return img_to_array(image)
        else :
            return np.array([])
    except Exception as e:
        print("Error in Image : {}".format(e))
        return None



healthy=['Pepper__bell___healthy','Potato___healthy','Tomato_healthy']

unhealthy=['Pepper__bell___Bacterial_spot','Pepper__bell___healthy','Potato___Early_blight',
           'Potato___healthy','Potato___Late_blight','Tomato_Bacterial_spot','Tomato_Early_blight',
           'Tomato_healthy','Tomato_Late_blight','Tomato_Leaf_Mold','Tomato_Septoria_leaf_spot',
           'Tomato_Spider_mites_Two_spotted_spider_mite','Tomato__Target_Spot','Tomato__Tomato_mosaic_virus',
           'Tomato__Tomato_YellowLeaf__Curl_Virus']

predicted_list=[]

labelbinarizer = LabelBinarizer()

image_labels = labelbinarizer.fit_transform(label_list)


def disease_find_out(current_date_and_time,sec):
    try:
        
        hours_added = datetime.timedelta(days = 0,hours=0,minutes=0,seconds=sec-10)
        future_date_and_time = current_date_and_time + hours_added
        filename="leafImage-"+future_date_and_time.strftime("%Y.%m.%d-%H:%M:%S")+".jpg"
        
        image_url="https://jerryleaf.000webhostapp.com/leaf/pic/"+filename
        print(filename," searching.......... ")
        filesavename="leaf image "+str(sec)+".JPG"
        urllib.request.urlretrieve(image_url, filesavename)
        print(filename," .............saved ")
        
        imar = convert_image_to_array(filesavename) 
        npimagelist = np.array([imar], dtype=np.float16) / 225.0
        PREDICTEDCLASSES2 = model.predict_classes(npimagelist) 
        labelbinarizer = LabelBinarizer()
        image_labels = labelbinarizer.fit_transform(label_list)
        print (labelbinarizer.classes_[PREDICTEDCLASSES2])
        la=(labelbinarizer.classes_[PREDICTEDCLASSES2]).copy()
        lb=str(la[0])

        #d=a+b

        if lb != 'Pepper__bell___healthy' and lb != 'Potato___healthy' and lb != 'Tomato_healthy':
            uu="https://jerryleaf.000webhostapp.com/leaf/resultupload.php"
            para={"k1":"leaf affected","k2":image_url,"k3":lb}
            response=requests.get(url=uu,params=para)
            response.text
            print("Update successfully")
        else:
            uu="https://jerryleaf.000webhostapp.com/leaf/resultupload.php"
            para={"k1":"leaf not affected","k2":image_url,"k3":lb}
            response=requests.get(url=uu,params=para)
            response.text
            print("Update Successfully")
    except Exception as e:
        print(e)


sec=0
current_date_and_time = datetime.datetime.now()
while True:
    time.sleep(2)
    disease_find_out(current_date_and_time,sec)
    sec+=1
    

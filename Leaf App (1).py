from tkinter import *
import numpy as np
import pandas as pd
from PIL import Image,ImageTk
from tkinter import filedialog
import cv2

from sklearn.preprocessing import LabelBinarizer
from keras.preprocessing.image import img_to_array

#load_model = keras.models.load_model('model_new.h5')
from keras.models import load_model
load_model = load_model('molel_new.h5')

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


root = Tk()

root.title('Leaf Prediction')
root.geometry('1100x700')
root.resizable(False,False)
root.iconbitmap('icon.ico')

def openfilename():
  
    # open file dialog box to select image
    # The dialogue box has a title "Open"
    filename = filedialog.askopenfilename(title ="Select Image")
    return filename

def open_img():
    # Select the Imagename  from a folder 
    x = openfilename()
  
    # opens the image
    img = Image.open(x,'r')
      
    # resize the image and apply a high-quality down sampling filter
    img = img.resize((353, 310), Image.ANTIALIAS)
  
    # PhotoImage class is used to add image to widgets, icons etc
    img = ImageTk.PhotoImage(img)
   
    # create a label
    panel = Label(root, image = img)
      
    # set the image as img 
    panel.image = img
    panel.place(x=70,y=150)
    
    return x


def pred_leaf():
    
    
    image_directory = open_img()
    print(image_directory)
    if image_directory.endswith(".jpg") == True or image_directory.endswith(".JPG") == True or image_directory.endswith(".png") == True:
        img3 = image_directory
        img5 = cv2.imread(img3)
       
        
        imar = convert_image_to_array(img3)
        img4 = cv2.imread(img3)
        hsv = cv2.cvtColor(img4, cv2.COLOR_BGR2HSV)
        lower_range = np.array([10, 100, 20])
        upper_range = np.array([20, 255, 200])
        mask = cv2.inRange(hsv, lower_range, upper_range)
       
        
        
        npimagelist = np.array([imar], dtype=np.float16) / 225.
        PREDICTEDCLASSES2 = load_model.predict_classes(npimagelist)
        print(PREDICTEDCLASSES2)
        pr=labelbinarizer.classes_[PREDICTEDCLASSES2]
        
       
        print(pr)
        msg1.config(text=pr[0])
        
        
        if pr[0] in healthy:
            msg2.config(text=str("HEALTHY"))
            
        elif pr[0] in unhealthy:
            msg2.config(text=str("UNHEALTHY"))
            
       
        else:
            msg2.config(text=str("Unknown"))
        
        
        if 'Pepper' in pr[0]:
            msg3.config(text=str("PEPPER"))
            
        elif 'Potato' in pr[0]:
            msg3.config(text=str("POTATO"))
            
        elif 'Tomato' in pr[0]:
            msg3.config(text=str("TOMATO"))
            
       
        else:
            msg3.config(text=str("Unknown"))
            
        
       
 
    
lb1 = Label(root,text = 'Leaf Prediction',font=('calibri',25,'bold'), bg='grey',fg='white')
lb1.pack()

lb2 = Label(root,text = 'Predicted Plant Disease',font = ('calibri',18,'bold'))
lb2.place(x=610,y=350)

lb3 = Label(root,text = 'Leaf State',font = ('calibri',18,'bold'))
lb3.place(x=500,y=150)


lb4 = Label(root,text = 'Plant Type',font = ('calibri',18,'bold'))
lb4.place(x=495,y=250)


msg1 = Label(root,text='',font=('calibri',20),bg='#03a30e',fg='black',width=43)
msg1.place(x=463,y=400)

msg2 = Label(root,text='',font=('calibri',20),bg='#03a30e',fg='black',width=18)
msg2.place(x=610,y=150)



msg3 = Label(root,text='',font=('calibri',20),bg='#03a30e',fg='black',width=18)
msg3.place(x=610,y=250)




lb = Label(root,text='',bg='white',width=50,height=20)
lb.place(x=70,y=150)

btn1 = Button(root,text='UPLOAD',bg='#03a37e',fg='black',font=('calibri',10,'bold'),activebackground='#4d4d4d',activeforeground='white',width=12,height=2,command=pred_leaf) 
btn1.place(x=200,y=480)

root.mainloop()

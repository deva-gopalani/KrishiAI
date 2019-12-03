from picamera import PiCamera
from time import sleep
import requests
import json
import base64
 
camera = PiCamera()
i = 0
while True:
	i += 1
	camera.start_preview()
	sleep(5)
	camera.capture('/home/pi/Desktop/saved/image%s.jpg' % i)
	camera.stop_preview()
	sleep(60)
	URL = "http://devagopalani.pythonanywhere.com/pest"
	content_type = 'image/jpeg'
	headers = {'content-type': content_type}
	imgfile = '/home/pi/Desktop/saved/image'+str(i)+'.jpg'
	with open(imgfile, "rb") as imageFile:
		str = base64.b64encode(imageFile.read())
	# send http request with image and receive response
	response = requests.post(URL,str)
	print(response)
	print(json.loads(response.text))
	sleep(60)

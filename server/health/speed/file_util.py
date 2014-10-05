import time

def get_article(file_path):
	with open(file_path,"r") as article:
		text = article.read().replace('\n','')
	return text

def write_article(file_path,text):
	with open(file_path,"w") as article:
		article.write(text)

def time_stamp():
	return int(time.time())

print time_stamp()

write_article("./hello_world.txt","hello world!!\n sdsasdsdasdasd \n  hssshshshshshshs")
print get_article("./hello_world.txt")
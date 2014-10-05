from django.shortcuts import render
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth import authenticate
from models import *
from file_util import *
#from readability import parse_url
import json

# Create your views here.

def test_get(request):
	response_data = {'text':"hello world this is a test "}
	return HttpResponse(json.dumps(response_data),content_type="application/json")

def get_article(request,article_id):
	response_data = {'error':"invalid id"}
	try:
		article_id = int(article_id)
	except ValueError as e:
		return HttpResponse(json.dumps(response_data),content_type="application/json",status=400)

	if article_id == 0 or article_id > len(Article.objects.all()):
		return HttpResponse(json.dumps(response_data),content_type="application/json",status=400)

	article = Article.objects.get(id=article_id)
	if not article.has_text:
		response_data = {'error':"no content"}
		return HttpResponse(json.dumps(response_data),content_type="application/json",status=400)
	article_txt = article.text
	response_data = {'text':article_txt}
	return HttpResponse(json.dumps(response_data),content_type="application/json")

@csrf_exempt
def get_all_user_articles(request):
	if request.method == "GET":
		return HttpResponse(status=400)
	username = request.POST['username']
	password = request.POST.get('password',default="")

	user = authenticate(username=username,password=password)
	if user:
		profile = UserProfile.objects.get(user=user)
		articles = []
		response_data = {"articles":articles}
		for article in profile.articles.all():
			date_string = "{0}-{1}-{2}".format(article.date_uploaded.month,article.date_uploaded.day,article.date_uploaded.year)
			article_json = {"title":article.title,"text":article.text,"date":date_string}
			articles.append(article_json)
		return HttpResponse(json.dumps(response_data),content_type="application/json",status=200)
	else:
		response_data = {'error':"authentication failed"}
		return HttpResponse(json.dumps(response_data),content_type="application/json",status=400)


@csrf_exempt
def post_article(request):
	if request.method == "GET":
		return HttpResponse(status=400)
	username = request.POST['username']
	password = request.POST.get('password',default="")
	url      = request.POST['url']

	user = authenticate(username=username,password=password)
	if user:
		profile = UserProfile.objects.get(user=user)
		##text = parse_url(url)["content"]
		article = Article(title="some title",text="some text")
		article.has_text = True
		article.date_uploaded = datetime.now()
		article.save()
		profile.articles.add(article)
		profile.save()
		url = "{0}/api/v1/article/{1}/show.json".format(request.get_host(),article.id)
		response_data = {'url':url}
		return HttpResponse(json.dumps(response_data),content_type="application/json",status=200)
	
	response_data = {'error':"authentication failed"}
	return HttpResponse(json.dumps(response_data),content_type="application/json",status=400)

@csrf_exempt
def add_user(request):
	if request.method == "GET":
		return HttpResponse(status=400)
	username = request.POST['username']
	password = request.POST.get('password',default="")

	new_user = None
	try:
		new_user = User.objects.create_user(username,"",password)
		u1 = UserProfile(user=new_user)
	except Exception as e:
		if new_user:
			new_user.delete()
		response_data = {'error':"username already exists"}
		return HttpResponse(json.dumps(response_data),content_type="application/json")
	u1.save()
	response_data = {'error':"none"}
	return HttpResponse(json.dumps(response_data),content_type="application/json")



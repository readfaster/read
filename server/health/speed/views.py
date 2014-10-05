from django.shortcuts import render
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth import authenticate
from models import *
from readability import parse_url
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
def delete_article(request):
	if request.method == "GET":
		return HttpResponse(status=400)
	username = request.POST['username']
	password = request.POST.get('password',default="")
	article_id = request.POST['id']
	try:
		article_id = int(article_id)
	except ValueError as e:
		response_data = {'error':"invalid id, id is not a number"}
		return HttpResponse(json.dumps(response_data),content_type="application/json",status=400)
	article = None
	try:
		article = Article.objects.get(id=article_id)
		article.delete()
	except Exception as e:
		response_data = {'error':"invalid id, id not present"}
		return HttpResponse(json.dumps(response_data),content_type="application/json",status=400)

	response_data = {'text':"'{0}' deleted".format(article.title)}
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
			article_json = {"title":article.title,"text":article.text,"date":date_string,"id":article.id}
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
		content = parse_url(url)
		article = Article(title=content["title"],text=content["content"])
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



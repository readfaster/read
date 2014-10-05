from django.shortcuts import render
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth import authenticate
from models import *
import json

# Create your views here.
def test_get(request):
	response_data = {'text':"hello world this is a test "}
	return HttpResponse(json.dumps(response_data),content_type="application/json")

def get_article(request,article_id):
	response_data = {'text':"hello world this is a test of id "+ article_id}
	return HttpResponse(json.dumps(response_data),content_type="application/json")

@csrf_exempt
def post_article(request):
	if request.method == "GET":
		return HttpResponse(status=400)
	username = request.POST['username']
	password = request.POST['password']

	user = authenticate(username=username,password=password)
	if user:
		profile = UserProfile.objects.get(user=user)
		article = Article(title="some title",file_path="some file path")
		article.save()
		profile.articles.add(article)
		profile.save()
		t1 = profile.articles.all()[0].title
		response_data = {'text':"hello world this is a test of post " + t1}
		return HttpResponse(json.dumps(response_data),content_type="application/json",status=200)
	
	response_data = {'text':"content not added"}
	return HttpResponse(json.dumps(response_data),content_type="application/json",status=400)

@csrf_exempt
def add_user(request):
	if request.method == "GET":
		return HttpResponse(status=400)
	username = request.POST['username']
	password = request.POST['password']

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



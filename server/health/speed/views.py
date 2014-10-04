from django.shortcuts import render
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
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
	response_data = {'text':"hello world this is a test of post"}
	return HttpResponse(json.dumps(response_data),content_type="application/json")
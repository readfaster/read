from django.shortcuts import render
from django.http import HttpResponse
import json

# Create your views here.
def get_request(request,file_path):
	response_data = {'text':"hello world this is a test "+file_path}
	return HttpResponse(json.dumps(response_data),content_type="application/json")


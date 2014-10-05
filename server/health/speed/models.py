from django.db import models
from django.contrib.auth.models import User

class Article(models.Model):
	title = models.CharField(max_length=128)
	file_path = models.CharField(max_length=128)

class UserProfile(models.Model):
    user = models.OneToOneField(User)
    articles = models.ManyToManyField(Article)



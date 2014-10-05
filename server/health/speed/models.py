from django.db import models
from django.contrib.auth.models import User
from datetime import datetime 

class Article(models.Model):
	title = models.CharField(max_length=128)
	has_text = models.BooleanField(default=False)
	text = models.TextField(default="No Content")
	date_uploaded = models.DateField(default=datetime.now())

	def __unicode__(self):
		return self.title

class UserProfile(models.Model):
    user = models.OneToOneField(User)
    articles = models.ManyToManyField(Article)

    def __unicode__(self):
    	return user.username



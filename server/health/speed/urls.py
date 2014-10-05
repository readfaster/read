from django.conf.urls import patterns, include, url
import views

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'health.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),


    url(r'^api/v1/article/(?P<article_id>\w{0,50})/show.json$', views.get_article,name="get_article"),
    url(r'^api/v1/article/new.json', views.post_article,name="post_article"),
    url(r'^api/v1/testtext.json', views.test_get,name="test"),
    url(r'^api/v1/add_user.json', views.add_user,name="add user"),
)

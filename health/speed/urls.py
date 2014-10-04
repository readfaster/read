from django.conf.urls import patterns, include, url
import views

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'health.views.home', name='home'),
    # url(r'^blog/', include('blog.urls')),

    url(r'^api/v1/(?P<file_path>[\w.]{0,50})/$', views.get_request,name="get"),
)

import requests, os
from bs4 import BeautifulSoup

READABILITY_TOKEN = os.environ['READABILITY_TOKEN']
READABILITY_URL = "https://readability.com/api/content/v1/parser"

"""
Returns the parsed article from url.
"""
def parse_url(url):
  payload = {
    'token': READABILITY_TOKEN,
    'url': url
  }
  response = requests.get(READABILITY_URL, params=payload)
  if response.status_code != 200:
    print "status code not 200: %d" % response.status_code
    return r.content
  j = response.json()
  j['content'] = strip_html(j['content'])
  return j

def strip_html(content):
  return BeautifulSoup(content).get_text()

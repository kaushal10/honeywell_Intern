import urllib.request
import ssl
#import urllib.request.Request
from urllib.request import Request,urlopen
import requests

url = "https://build.chevalpartners.com/export/plan.action?buildKey=SI-ANS"
#req = urllib.request(url)
key = "Authorization"
val = "Basic aDI4MDY3NDpoMjgwNjc0"
hey = Request(url)
req = hey.add_header(key,val)
gcontext = ssl.SSLContext(ssl.PROTOCOL_TLSv1)  # Only for gangstars
#info = urlopen(url, context=gcontext).read()
content = requests.get(url,verify = False , auth=('h280674', 'green@123'))
print (content)
#print (info)
import requests
print (requests.__version__)
# Fill in your details here to be posted to the login form.
payload = {
    'username': 'h280674',
    'password': 'green@123'
}

# Use 'with' to ensure the session context is closed after use.
with requests.Session() as s:
    p = s.post('https://build.chevalpartners.com/userlogin!doDefault.action', data=payload, verify=False)
    # print the html returned or something more intelligent to see if it's a successful login page.
    print (p.text)

    # An authorised request.
    r = s.get('https://build.chevalpartners.com/export/plan.action?buildKey=SI-SS')
    print (r.text)
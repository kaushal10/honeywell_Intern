import os
import tkinter 
from tkinter import *

inp = input("enter path of file : \n")
file = open(inp)
f=file.read().splitlines()
#print (file.name)
opfilePath = str(file.name) + "_withQuotes.txt"
array = []
for i in f:
	i = "\"" + i + "\"" + ",\n"
	array.append(i)
	#print (i)
fw= open(opfilePath,'w+')
for i in array:
	fw.write(i)	
print ("Your file has been saved to location : " + opfilePath)

#http://cbpgatqareguiweb.azurewebsites.net
#f54fd133-b81e-4dfb-b57a-2290ae0b1bce
#P$Wyew-v1dRwZIAs(rKy%By8mk_zOC)-[cO-IGUI
#P$Wyew-v1dRwZIAs(rKy%By8mk_zOC)-[cO-lGUl
#client_credentials
#"access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IlNTUWRoSTFjS3ZoUUVEU0p4RTJnR1lzNDBRMCIsImtpZCI6IlNTUWRoSTFjS3ZoUUVEU0p4RTJnR1lzNDBRMCJ9.
'''eyJhdWQiOiJodHRwOi8vY2JwZ2F0cWFyZWd1aXdlYi5henVyZXdlYnNpdGVzLm5ldCIsImlzcyI6Imh0dHBzOi8vc3RzLndpbmRvd3MubmV0L2YwMjY5NDc0LWFjOTUtNGY1Yi05NWE5LWY3YThhYmU3N
zliMy8iLCJpYXQiOjE1MjE2MzA4NTUsIm5iZiI6MTUyMTYzMDg1NSwiZXhwIjoxNTIxNjM0NzU1LCJhaW8iOiJZMk5nWUhCMFludmpjNGN6aE5NNTh0M0t5a1FYQUE9PSIsImFwcGlkIjoiZjU0ZmQxMzMtY
jgxZS00ZGZiLWI1N2EtMjI5MGFlMGIxYmNlIiwiYXBwaWRhY3IiOiIxIiwiZ3JvdXBzIjpbIjRiZDBjZDM0LTc1ZDUtNDBkOS1iOTg0LTMyOWNkZTc3ZDY5MyIsImRjYzY4MmUzLWNjNzItNGM0Yi05N2YzLTM
5YzlhOGRjNjQxNiIsIjgwZTY1YWU5LTFjMWUtNDU5My05ZDI4LWJjZjg4ZDBhYWZmZSIsIjY5NmNhYmRhLWE1ZTEtNDQ0Yy05MzRiLTNiZmY0NDk0MzYzYyIsIjMwMWE4MzEyLThlMGQtNDVlNi04N2JiLWE4NDB
jOTQzY2RhOSIsImZmMWNmNTU1LWUzODctNGE2Ny1iOTMzLTdiNWQ5NDlkOWE5YiIsIjJhOTVhNjQyLTczNmEtNDdlNS1hNjIxLTJmNmYyNjY4ZWU5YSIsIjczOTkyZDIyLTc0MzgtNGVhMS04ZjdlLTk1MTM4ZTNhN
TQwZSIsImM1ZDA1Mzk0LWE1YzgtNDIxNS05MDBhLWYyMjNiYTk3Yzc4YiIsImNjNWUxODk3LWUxNDYtNDAyYS1iMWI0LWNiOWZhN2NhMmVjYSIsIjFlNmNiNjEyLWNjNmItNGVkYS05Mzk1LTkwNDJiZDUxNDY4NSIsIm
FmMDFhMTI0LWJhYTgtNGZkNC1iZjc4LTA3OTZkYTBmMWJjZSJdLCJpZHAiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9mMDI2OTQ3NC1hYzk1LTRmNWItOTVhOS1mN2E4YWJlNzc5YjMvIiwib2lkIjoiM2JiZjllOTgtMmFlZC00
OGM2LTg0MzItYTdmYWIyMzA3NjBiIiwic3ViIjoiM2JiZjllOTgtMmFlZC00OGM2LTg0MzItYTdmYWIyMzA3NjBiIiwidGlkIjoiZjAyNjk0NzQtYWM5NS00ZjViLTk1YTktZjdhOGFi
ZTc3OWIzIiwidXRpIjoiLUVmTXgxcGdFa2lWSXlseVUxUXdBQSIsInZlciI6IjEuMCJ9.QENIXZD_kOQwn_Qfn7Z1lcJiBesW3bDssmTRfLX_OHlqoRVVe6N1KxyTGba0Fca3lvsp
E4oYk7wPszVMCnqZCdiQLDiUKBN9KhlTmr2O0_eSazDJeSXMTm_KAJ5OuLaNGbzKXRf9aFLP1lDW2s-3lnNman2DPCQMkWNPVtOvbpYYjBH8WWaotic9UjHRdNF2v435vzqEnrt8I_xe4F
LRmRX_26-v2zhBD8cT6fT2k11ffG8e4hB_e6T2ut-82W_pJjVTWsEatJ0kTlsqIREr5JG0DqWNiam_MCWNK5Yia4peeCJfdj-RYPz77jThAYWsDiyzIduy_7mK9X_c4zQRuA"'''

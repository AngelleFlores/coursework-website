#! /usr/bin/python
import cgitb; cgitb.enable()
import cgi, sys

form = None

def show_params():
   for key in form.keys():
      print '<p>For key ', key, ' you entered <font class="bigger">', form.getfirst(key)
      print "</font></p>"

def main(argv=None):
   global form
   if argv is None:
      argv = sys.argv
   form = cgi.FieldStorage()

   print """Content-type: text/html

<html>
<head>
<title>Test of form submittal</title>
<style type="text/css">
   body {
      color: white;
      background: green;
      font-family: monospace;
      font-size: 12pt;
   }
   .bigger {
      color: yellow;
      font-size: 18pt;
   }
</style>
<h1>Testing form submittal</h1>
"""

   show_params()
   print """
</body>
</html>
"""


if __name__ == "__main__":
    sys.exit(main())

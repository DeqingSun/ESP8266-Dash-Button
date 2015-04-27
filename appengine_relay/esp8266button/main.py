#!/usr/bin/env python
#
# Copyright 2007 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
import webapp2

from datetime import datetime

from google.appengine.api import memcache
from google.appengine.api import mail

class ButtonHandler(webapp2.RequestHandler):
    def get(self):
        error_var = self.request.get("err","NO")
        if (error_var == "YES"):
            self.error(500)
        else:    
            self.response.write('OK')
            current_time_str = datetime.now().strftime("%H:%M:%S on %B %d, %Y")
            last_time = memcache.get("recent0_time");
            last_time2 = memcache.get("recent1_time");
            if (last_time == None):
                last_time = "Unknown"
            if (last_time2 == None):
                last_time2 = "Unknown"            
            memcache.set("recent2_time", last_time2);
            memcache.set("recent1_time", last_time);
            memcache.set("recent0_time", current_time_str);
            mail.send_mail(sender="button@esp8266button.appspotmail.com",
              to="trigger@recipe.ifttt.com",
              subject="trigger email",
              body="Whatever")
            
            
        
class MainHandler(webapp2.RequestHandler):
    def get(self):
        self.response.write('Hello world!<br>\n\r')
        last_time0=memcache.get("recent0_time");
        last_time1=memcache.get("recent1_time");
        last_time2=memcache.get("recent2_time");
        if (last_time0 == None):
                last_time0 = "Unknown"
        if (last_time1 == None):
                last_time1 = "Unknown"
        if (last_time2 == None):
                last_time2 = "Unknown"
        self.response.write('recent button request<br>\n\r')
        self.response.write(last_time0+'<br>\n\r')
        self.response.write(last_time1+'<br>\n\r')
        self.response.write(last_time2+'<br>\n\r')
        

app = webapp2.WSGIApplication([
    ('/', MainHandler),
    ('/button', ButtonHandler),
], debug=True)

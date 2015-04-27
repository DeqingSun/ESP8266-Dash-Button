import logging
import webapp2
from google.appengine.ext.webapp.mail_handlers import InboundMailHandler

class LogSenderHandler(InboundMailHandler):
    def receive(self, mail_message):
        logging.info("================================")
        logging.info("Received a mail_message from: " + mail_message.sender)
        logging.info("The email subject: " + mail_message.subject)
        logging.info("The email was addressed to: " + mail_message.to)
        
        bodies = mail_message.bodies()

        for content_type, body in bodies:
            decoded_html = body.decode()
            logging.info("content_type: " + content_type)
            logging.info("decoded_html: " + decoded_html)
            

app = webapp2.WSGIApplication([LogSenderHandler.mapping()], debug=True)

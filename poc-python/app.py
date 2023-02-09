
import redis
from flask import Flask, jsonify, request
import requests
from gevent.pywsgi import WSGIServer


app = Flask(__name__)
redisClient = redis.Redis(host='localhost', port=6379, db=0)


# for client to server
@app.route('/chat-with-server', methods=['POST'])
def process_message():
    jsonStr = request.get_json()

    if jsonStr is None or "clientId" not in jsonStr or "host" not in jsonStr or "text" not in jsonStr:
        return "Request body missing or incomplete.", 400

    redisClient.set(jsonStr['clientId'], jsonStr['host'])
    print("CLIENT: " + jsonStr['text'])
    return "Message received", 200


def run_app():
    # app.run(host="localhost", port=9091, debug=True)
    http_server = WSGIServer(('localhost', 9091), app, log=None)
    http_server.serve_forever()


# for server to client
@app.route('/chat-with-client', methods=['POST'])
def chat_with_client():
    jsonStr = request.get_json()

    if jsonStr is None or 'clientId' not in jsonStr or 'text' not in jsonStr:
        return "Request body missing or incomplete.", 400

    clientId = jsonStr['clientId']
    text = jsonStr['text']

    clientHostAddress = redisClient.get(clientId)

    if clientHostAddress is None:
        return "Client host address does not exist. Client probably never connected.", 500

    apiUrl = "http://" + clientHostAddress.decode("utf-8") + ":9090/chat"
    response = requests.post(apiUrl, data=text)

    if 200 <= response.status_code < 300:
        print("SERVER: " + text)
        return "Message sent successfully", 200
    else:
        return "error while sending message. Error: " + response.status_code, 500


if __name__ == "__main__":
    run_app()

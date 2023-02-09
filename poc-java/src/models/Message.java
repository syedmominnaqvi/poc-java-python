package models;

public class Message {
    private String clientId;
    private String text;
    private String host;

    public Message(String clientId, String text, String host, int port) {
        this.clientId = clientId;
        this.text = text;
        this.host = host;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String toJsonString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ \"clientId\":\"");
        stringBuilder.append(this.clientId);
        stringBuilder.append("\", \"text\":\"");
        stringBuilder.append(this.text);
        stringBuilder.append("\", \"host\":\"");
        stringBuilder.append(this.host);
        stringBuilder.append("\"}");

        return stringBuilder.toString();
    }
}

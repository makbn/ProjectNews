package ir.ac.um.ce.projectnews.search;

class Entry {
    private String id;
    private String body;

    public Entry(String id, String body) {
        this.id = id;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public String getBody() {
        return body;
    }
}
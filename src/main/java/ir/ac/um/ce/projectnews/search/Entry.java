package ir.ac.um.ce.projectnews.search;

class Entry {
    private String id;
    private String body;

    Entry(String id, String body) {
        this.id = id;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    String getBody() {
        return body;
    }
}
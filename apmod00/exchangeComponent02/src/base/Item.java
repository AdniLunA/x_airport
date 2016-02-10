package base;

public class Item {
    private int id;
    private String content;

    public Item(int id,String content) {
        this.id = id;
        this.content = content;
    }

    public int getID() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ Item : ");
        stringBuilder.append("id = ").append(id).append(",");
        stringBuilder.append("content = ").append(content).append(" }");
        return stringBuilder.toString();
    }
}
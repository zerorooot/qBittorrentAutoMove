package github.zerorooot.bean;


public class FileBean {
    private String name;
    private String content_path;
    private String id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getContent_path() {
        return content_path;
    }

    public void setContent_path(String content_path) {
        this.content_path = content_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "name='" + name + '\'' +
                ", content_path='" + content_path + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

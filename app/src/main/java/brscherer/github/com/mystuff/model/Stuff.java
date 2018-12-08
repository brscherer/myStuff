package brscherer.github.com.mystuff.model;

public class Stuff {
    private Long id;
    private String name;
    private Boolean borrowed;

    public Stuff(String name){
        this.name = name;
        this.borrowed = false;
    }

    public Stuff() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Boolean borrowed) {
        this.borrowed = borrowed;
    }
}

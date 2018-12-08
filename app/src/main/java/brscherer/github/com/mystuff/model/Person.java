package brscherer.github.com.mystuff.model;

import java.util.List;

public class Person {
    private Long id;
    private String name;
    private String email;
    private List<Stuff> stuffList;

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Person() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Stuff> getStuffList() {
        return stuffList;
    }

    public void setStuffList(List<Stuff> stuffList) {
        this.stuffList = stuffList;
    }
}

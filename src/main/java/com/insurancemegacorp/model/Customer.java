package com.insurancemegacorp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity // For demo/upgrade only; not used in main logic
public class Customer {

    @Id
    private String id;
    private String name;
    private int age;

    public Customer(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

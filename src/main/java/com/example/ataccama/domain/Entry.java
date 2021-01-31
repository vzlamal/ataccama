package com.example.ataccama.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "entry")
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "pass")
    private String pass;

    @Column(name = "url")
    private String url;

    @Column(name = "database")
    private String database;

    @Column(name = "port")
    private int port;


    public Entry(String name, String pass, String database, String url, int port) {
        this.name = name;
        this.pass = pass;
        this.database = database;
        this.url = url;
        this.port = port;
    }

    public Entry() {

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public String getUrl() {
        return url;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setName(String user) {
        this.name = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getConnectionString() {
        return url + ":" + port + "/" + database;
    }

    @Override
    public String toString() {
        return "Entity [id=" + id + ", name=" + name + ", pass=" + pass + ", url=" + url + ", database=" + database + ", port=" + port + "]";
    }
}

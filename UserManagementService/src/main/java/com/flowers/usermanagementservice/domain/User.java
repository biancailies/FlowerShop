package com.flowers.usermanagementservice.domain;

public class User {

    private UserId id;
    private String username;
    private String password;
    private String type;
    private Integer flowerShopId;

    public User() {
    }

    public User(UserId id, String username, String password, String type, Integer flowerShopId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
        this.flowerShopId = flowerShopId;
    }

    public User(String username, String password, String type, Integer flowerShopId) {
        this.id = new UserId(0);
        this.username = username;
        this.password = password;
        this.type = type;
        this.flowerShopId = flowerShopId;
    }

    public UserId getId() {
        return id;
    }

    public void setId(UserId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFlowerShopId() {
        return flowerShopId;
    }

    public void setFlowerShopId(Integer flowerShopId) {
        this.flowerShopId = flowerShopId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", type='" + type + '\'' +
                ", flowerShopId=" + flowerShopId +
                '}';
    }
}

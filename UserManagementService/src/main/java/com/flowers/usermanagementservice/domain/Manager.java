package com.flowers.usermanagementservice.domain;

public class Manager extends User {

    public Manager() {
        setType("MANAGER");
    }

    public Manager(UserId id, String username, String password, Integer flowerShopId) {
        super(id, username, password, "MANAGER", flowerShopId);
    }

    public Manager(String username, String password, Integer flowerShopId) {
        super(username, password, "MANAGER", flowerShopId);
    }
}

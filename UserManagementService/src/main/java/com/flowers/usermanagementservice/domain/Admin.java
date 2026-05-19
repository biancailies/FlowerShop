package com.flowers.usermanagementservice.domain;

public class Admin extends User {

    public Admin() {
        setType("ADMIN");
    }

    public Admin(UserId id, String username, String password, Integer flowerShopId) {
        super(id, username, password, "ADMIN", flowerShopId);
    }

    public Admin(String username, String password) {
        super(username, password, "ADMIN", null);
    }
}

package com.flowers.usermanagementservice.domain;

public class Client extends User {

    public Client() {
        setType("CLIENT");
    }

    public Client(UserId id, String username, String password, Integer flowerShopId) {
        super(id, username, password, "CLIENT", flowerShopId);
    }

    public Client(String username, String password) {
        super(username, password, "CLIENT", null);
    }
}

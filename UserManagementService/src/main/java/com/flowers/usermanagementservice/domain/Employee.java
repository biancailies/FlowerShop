package com.flowers.usermanagementservice.domain;

public class Employee extends User {

    public Employee() {
        setType("EMPLOYEE");
    }

    public Employee(UserId id, String username, String password, Integer flowerShopId) {
        super(id, username, password, "EMPLOYEE", flowerShopId);
    }

    public Employee(String username, String password, Integer flowerShopId) {
        super(username, password, "EMPLOYEE", flowerShopId);
    }
}

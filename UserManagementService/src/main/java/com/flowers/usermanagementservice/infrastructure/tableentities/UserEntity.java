package com.flowers.usermanagementservice.infrastructure.tableentities;

import com.flowers.usermanagementservice.domain.User;
import com.flowers.usermanagementservice.domain.UserId;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "flower_shop_id")
    private Integer flowerShopId;

    public UserEntity() {
    }

    public UserEntity(User domainUser) {
        if (domainUser.getId() != null && domainUser.getId().getUserId() > 0) {
            this.id = domainUser.getId().getUserId();
        } else {
            this.id = null;
        }
        this.username = domainUser.getUsername();
        this.password = domainUser.getPassword();
        this.type = domainUser.getType();
        this.flowerShopId = domainUser.getFlowerShopId();
    }

    public User toUser() {
        return new User(
                new UserId(this.id),
                this.username,
                this.password,
                this.type,
                this.flowerShopId
        );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}

package com.flowers.usermanagementservice.services.dto;

public class LoginResponse {

    private int userId;
    private String username;
    private String type;
    private Integer flowerShopId;
    private boolean success;
    private String message;

    public LoginResponse() {
    }

    public LoginResponse(int userId, String username, String type, Integer flowerShopId,
                         boolean success, String message) {
        this.userId = userId;
        this.username = username;
        this.type = type;
        this.flowerShopId = flowerShopId;
        this.success = success;
        this.message = message;
    }

    /**
     * Constructor pentru login eșuat.
     */
    public LoginResponse(boolean success, String message) {
        this.userId = 0;
        this.username = null;
        this.type = null;
        this.flowerShopId = null;
        this.success = success;
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

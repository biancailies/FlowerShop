package com.flowers.flowercatalogservice.domain;

public class ImageId {

    private int imageId;

    public ImageId() {
    }

    public ImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageId that = (ImageId) o;
        return imageId == that.imageId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(imageId);
    }

    @Override
    public String toString() {
        return "ImageId{" + "imageId=" + imageId + '}';
    }
}

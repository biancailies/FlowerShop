package com.flowers.flowercatalogservice.domain;

public class Image {

    private ImageId id;
    private FlowerId flowerId;
    private String url;

    public Image() {
    }

    public Image(ImageId id, FlowerId flowerId, String url) {
        this.id = id;
        this.flowerId = flowerId;
        this.url = url;
    }

    public Image(FlowerId flowerId, String url) {
        this.id = new ImageId(0);
        this.flowerId = flowerId;
        this.url = url;
    }

    public ImageId getId() {
        return id;
    }

    public void setId(ImageId id) {
        this.id = id;
    }

    public FlowerId getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(FlowerId flowerId) {
        this.flowerId = flowerId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", flowerId=" + flowerId +
                ", url='" + url + '\'' +
                '}';
    }
}

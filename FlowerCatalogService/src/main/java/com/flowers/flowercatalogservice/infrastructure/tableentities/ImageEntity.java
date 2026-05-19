package com.flowers.flowercatalogservice.infrastructure.tableentities;

import com.flowers.flowercatalogservice.domain.FlowerId;
import com.flowers.flowercatalogservice.domain.Image;
import com.flowers.flowercatalogservice.domain.ImageId;
import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "flower_id", nullable = false)
    private int flowerId;

    @Column(name = "url", nullable = false, columnDefinition = "LONGTEXT")
    private String url;

    public ImageEntity() {
    }

    public ImageEntity(Image domainImage) {
        if (domainImage.getId() != null && domainImage.getId().getImageId() > 0) {
            this.id = domainImage.getId().getImageId();
        } else {
            this.id = null;
        }
        this.flowerId = domainImage.getFlowerId().getFlowerId();
        this.url = domainImage.getUrl();
    }

    public Image toImage() {
        return new Image(
                new ImageId(this.id),
                new FlowerId(this.flowerId),
                this.url
        );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(int flowerId) {
        this.flowerId = flowerId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

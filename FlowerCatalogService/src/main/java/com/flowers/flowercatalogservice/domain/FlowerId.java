package com.flowers.flowercatalogservice.domain;

public class FlowerId {

    private int flowerId;

    public FlowerId() {
    }

    public FlowerId(int flowerId) {
        this.flowerId = flowerId;
    }

    public int getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(int flowerId) {
        this.flowerId = flowerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlowerId that = (FlowerId) o;
        return flowerId == that.flowerId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(flowerId);
    }

    @Override
    public String toString() {
        return "FlowerId{" + "flowerId=" + flowerId + '}';
    }
}

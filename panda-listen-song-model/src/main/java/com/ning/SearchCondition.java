package com.ning;

import java.io.Serializable;

public class SearchCondition implements Serializable {
    private Integer currentPage;
    private String name;
    private Integer singerId;
    private Integer typeId;
    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getSingerId() {
        return singerId;
    }

    public void setSingerId(Integer singerId) {
        this.singerId = singerId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SearchCondition{" +
                "currentPage=" + currentPage +
                ", singerId=" + singerId +
                ", typeId=" + typeId +
                ", name='" + name + '\'' +
                '}';
    }
}

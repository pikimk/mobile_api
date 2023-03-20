package com.example.MobileDemo;


public class MobileData {

    private Long id;
    private String manufacturer;
    private String model;
    private String part;
    private Integer quantity;
    private String description;

    public MobileData(Long id,String manufacturer, String model, String part, Integer quantity, String description){
        this.manufacturer = manufacturer;
        this.model = model;
        this.part = part;
        this.quantity = quantity;
        this.description = description;
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

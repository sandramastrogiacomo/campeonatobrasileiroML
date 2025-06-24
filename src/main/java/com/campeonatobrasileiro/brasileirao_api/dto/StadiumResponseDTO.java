package com.campeonatobrasileiro.brasileirao_api.dto;

public class StadiumResponseDTO {
    private Long id;
    private String name;
    private String city;
    private Integer capacity;

    public StadiumResponseDTO(Long id, String name, String city, Integer capacity) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}

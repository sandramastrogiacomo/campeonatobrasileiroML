package com.campeonatobrasileiro.brasileirao_api.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "estadios")
public class StadiumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String city;

    private Integer capacity;

    @OneToMany (mappedBy = "stadium", fetch = FetchType.LAZY)
    private List<MatchEntity> matches;

    public StadiumEntity() {
    }

    public StadiumEntity(Long id, String name, String city, Integer capacity) {
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

    public List<MatchEntity> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchEntity> matches) {
        this.matches = matches;
    }
}



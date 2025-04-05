package com.example.backend.Services;

import com.example.backend.Entity.Plat;

import java.util.List;

public interface PlatInterface {
    public Plat addPlat(Plat plat);
    void deletePlat(Long idPlat);
    public Plat updatePlat(Long idPlat,Plat plat);
    public Plat getPlatByName(String name);
    public List<Plat> getplats();
}
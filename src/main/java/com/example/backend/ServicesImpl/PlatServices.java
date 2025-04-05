package com.example.backend.ServicesImpl;

import com.example.backend.Entity.Plat;
import com.example.backend.Repository.PlatRepository;
import com.example.backend.Services.PlatInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatServices implements PlatInterface {
    @Autowired
    PlatRepository platRepository;
    @Override
    public Plat addPlat(Plat plat) {
        return platRepository.save(plat);
    }
    @Override
    public void deletePlat(Long idPlat) {
        platRepository.deleteById(idPlat);

    }
    @Override
    public Plat updatePlat(Long idPlat, Plat plat) {
        Plat plt = platRepository.findById(idPlat).get();
        plt.setName(plat.getName());
        plt.setDescription(plat.getDescription());
        plt.setPrix(plat.getPrix());
        return platRepository.save(plt);
    }
    @Override
    public Plat getPlatByName(String name) {
        return platRepository.findByName(name);
    }
    @Override
    public List<Plat> getplats() {

        return platRepository.findAll();
    }


}


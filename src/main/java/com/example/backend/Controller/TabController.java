package com.example.backend.Controller;


import com.example.backend.Entity.Tab;
import com.example.backend.Services.TabInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tab")
public class TabController {
    @Autowired
    private TabInterface tabInterface;
    @PostMapping("/add")
    public String add(@RequestBody Tab tab) {

        return tabInterface.addTab(tab);
    }

    @DeleteMapping("/delete/{idTable}")
    public void delete(@PathVariable Long idTable) {
        tabInterface.delete(idTable);
    }


    @PutMapping("/update/{ids}")
    public Tab update(@PathVariable("ids")Long idTable,@RequestBody Tab tab)
    {
        return tabInterface.update(idTable,tab);
    }


    @GetMapping("/gettabs")
    public List<Tab> getAllTabs(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        // Utilisation de l'instance injectée pour appeler la méthode
        return tabInterface.getAllTabs(page, size);
    }


    @GetMapping("/get/{idTable}")
    public Tab getTab(@PathVariable Long idTable) {
        return tabInterface.getTab(idTable);  // Appel à la méthode du service
    }






}

package com.example.backend.Services;


import com.example.backend.Entity.Tab;

import java.util.List;

public interface TabInterface {

    Tab getTab(Long idTable);

    List<Tab> getAllTabs(int page, int size);

    String addTab(Tab tab);

    void delete(Long idTable);

    public Tab update(Long idTable, Tab tab);
}
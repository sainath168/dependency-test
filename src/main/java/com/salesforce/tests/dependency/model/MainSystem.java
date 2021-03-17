package com.salesforce.tests.dependency.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainSystem {
    private Map<String, Item> items;
    private Map<String, Set<Item>> dependenciesMap;
    private Map<String, Set<Item>> dependentsMap;

    public MainSystem() {
        this.items = new HashMap<>();
        this.dependenciesMap = new HashMap<>();
        this.dependentsMap = new HashMap<>();
    }

    public void addItem(Item item) {
        this.items.put(item.getName(), item);
    }

    public Set<Item> getItemDependencies(String itemName) {
        if (!dependenciesMap.containsKey(itemName)) {
            dependenciesMap.put(itemName, new HashSet<>());
        }
        return dependenciesMap.get(itemName);
    }

    public Set<Item> getItemDependents(String itemName) {
        if (!dependentsMap.containsKey(itemName)) {
            dependentsMap.put(itemName, new HashSet<>());
        }
        return dependentsMap.get(itemName);
    }

    //TODO: should minimize the effort of looping all the items in the system
    public void updateDependencyDataInSystem() {
        dependenciesMap.values()
                .forEach(set -> {
                    set.forEach(item -> {
                        if (items.containsKey(item.getName()))  {
                            Item actualItem = items.get(item.getName());
                            item.setExplicitlyInstalled(actualItem.isExplicitlyInstalled());
                            item.setImplicitlyInstalled(actualItem.isImplicitlyInstalled());
                        }
                    });
                });

        dependentsMap.values()
                .forEach(set -> {
                    set.forEach(item -> {
                        if (items.containsKey(item.getName()))  {
                            Item actualItem = items.get(item.getName());
                            item.setExplicitlyInstalled(actualItem.isExplicitlyInstalled());
                            item.setImplicitlyInstalled(actualItem.isImplicitlyInstalled());
                        }
                    });
                });
    }

    // getters and setter
    public Map<String, Item> getItems() {
        return items;
    }

    public void setItems(Map<String, Item> items) {
        this.items = items;
    }

    public Map<String, Set<Item>> getDependenciesMap() {
        return dependenciesMap;
    }

    public void setDependenciesMap(Map<String, Set<Item>> dependenciesMap) {
        this.dependenciesMap = dependenciesMap;
    }

    public Map<String, Set<Item>> getDependentsMap() {
        return dependentsMap;
    }

    public void setDependentsMap(Map<String, Set<Item>> dependentsMap) {
        this.dependentsMap = dependentsMap;
    }
}

package com.salesforce.tests.dependency.model;

public class Item {
    private String name;
    private boolean isExplicitlyInstalled;
    private boolean isImplicitlyInstalled;

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, boolean isExplicitlyInstalled, boolean isImplicitlyInstalled) {
        this.name = name;
        this.isExplicitlyInstalled = isExplicitlyInstalled;
        this.isImplicitlyInstalled = isImplicitlyInstalled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExplicitlyInstalled() {
        return isExplicitlyInstalled;
    }

    public void setExplicitlyInstalled(boolean explicitlyInstalled) {
        isExplicitlyInstalled = explicitlyInstalled;
    }

    public boolean isImplicitlyInstalled() {
        return isImplicitlyInstalled;
    }

    public void setImplicitlyInstalled(boolean implicitlyInstalled) {
        isImplicitlyInstalled = implicitlyInstalled;
    }

    public boolean isInstalled() {
        return this.isExplicitlyInstalled || this.isImplicitlyInstalled;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item) {
            Item item = (Item) obj;
            return (this.name == null && item.name == null) ||
                    (this.name != null && this.name.equalsIgnoreCase(item.name));
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (name != null) {
            return name.hashCode();
        }
        return 0;
    }
}

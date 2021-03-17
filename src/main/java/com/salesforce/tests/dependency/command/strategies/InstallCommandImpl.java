package com.salesforce.tests.dependency.command.strategies;

import com.salesforce.tests.dependency.model.Item;
import com.salesforce.tests.dependency.model.MainSystem;

import java.util.Objects;
import java.util.Set;

public class InstallCommandImpl implements CommandStrategy {
    @Override
    public String execute(String[] items, MainSystem system) {

        String installingItem = items[0];

        StringBuilder output = new StringBuilder();

        Set<Item> dependencies = system.getItemDependencies(installingItem);
        dependencies.forEach(item -> {
            installItem(item, false, true, output);
            system.addItem(item); // basically to update the item with updated installation status
        });

        if (Objects.isNull(system.getItems().get(installingItem))) {
            Item item = new Item(installingItem);
            system.addItem(item);
        }
        installItem(system.getItems().get(installingItem), true, false, output);

        // update items in the system with its latest values
        system.updateDependencyDataInSystem();

        return output.toString();
    }

    private void installItem(Item item, boolean explicitlyInstalling, boolean implicitlyInstalling, StringBuilder output) {
        if (item.isInstalled()) {
            // either explicitly or implicitly
            return;
        }
        item.setExplicitlyInstalled(explicitlyInstalling);
        item.setImplicitlyInstalled(implicitlyInstalling);
        output.append("Installing ").append(item.getName()).append(System.lineSeparator());
    }
}

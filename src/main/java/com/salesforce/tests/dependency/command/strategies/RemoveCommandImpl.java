package com.salesforce.tests.dependency.command.strategies;

import com.salesforce.tests.dependency.model.Item;
import com.salesforce.tests.dependency.model.MainSystem;

import java.util.Set;
import java.util.stream.Collectors;

public class RemoveCommandImpl implements CommandStrategy {
    @Override
    public String execute(String[] items, MainSystem system) {

        if (items.length > 1) {
            throw new IllegalArgumentException("Remove one item at a time");
        }

        StringBuilder output = new StringBuilder();

        String removingItem = items[0];

        //TODO: non-completed code
        Set<Item> dependents = system.getItemDependents(removingItem);
        Set<Item> installedDependents = dependents.stream().filter(Item::isInstalled).collect(Collectors.toSet());

        if (!installedDependents.isEmpty()) {
            output.append(removingItem).append(" is still needed");
        } else {
            if (system.getItems().containsKey(removingItem)) {
                if (system.getItems().get(removingItem).isInstalled()) {
                    unInstallItem(system.getItems().get(removingItem), output);
                    Set<Item> dependencies = system.getItemDependencies(removingItem);
                    dependencies.forEach(item -> unInstallItem(item, output));
                } else {
                    output.append(removingItem).append(" is not installed");
                }
            } else {
                output.append(removingItem).append(" is unknown");
            }
        }

        system.updateDependencyDataInSystem();

        return output.toString();
    }

    /**
     * TODO: above code is not going to check the impact of removing the dependencies
     *  we should instead do the recursive call
      */
    private void recursiveUnInstall() {

    }

    private void unInstallItem(Item item, StringBuilder ouput) {
        item.setImplicitlyInstalled(false);
        item.setExplicitlyInstalled(false);
        ouput.append("Removing ").append(item.getName());
    }
}

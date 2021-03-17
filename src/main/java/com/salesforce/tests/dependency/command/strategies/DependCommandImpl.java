package com.salesforce.tests.dependency.command.strategies;

import com.salesforce.tests.dependency.model.Item;
import com.salesforce.tests.dependency.model.MainSystem;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.Arrays;
import java.util.Set;

/**
 * Sample command
 * DEPEND TELNET TCPIP NETCARD
 */
public class DependCommandImpl implements CommandStrategy {
    @Override
    public String execute(String[] items, MainSystem system) {

        StringBuilder output = new StringBuilder();

        /*
         * DEPEND Item1 Item2
         *
         * i.e.,
         *
         * item1 requires item2 (item1 has a dependency on item2)
         * item2 has one dependent called item1
         *
         *
         * so our target is to
         * -> create dependency (usefull to install the item and its dependencies)
         * -> dependent list (usefull when you want to remove item and check if any impact on dependents)
         */

        Item item1 = new Item(items[0]);
        system.addItem(item1);

        for (String item : Arrays.copyOfRange(items, 1, items.length)) {

            Item item2 = new Item(item);

            // if item2 is already a dependent of item1, then again item1 can't be dependent of item2 ==> cyclic dependency
            Set<Item> items1Dependents = system.getItemDependents(item1.getName());
            if (items1Dependents.contains(item2)) {
                output.append(item2.getName()).append(" depends on ").append(item1.getName()).append(", ignoring command");
                continue;
            }

            system.addItem(item2);

            Set<Item> dependencies = system.getItemDependencies(item1.getName());
            dependencies.add(item2);

            Set<Item> dependents = system.getItemDependents(item);
            dependents.add(item1);

        }

        return output.toString();
    }
}

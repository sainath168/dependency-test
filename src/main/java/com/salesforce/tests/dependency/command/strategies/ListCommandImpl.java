package com.salesforce.tests.dependency.command.strategies;

import com.salesforce.tests.dependency.model.Item;
import com.salesforce.tests.dependency.model.MainSystem;

public class ListCommandImpl implements CommandStrategy {
    @Override
    public String execute(String[] items, MainSystem system) {

        StringBuilder output = new StringBuilder();

        // get only installed items (either explicitly  or implicitly)
        system.getItems().values()
                .stream()
                .filter(Item::isInstalled)
                .forEach(item -> output.append(item.getName()).append(System.lineSeparator()));

        return output.toString();
    }
}

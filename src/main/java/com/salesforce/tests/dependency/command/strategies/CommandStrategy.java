package com.salesforce.tests.dependency.command.strategies;

import com.salesforce.tests.dependency.model.MainSystem;

public interface CommandStrategy {
    /**
     * Accept the item(s) to pass it to command and return the output received from the command
     * @param items
     * @return
     */
    String execute(String[] items, MainSystem system);
}

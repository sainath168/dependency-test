package com.salesforce.tests.dependency;

import com.salesforce.tests.dependency.command.enums.Command;
import com.salesforce.tests.dependency.command.strategies.CommandStrategy;
import com.salesforce.tests.dependency.command.strategies.DependCommandImpl;
import com.salesforce.tests.dependency.command.strategies.InstallCommandImpl;
import com.salesforce.tests.dependency.command.strategies.ListCommandImpl;
import com.salesforce.tests.dependency.command.strategies.RemoveCommandImpl;
import com.salesforce.tests.dependency.model.MainSystem;

import java.util.Arrays;
import java.util.Scanner;

/**
 * The entry point for the Test program
 */
public class Main {

    private static String EMPTY_SPACE = " ";

    private CommandStrategy commandStrategy;

    /**
     * strategy pattern
     * @param command
     */
    private void setStrategy(Command command) {
        switch (command) {
            case DEPEND:
                this.commandStrategy = new DependCommandImpl();
                break;
            case INSTALL:
                this.commandStrategy = new InstallCommandImpl();
                break;
            case REMOVE:
                this.commandStrategy = new RemoveCommandImpl();
                break;
            case LIST:
                this.commandStrategy = new ListCommandImpl();
                break;
        }
    }

    public CommandStrategy getCommandStrategy() {
        return commandStrategy;
    }

    public static void main(String[] args) {

        Main main = new Main();
        MainSystem system = new MainSystem();

        //read input from stdin
        Scanner scan = new Scanner(System.in);

        while (true) {
            String line = scan.nextLine();

            //no action for empty input
            if (line == null || line.length() == 0) {
                continue;
            }

            //the END command to stop the program
            if ("END".equals(line)) {
                System.out.println("END");
                break;
            }

            //implementation

            String[] instruction = line.split(EMPTY_SPACE);

            try {
                Command command = Command.valueOf(instruction[0]); // expected one of the commands listed in Commands enum
                main.setStrategy(command);


                String[] items = Arrays.copyOfRange(instruction, 1, instruction.length);
                String output = main.getCommandStrategy().execute(items, system);

                if (output != null) {
                    // for ex, in DEPEND case we don't print any output
                    System.out.println(output);
                }

            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Unknown Command"); // not expected
            }

        }

    }
}
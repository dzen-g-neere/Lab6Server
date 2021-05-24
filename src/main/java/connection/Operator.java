package connection;

import labwork.LabWork;
import utility.*;

import java.util.Scanner;

public class Operator {
    private static Scanner userScanner;
    private static String myenv;
    private static FileManager fileManager;
    private static CollectionManager collectionManager;
    private static LabWorkAsker asker;
    private static CommandManager commandManager;

    public Operator(Scanner userScanner, String myenv, CollectionManager collectionManager, FileManager fileManager, LabWorkAsker asker, CommandManager commandManager) {
        Operator.userScanner = userScanner;
        Operator.myenv = myenv;
        Operator.fileManager = fileManager;
        Operator.collectionManager = collectionManager;
        Operator.asker = asker;
        Operator.commandManager = commandManager;

    }

    public ExchangeClass startCommand(ExchangeClass exchangeClass) {
        try {
            String arg = exchangeClass.getArgument();
            switch (exchangeClass.getCommandName()) {
                case "insert":
                    LabWork labWork = exchangeClass.getLabWork();
                    collectionManager.addLabWorkToCollection(labWork.getName(), labWork);
                    //exchangeClass.setAnswer(commandManager.insertLWToCollection(arg));
                    break;
                case "update":
                    exchangeClass.setAnswer(commandManager.updateID(arg));
                    break;
                case "show":
                    exchangeClass.setAnswer(commandManager.showCollection(arg));
                    break;
                case "help":
                    exchangeClass.setAnswer(commandManager.help(arg));
                    break;
                case "info":
                    exchangeClass.setAnswer(commandManager.info(arg));
                    break;
                case "remove_key":
                    exchangeClass.setAnswer(commandManager.remove_key(arg));
                    break;
                case "clear":
                    exchangeClass.setAnswer(commandManager.clear(arg));
                    break;
                case "save":
                    exchangeClass.setAnswer(commandManager.save(arg));
                    break;
                case "execute_script":
                    exchangeClass.setAnswer("THIS COMMAND IS NOT WORKING");
                    break;
                case "exit":
                    exchangeClass.setAnswer(commandManager.exit(arg));
                    break;
                case "replace_if_greater":
                    exchangeClass.setAnswer(commandManager.replace_if_greater(arg));
                    break;
                case "replace_if_lowe":
                    exchangeClass.setAnswer(commandManager.replace_if_lowe(arg));
                    break;
                case "remove_greater_key":
                    exchangeClass.setAnswer(commandManager.remove_greater_key(arg));
                    break;
                case "group_counting_by_creation_date":
                    exchangeClass.setAnswer(commandManager.group_counting_by_creation_date(arg));
                    break;
                case "filter_greater_than_average_point":
                    exchangeClass.setAnswer(commandManager.filter_greater_than_average_point(arg));
                    break;
                case "print_descending":
                    exchangeClass.setAnswer(commandManager.print_descending(arg));
                    break;
                default:
                    System.out.println("Не является внутренней командой. Повтороте ввод или напишите help для получения актуального списка команд.");
            }
        } catch (Exception e) {
            exchangeClass.setAnswer("Что-то пошло не так");
            return exchangeClass;
        }
        return exchangeClass;
    }
}

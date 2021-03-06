package commands;

import connection.ExchangeClass;
import exceptions.WrongArgumentException;
import utility.CollectionManager;

/**
 * This is command 'remove_greater_key'. Remove elements which have key that is more than given.
 */
public class RemoveGreaterKey extends AbstractCommand implements Command {
    CollectionManager collectionManager;

    public RemoveGreaterKey(CollectionManager collectionManager) {
        super("remove_greater_key", " - удалить из коллекции все элементы, ключ которых превышает заданный");
        this.collectionManager = collectionManager;
    }

    /**
     * Execute of 'remove_greater_key' command.
     */
    @Override
    public String execute(String argument) {

        try {
            if (argument.isEmpty()) throw new WrongArgumentException();
            return collectionManager.removeGreaterKey(argument);
        } catch (WrongArgumentException e) {
            return "Некорректный аргумент";
        }
    }
}

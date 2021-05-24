package commands;

import connection.ExchangeClass;
import exceptions.IncorrectScriptException;
import labwork.LabWork;
import utility.CollectionManager;
import utility.LabWorkAsker;

/**
 * This is command 'update'. Refreshes an element of collection which id equals given one.
 */
public class UpdateIDCommand extends AbstractCommand implements Command {
    CollectionManager collectionManager;
    LabWorkAsker labWorkAsker;

    public UpdateIDCommand(CollectionManager collectionManager, LabWorkAsker labWorkAsker) {
        super("update", " ID - обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.labWorkAsker = labWorkAsker;
    }

    /**
     * Execute of 'update' command.
     */
    @Override
    public String execute(String argument) throws IncorrectScriptException {
        try {
            int i = Integer.parseInt(argument);
            LabWork labWork = collectionManager.getByKey(argument);
            return collectionManager.addLabWorkToCollection(
                    argument,
                    new LabWork(
                            labWork.getId(),
                            labWork.getName(),
                            labWorkAsker.askCoordinates(),
                            labWork.getCreationDate(),
                            labWorkAsker.askMinimalPoint(),
                            labWorkAsker.askPersonalQualitiesMinimum(),
                            labWorkAsker.askAveragePoint(),
                            labWorkAsker.askDifficulty(),
                            labWorkAsker.askAuthor()
                    )
            );
        } catch (NumberFormatException e) {
            return "ID должен быть целым числом";
        } catch (Exception e) {
            e.printStackTrace();
            return "Непредвиденная ошибка";
        }
    }
}

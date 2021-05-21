package run;

import commands.*;
import utility.*;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Main application class. Creates all instances and runs the program.
 *
 * @author Дмитрий Залевский P3112
 */
public class Main {

    public static void main(String[] args) {

        /*

        String path = System.getenv("envVariable");
        if (path == null){
            path = "backup";
            System.out.println("Переменная окружения не найдена, записано значение по умолчанию - backup");
        }

        Scanner userScanner = new Scanner(System.in);
        final String envVariable = path;
        LabWorkAsker labWorkAsker = new LabWorkAsker(userScanner);
        FileManager fileManager = new FileManager(envVariable, labWorkAsker);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        collectionManager.loadCollection();
        CommandManager commandManager = new CommandManager(
                new InsertCommand(collectionManager, labWorkAsker),
                new ShowCommand(collectionManager),
                new ExitCommand(),
                new UpdateIDCommand(collectionManager, labWorkAsker),
                new InfoCommand(collectionManager),
                new ClearCommand(collectionManager),
                new ExecuteScriptCommand(),
                new FilterGreaterThanAveragePointCommand(collectionManager),
                new GroupCountingByCreationDateCommand(collectionManager),
                new HelpCommand(),
                new PrintDescendingCommand(collectionManager),
                new RemoveGreaterKey(collectionManager),
                new RemoveKeyCommand(collectionManager),
                new ReplaceIfGreaterCommand(collectionManager, labWorkAsker),
                new ReplaceIfLowerCommand(collectionManager, labWorkAsker),
                new SaveCommand(collectionManager)
        );
        ConsoleManager consoleManager = new ConsoleManager(userScanner, commandManager, labWorkAsker);
        consoleManager.interectiveMode();

         */
        try {
        byte[] bytes = new byte[10];
        SocketAddress address =
                new InetSocketAddress(678);
        DatagramChannel channel =
                DatagramChannel.open();
        channel.configureBlocking(false);
        channel.bind(address);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        address = channel.receive(buffer);
            System.out.println("SMS received");
        for (int i = 0; i < 10; i++)
            bytes[i] *= 2;
        buffer.flip();
        channel.send(buffer, address);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}

package connection;


import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.Iterator;

public class Server {
    static int BUF_SZ = 1024;
    static int port = 8340;
    Operator operator;
    ExchangeClass exchangeClass;

    public Server(Operator operator) {
        this.operator = operator;
    }

    public void process() {
        try {
            Selector selector = Selector.open();
            DatagramChannel channel = DatagramChannel.open();
            InetSocketAddress isa = new InetSocketAddress(port);
            channel.socket().bind(isa);
            channel.configureBlocking(false);
            SelectionKey clientKey = channel.register(selector, SelectionKey.OP_READ);
            clientKey.attach(new Connect());
            while (true) {
                try {
                    selector.select();
                    Iterator selectedKeys = selector.selectedKeys().iterator();
                    while (selectedKeys.hasNext()) {
                        try {
                            SelectionKey key = (SelectionKey) selectedKeys.next();
                            selectedKeys.remove();

                            if (!key.isValid()) {
                                continue;
                            }

                            if (key.isReadable()) {
                                read(key);
                                key.interestOps(SelectionKey.OP_WRITE);
                            } else if (key.isWritable()) {
                                write(key);
                                key.interestOps(SelectionKey.OP_READ);
                            }
                        } catch (IOException e) {
                            System.err.println("glitch, continuing... " + (e.getMessage() != null ? e.getMessage() : ""));
                        }
                    }
                } catch (IOException e) {
                    System.err.println("glitch, continuing... " + (e.getMessage() != null ? e.getMessage() : ""));
                }
            }
        } catch (IOException e) {
            System.err.println("network error: " + (e.getMessage() != null ? e.getMessage() : ""));
        }
    }

    private void read(SelectionKey key) throws IOException {
        try {
            DatagramChannel channel = (DatagramChannel) key.channel();
            Connect connect = (Connect) key.attachment();
            connect.socketAddress = channel.receive(connect.request);
            ExchangeClass e = deserialize(connect.request.array());
            connect.request.rewind();
            System.out.println(e.getCommandName() + " " + e.getArgument() + "\n");
            exchangeClass = operator.startCommand(e);
            connect.response = ByteBuffer.wrap(serialize(exchangeClass));
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Что-то пошло не так. Server  Read.");
            e.printStackTrace();
        }
    }

    private void write(SelectionKey key) throws IOException {
        DatagramChannel channel = (DatagramChannel) key.channel();
        Connect connect = (Connect) key.attachment();
        channel.send(connect.response, connect.socketAddress);
        connect.response.rewind();
    }

    public byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
            }
            return b.toByteArray();
        }
    }

    public ExchangeClass deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream b = new ByteArrayInputStream(bytes)) {
            try (ObjectInputStream o = new ObjectInputStream(b)) {
                return (ExchangeClass) o.readObject();
            }
        }
    }

    class Connect {
        ByteBuffer request;
        ByteBuffer response;
        SocketAddress socketAddress;

        public Connect() {
            request = ByteBuffer.allocate(65535);
        }
    }

}

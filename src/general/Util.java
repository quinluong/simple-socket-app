package general;

import java.net.DatagramSocket;

public class Util {

    public static final String SERVER_IP = "127.0.0.1";
    public static final int SERVER_PORT = 4848;
    public static final int NUMBER_OF_THREADS = 2;
    public static final byte[] UDP_BUFFER = new byte[4096];
    public static final String MULTICAST_IP = "224.0.0.1";
    public static final int MULTICAST_PORT = 8888;

    public static String datagramSocketToString(DatagramSocket datagramSocket) {
        return "DatagramSocket[localaddr=" + datagramSocket.getLocalAddress() + ",localport=" + datagramSocket.getLocalPort() + "]";
    }

    public static ParsedCommand parseCommand(String input) throws Exception {
        if (input == null) {
            throw new Exception("Invalid input");
        }

        input = input.trim();

        if (input.isEmpty()) {
            throw new Exception("Invalid input");
        }

        String[] inputArr = input.split(" ");

        if (inputArr.length < 1) {
            throw new Exception("Invalid input");
        }

        String command = inputArr[0].trim();

        CommandEnum commandEnum = CommandEnum.getByValue(command);

        if (commandEnum == null) {
            throw new Exception("Invalid command");
        }

        ParsedCommand parsedCommand = new ParsedCommand();
        parsedCommand.setCommandEnum(commandEnum);

        if (commandEnum == CommandEnum.SET || commandEnum == CommandEnum.GET || commandEnum == CommandEnum.DEL) {
            if (inputArr.length < 2) {
                throw new Exception("Invalid input");
            }

            String key = inputArr[1].trim();

            if (key.isEmpty()) {
                throw new Exception("Invalid key");
            }

            parsedCommand.setKey(key);

            if (commandEnum == CommandEnum.SET) {
                if (inputArr.length != 3) {
                    throw new Exception("Invalid input");
                }

                String value = inputArr[2].trim();

                if (value.isEmpty()) {
                    throw new Exception("Invalid value");
                }

                parsedCommand.setValue(value);
            }
        }

        return parsedCommand;
    }

}

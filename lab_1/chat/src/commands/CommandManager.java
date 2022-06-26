package commands;

import other.Data;

import static commands.CommandType.*;

public class CommandManager {

    private static final String helpMsg =
            "*********************************\n" +
            "Q - quit chat\n" +
            "U - send UDP message\n" +
            "M - multicast messages\n" +
            "D - default mode (TCP)\n" +
            "*********************************\n";

    private CommandManager() {}

    public static void showHelp() {
        System.out.println(helpMsg);
    }

    public static CommandType getCommandType(String command) {
        CommandType result;

        switch(command.trim())
        {
            case "D":
            case "d":
                result = DEFAULT_MODE;
                break;

            case Data.QUIT_KEY:
            case "Q":
            case "q":
                result = QUIT;
                break;

            case Data.UDP_KEY:
                result = UDP_RECEIVE;
                break;

            case "U":
            case "u":
                result = UDP_SEND;
                break;

            case Data.MULTICAST_KEY:
                result = MULTICAST_OPEN;
                break;

            case "M":
            case "m":
                result = MULTICAST;
                break;

            default:
                result = INVALID_COMMAND;
        }

        return result;
    }
}

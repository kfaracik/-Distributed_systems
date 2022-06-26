package other;

public class Data {

    // TCP
    public static final int PORT = 80;
    public static final String HOST = "localhost";

    public static final String USER_NAME_KEY = "com.agh.client_server.USER_NAME";                   // initial message to server
    public static final String QUIT_KEY = "com.agh.client_server.QUIT";
    public static final String UDP_KEY = "com.agh.client_server.UDP";                               // init UDP chanel
    public static final String CLIENT_PORT_PING_KEY = "com.agh.client_server.CLIENT_PORT_PING";     // user reply after UDP_KEY receive from server
    public static final String MULTICAST_KEY = "com.agh.client_server.MULTICAST";

    // Multicast
    public static final String MULTICAST_GROUP_ADDRESS = "230.0.0.0";
    public static final int MULTICAST_PORT = 5005;
    public static final String MULTICAST_END = "com.agh.client_server.MULTICAST_END";

    private Data() {}
}

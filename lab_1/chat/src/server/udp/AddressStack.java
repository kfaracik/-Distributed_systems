package server.udp;

import java.net.InetAddress;
import java.util.Stack;

public class AddressStack {

    private final Stack<Integer> portStack;
    private final Stack<InetAddress> addressStack;

    public AddressStack() {
        portStack = new Stack<>();
        addressStack = new Stack<>();
    }

    public void push(int port, InetAddress address) {
        portStack.push(port);
        addressStack.push(address);
    }

    public int popPrt() {
        return portStack.pop();
    }

    public InetAddress popAddress() {
        return addressStack.pop();
    }

    public Boolean empty() {
        return portStack.empty() || addressStack.empty();
    }
}

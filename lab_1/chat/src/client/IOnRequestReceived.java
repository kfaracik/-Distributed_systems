package client;

public interface IOnRequestReceived {
    public void onUdpReceived();
    public void onMulticastReceived();
}

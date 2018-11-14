/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Link;

import WebService.Acquaintance.ILink;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Peterzxcvbnm
 * @since 2018-10-24
 * @version 1.0
 */
public class ClientConnection {

    private ServerSocket server;
    private ILink link;
    //private MessageParser messageParser = new MessageParser;
    //private Encrypt encrypt = new Encrypt;

    public ClientConnection(String ipAddress, ILink link) throws Exception {
        //Create a socket with the passed ip address
        if (ipAddress != null && !ipAddress.isEmpty()) {
            this.server = new ServerSocket(2345, 1, InetAddress.getByName(ipAddress));
        } //If the method is not passed an ip address assign one automatically
        else {
            this.server = new ServerSocket(2345, 1, InetAddress.getLocalHost());
        }
        this.link = link;
    }

    /**
     * Method to create a connection to the client and make a thread for the
     * client to use
     *
     * @throws Exception
     */
    public void establishCommunication() throws Exception {
        Socket client = this.server.accept(); //Accept a client
        Runnable thread = new HandleConnection(client, link); //Create a thread to service the client
        new Thread(thread).start(); //Start the thread
    }

    public InetAddress getSocketAddress() {
        return this.server.getInetAddress();
    }

    public int getPort() {
        return this.server.getLocalPort();
    }
}

class HandleConnection implements Runnable {

    private Socket socket; //Socket for connection
    private ILink link;

    public HandleConnection(Socket socket, ILink link) {
        this.socket = socket;
        this.link = link;
    }

    @Override
    public void run() {
        //Show the no. of users connected
        System.out.println("\r\nCurrent users: " + (java.lang.Thread.activeCount() - 1));
        String data = null;
        //Get the ip of the client
        String clientAddress = socket.getInetAddress().getHostAddress();
        //Print the ip of the client
        System.out.println("New connection from " + clientAddress);

        //Communicate with the client
        try {
            //PrintWriter to create a response to the client
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            //Get the message from the client
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while ((data = in.readLine()) != null) {
                //Print the message from the client
                System.out.println("Client says: " + data);
                //Send the response to the client
                out.println(link.messageParser(data));

            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        //When the client disconnects print the decremented no. of users
        System.out.println("Current users: " + (java.lang.Thread.activeCount() - 2));
    }
}

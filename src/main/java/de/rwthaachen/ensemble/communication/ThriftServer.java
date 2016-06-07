package de.rwthaachen.ensemble.communication;

import de.rwthaachen.ensemble.orchestration.Controller;
import de.rwthaachen.ensemble.orchestration.Pipeline;

// Thrift java libraries 
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
// Thrift client-side code for registering w/ sirius
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

/**
 * Starts the ensemble server and listens for requests.
 */
public class ThriftServer {
    private static void connectToCMD() {
        QueryInput query_input = new QueryInput();
        query_input.type = "ensemble";
        query_input.data = new ArrayList<String>();
        query_input.data.add("localhost");
        query_input.tags = new ArrayList<String>();
        query_input.tags.add("9090");
        QuerySpec spec = new QuerySpec();
        spec.content = new ArrayList<QueryInput>();
        spec.content.add(query_input);
        // Initialize thrift objects.
        TTransport transport = new TSocket("localhost", 8080);
        TProtocol protocol = new TBinaryProtocol(new TFramedTransport(transport));
        LucidaService.Client client = new LucidaService.Client(protocol);
        try {
            transport.open();
            System.out.println("Connecting to CMD at port " + 8080);
            // Register itself to CMD.
            client.create("", spec);
            transport.close();
            System.out.println("Successfully connected to CMD");
        } catch (TException x) {
            x.printStackTrace();
        }
    }
    
    /** 
     * Entry point for ensemble.
     */
    public static void setup(Controller controller)
            throws TTransportException, IOException, InterruptedException { 
        connectToCMD();
        TProcessor proc = new LucidaService.AsyncProcessor(
                new ThriftHandler.AsyncThriftHandler(controller));
        TNonblockingServerTransport transport = new TNonblockingServerSocket(9090);
        TThreadedSelectorServer.Args arguments = new TThreadedSelectorServer.Args(transport)
        .processor(proc)    
        .protocolFactory(new TBinaryProtocol.Factory())
        .transportFactory(new TFramedTransport.Factory());
        final TThreadedSelectorServer server = new TThreadedSelectorServer(arguments);
        System.out.println("ensemble at port 9090");
        server.serve();
    }
}

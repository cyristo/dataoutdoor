package dataoutdoor.exec;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import dataoutdoor.common.Utils;

public class HttpRunner {
    // Base URI the Grizzly HTTP server will listen on
    private static String baseURI = Utils.getProperty("dataoutdoor.baseuri");
	
    
    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Data Outdoor started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", baseURI));
        System.in.read();
        server.shutdown();
 
    }
    
    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.example package
        final ResourceConfig rc = new ResourceConfig().packages("dataoutdoor.exec");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(baseURI), rc);
    }


}

package server;

import filters.DebugFilter;
import filters.ExceptionLogger;
import java.net.URI;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.bridge.SLF4JBridgeHandler;
import resources.CouponListResource;
import resources.CouponResource;
import resources.CustomerResource;
import resources.PointsResource;
import resources.TransactionListResource;
import resources.TransactionResource;

public class RestServer {

    public static void main(String[] args) throws Exception {

        // configure the unified logger
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        ResourceConfig config = new ResourceConfig();

        // add a debug filter that prints request details
        config.register(DebugFilter.class);
        config.register(ExceptionLogger.class);

        // add our resource classes
        config.register(CustomerResource.class);
        config.register(TransactionResource.class);
        config.register(CouponResource.class);
        config.register(TransactionListResource.class);
        config.register(CouponListResource.class);
        config.register(PointsResource.class);

        // define the URI that the server will use
        URI baseUri = new URI("http://localhost:8081/");

        // start the server (via the built-in Java HTTP server)
        JdkHttpServerFactory.createHttpServer(baseUri, config);

        System.out.println("REST Service Ready on http://localhost:8081/");
    }

}
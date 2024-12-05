package network.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import api2.*;

public class GrpcServer {

    private Server server;
    private final int port;

    public GrpcServer(int port, ComputationCoordinator coordinator, DataStore dataStore) {
        this.port = port;
        this.server = ServerBuilder.forPort(port)
            .addService(new GrpcComputationService(coordinator, dataStore))
                .build();
    }

    public void start() throws IOException {
        server.start();
        System.out.println("gRPC Server started on port " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server.");
            GrpcServer.this.stop();
        }));
    }

    public void stop() {
        if (server !=null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        DataStore dataStore = new DataStoreEmpty(new input());
        ComputationCoordinator coordinator = new ComputationCoordinatorEmpty(dataStore, new ComputeEngineEmpty(new input()));
        GrpcServer server = new GrpcServer(50051, coordinator, dataStore);
        server.start();
        server.blockUntilShutdown();
    }
}

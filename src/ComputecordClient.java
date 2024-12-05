import java.util.concurrent.TimeUnit;
import api2.ComputationServiceGrpc;
import api2.ComputationServiceGrpc.ComputationServiceBlockingStub;
import api2.ComputationServiceOuterClass;
import api2.ComputationServiceOuterClass.ComputeRequest;
import api2.ComputationServiceOuterClass.ComputeResponse;
import io.grpc.Channel;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
 
//call the coordite 
public class ComputecordClient { // Boilerplate TODO: change to <servicename>Client //
    private final ComputationServiceBlockingStub blockingStub; // Boilerplate TODO: update to appropriate blocking stub

    public ComputecordClient(Channel channel) {
        blockingStub = ComputationServiceGrpc.newBlockingStub(channel);  // Boilerplate TODO: update to appropriate blocking stub
    }

    // Boilerplate TODO: replace this method with actual client call/response logic
    public void order() {        
        ComputeRequest request = ComputeRequest.newBuilder().build();
        ComputeResponse response;
        try {
            response = blockingStub.compute(request);
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
            return;
        }
        if (response.hasErrorMessage()) {
            System.err.println("Error: " + response.getErrorMessage());
        } else {
            System.out.println("Response: " + response);
        }
    }

    public static void main(String[] args) throws Exception {
        String target = "localhost:50051";  // Boilerplate TODO: make sure the server/port match the server/port you want to connect to

        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create())
                .build();
        try {
            ComputecordClient client = new ComputecordClient(channel); // Boilerplate TODO: update to this class name
            client.order();
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}

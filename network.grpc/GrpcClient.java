import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import network.grpc.ComputationServiceGrpc;
import network.grpc.ComputeRequest;
import network.grpc.ComputeResponse;

public class GrpcClient {
    private final ComputationServiceGrpc.ComputationServiceBlockingStub blockingStub;

    public GrpcClient(ManagedChannel channel) {
        this.blockingStub = ComputationServiceGrpc.newBlockingStub(channel);
    }

    public void compute(List<Integer> numbers, String outputFile, String delimiter) {
        ComputeRequest request = ComputeRequest.newBuilder()
                .addAllInputList(numbers)
                .build();
        
        try {
            ComputeResponse response = blockingStub.compute(request);
            String result = response.getResult();
            writeOutput(result, outputFile, delimiter);
            System.out.println("Computation succeeded. Result written to " + outputFile);
        } catch (Exception e) {
            System.err.println("Computation failed: " + e.getMessage());
        }
    }

    private void writeOutput(String result, String outputFile, String delimiter) {
        try (FileWriter writer = new FileWriter(new File(outputFile))) {
            writer.write(String.join(delimiter, Arrays.asList(result.split(","))));
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a list of numbers separated by spaces: ");
        List<Integer> numbers = Arrays.asList(scanner.nextLine().split(" ")).stream()
                .map(Integer::parseInt).toList();

        System.out.print("Enter output file name (e.g., result.txt): ");
        String outputFile = scanner.nextLine();

        System.out.print("Enter a delimiter for the output file (optional, default is comma): ");
        String delimiter = scanner.nextLine().isEmpty() ? "," : scanner.nextLine();

        String target = "localhost:50051"; //Will have to set up more properly on my end to make sure localhost works, but could just be error on my end
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();

        try {
            GrpcClient client = new GrpcClient(channel);
            client.compute(numbers, outputFile, delimiter);
        } finally {
            channel.shutdownNow();
        }
    }
}

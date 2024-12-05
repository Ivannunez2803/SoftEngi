package network.grpc;

import api2.*;
import io.grpc.stub.StreamObserver;

public class GrpcComputationService extends ComputationServiceGrpc.ComutationServiceImplBase {

    private final ComputationCoordinator coordinator;
    private final DataStore dataStore;

    public GrpcComputationService(ComputationCoordinator coordinator, DataStore dataStore) {
        this.coordinator = coordinator;
        this.dataStore = dataStore;
    }

    @Override
    public void compute(ComputeRequest request, StreamObserver<ComputeResponse> responseObserver) {
        try {
            ComputeResult result = coordinator.compute(new api2.ComputeRequest(request.getInputList()));
            String output = result.getOutput();
            ComputeResponse response = ComputeResponse.newBuilder().setResult(output).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void readData(ReadRequest request, StreamObserver<ReadRepsonse> responseObserver) {
        String data = dataStore.read(null); 
        ReadResponse response = ReadResponse.newBuilder().setData(data).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void AppendResult(AppendRequest request, StreamObserver<AppendResponse> responseObserver) {
        WriteResult writeResult = dataStore.appendSingleResult(null, request.getResult());
        AppendResponse response = AppendResponse.newBuilder()
            .setSuccess(writeResult.getStatus() == WriteResultStatus.SUCCESS)
            .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

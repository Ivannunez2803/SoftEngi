import io.grpc.stub.StreamObserver;

import java.util.List;

import api2.ComputationServiceGrpc;
import api2.ComputationServiceOuterClass;
import api2.ComputationServiceOuterClass.input;

public class ComputationCordimpl extends ComputationServiceGrpc.ComputationServiceImplBase {

    @Override
    public void compute(ComputationServiceOuterClass.ComputeRequest request,
                        StreamObserver<ComputationServiceOuterClass.ComputeResponse> responseObserver) {
        
         input inputList = request.getObj();
         List<Integer> a = inputList.getListList();
        int result = 0;
        for (int i = 0; i < a.size(); i++) {
            result += inputList.getList(i);
        }

        ComputationServiceOuterClass.ComputeResponse response = ComputationServiceOuterClass.ComputeResponse
                .newBuilder()
                .setResult(""+result)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void readData(ComputationServiceOuterClass.ReadRequest request,
                         StreamObserver<ComputationServiceOuterClass.ReadResponse> responseObserver) {
        
        ComputationServiceOuterClass.ReadResponse response = ComputationServiceOuterClass.ReadResponse
                .newBuilder()
                .setData("Sample data")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void appendResult(ComputationServiceOuterClass.AppendRequest request,
                             StreamObserver<ComputationServiceOuterClass.AppendResponse> responseObserver) {
        
        ComputationServiceOuterClass.AppendResponse response = ComputationServiceOuterClass.AppendResponse
                .newBuilder()
                .setSuccess(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
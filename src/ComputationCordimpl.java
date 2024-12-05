import io.grpc.stub.StreamObserver;

import java.nio.ByteBuffer;
import java.util.List;

import api2.ComputationCoordinatorEmpty;
import api2.ComputationServiceGrpc;
import api2.ComputationServiceOuterClass;
import api2.ComputeEngineEmpty;
import api2.ComputeRequest;
import api2.DataStoreEmpty;
import api2.ComputationServiceOuterClass.ComputeResponse;
import api2.ComputationServiceOuterClass.ComputeResponse.Builder;
import api2.ComputationServiceOuterClass.input;
import api2.ComputationServiceOuterClass.output;

public class ComputationCordimpl extends ComputationServiceGrpc.ComputationServiceImplBase {
    // call coordaites
    DataStoreEmpty store = new DataStoreEmpty(null);
    ComputeEngineEmpty engine = new ComputeEngineEmpty(null);
    ComputationCoordinatorEmpty cord = new ComputationCoordinatorEmpty(store,engine);
    @Override
    public void compute(ComputationServiceOuterClass.ComputeRequest requestg, StreamObserver<ComputationServiceOuterClass.ComputeResponse> out) {
    Builder result;
    try {
         output output = requestg.getObj2();
         api2.input actinput = new api2.input(requestg.getObj().getListList());
         api2.output actoutput = new api2.output(requestg.getObj2().getOut());
         ComputeRequest hold = new ComputeRequest(actinput,actoutput);
         cord.compute(hold);
        result = api2.ComputationServiceOuterClass.ComputeResponse.newBuilder().setResult("true");
        System.out.println("done with computation");
    } catch (Exception e) {
        e.printStackTrace();
        result = ComputeResponse.newBuilder().setResult("false");
    }
    // convent from grpc class back into class
    // in respone summor or fail if respone also send compute result.
    // send back the respones out.onNext();
    out.onNext(result.build());
    out.onCompleted();
    }
}
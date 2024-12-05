//stuff from api2
import api2.ComputationCoordinator;
import api2.ComputationCoordinatorEmpty;
import api2.ComputeEngine;
import api2.ComputeEngineEmpty;
import api2.ComputeRequest;
import api2.ComputeResult;
import api2.DataStore;
import api2.input;
import api2.output;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
//junit
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class ComputationCoordinatorPrototypeSmoke{

    // var setup 
    private ComputationCoordinatorEmpty coordinatorPrototype;
    private ComputationCoordinator mockedApi;

    private DataStorePrototypeSmoke mockds;

    private ComputeEnginePrototypeSmoke mockengine;

    private input mockedInputConfig;
    private output mockedOutputConfig;

    private ComputeRequest mockedRequest;
    private ComputeResult mockedResult;

    //Initialization
    @BeforeEach
    public void setUp() { //create mock of each
        DataStore mockDs = mock(DataStore.class);
        ComputeEngine mockce = mock(ComputeEngine.class);
        coordinatorPrototype = new ComputationCoordinatorEmpty(mockDs,mockce);//obj
        mockedApi = mock(ComputationCoordinator.class);
        mockedInputConfig = mock(input.class);
        mockedOutputConfig = mock(output.class);
        mockedRequest = new ComputeRequest(mockedInputConfig, mockedOutputConfig, ',');
        mockedResult = mock(ComputeResult.class);
    }


    @Test
    public void testPrototype() {
        // Set up the behavior for the mock
        when(mockedApi.compute(mockedRequest)).thenReturn(mockedResult);
        when(mockedResult.getStatus()).thenReturn(ComputeResult.SUCCESS.getStatus()); // Assuming ComputeStatus has a constructor that takes a boolean

        // Call the method under test
        mockedResult = coordinatorPrototype.compute(mockedRequest);

        // Check the result status
        assertTrue(mockedResult.getStatus().isSuccess());
    }
}

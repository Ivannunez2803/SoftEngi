
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import api2.ComputationCoordinatorEmpty;
import api2.DataStore;
import api2.ComputeEngine;
import api2.DataStoreEmpty;
import api2.input;
import api2.InputConfig;
import api2.ComputeEngineEmpty;
import api2.ComputeResult;
import api2.ComputeRequest;

public class Testr {

    @Test
    public void testComputeWithEmptyInputList() {
        DataStore data = new DataStoreEmpty(new input(new ArrayList<>())); 
        ComputeEngine engine = new ComputeEngineEmpty(null);
        ComputationCoordinatorEmpty core = new ComputationCoordinatorEmpty(data,engine);
        ComputeRequest user = new ComputeRequest(new input(new ArrayList<>(Arrays.asList(1, 2, 3))), null);
        ComputeResult result = core.compute(user);

        // Assert
        assertEquals(ComputeResult.SUCCESS, result); // Expecting failure when input list is empty
    }
}

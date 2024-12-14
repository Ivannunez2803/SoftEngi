
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import api2.DataStoreEmpty;
import api2.WriteResult;
import api2.Input;
import api2.Output;

import java.io.*;
class DataStorePrototypeSmoke {
    @Mock
    private Input mockInput;

    @Mock
    private Output mockOutput;

    private DataStoreEmpty dataStore;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dataStore = new DataStoreEmpty(mockInput);
    }
    @Test
    WriteResult testAppendSingleResult_Success() throws IOException {
        File tempFile = new File("Datastore_Success.txt");
        when(mockOutput.getOutput()).thenReturn(tempFile.getAbsolutePath());
        String resultData = "TestResult";
        WriteResult result = dataStore.appendSingleResult(mockOutput, resultData);
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            String line = reader.readLine();
            assertNotNull(line);
            assertTrue(line.contains("TestResultresults"));
        }
        assertEquals(WriteResult.WriteResultStatus.SUCCESS, result.getStatus());
        return result;
    }
}
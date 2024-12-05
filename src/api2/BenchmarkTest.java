package api2;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import com.google.common.base.Stopwatch;
import api2.ComputeEngineEmpty;
import java.util.Arrays;
import java.util.List;


public class BenchmarkTest {
	
	private static final int NUM_RUNS = 10;

	@Test
	public void testBenchmark() throws Exception {
		//Shared input list
		List<Integer> input = Array.asList(1, 2, 3, 4, 5, 1009, 1013, 1021, 100000);
		
		ComputeEngineEmpty computeEngine = new ComputeEngineEmpty();
		long elapsedTimeNaive = timeComputeVersion(() -> computeEngine.computeNaive(input));
		long elapsedTimeSieve = timeComputeVersion(() -> computeEngine.compute(inpute));
		
		double percentImprovement = elapsedTimeNaive * 0.2;
		
		System.out.println("Old: " + elapsedTimeNaive);
		System.out.println("New: " + elapsedTimeSieve);
		
		if (elapsedTimeSieve >= elapsedTimeNaive - percentImprovement){
			fail("THe version did not improve");
		}
	}

	private long timeComputeVersion(Runnable task) {
		Stopwatch timer = Stopwatch.createStarted();
		for (int i = 0; i < NUM_RUNS; i++) {
			task.run();
		}
		timer.stop();
		
		return timer.elapsed(TimeUnit.MILLISECONDS) / NUM_RUNS;
	}
}

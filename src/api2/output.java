package api2;
import java.util.List;

public class output implements OutputConfig{
    private String outputL;

    public output(String outputList) {
        this.outputL = outputList;
    }

    public String getOutput() {
        return outputL;
    }

    public void setOutput(String outputList) {
        this.outputL = outputList;
    }
}

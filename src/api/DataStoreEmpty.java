package api;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import api.WriteResult.WriteResultStatus; 

public class DataStoreEmpty implements DataStore{

    @SuppressWarnings("unused")
    private Input received;
    @SuppressWarnings("unused")
    private Output send;
    private File myObj;

    public DataStoreEmpty(Input received){
        this.received = received;
    }

    @Override
    public String read(Input input) {
        StringBuilder objb = new StringBuilder();

        try (BufferedReader fileobj = new BufferedReader(new FileReader(myObj))) {
            String line;
            while ((line = fileobj.readLine()) != null) {
                objb.append(line).append(System.lineSeparator());
            }
        } catch (FileNotFoundException e) {
            throw new UnsupportedOperationException("File cant be found: " + myObj.getName(), e);
        } catch (IOException e) {
            throw new UnsupportedOperationException("Error reading the file: " + myObj.getName(), e);
        }

        return objb.toString();
    }
    @Override
    public WriteResult appendSingleResult(Output output, String result) {
        myObj = new File(output.getOutput());
        try (PrintWriter objw = new PrintWriter(new FileWriter(myObj, true))) {
            objw.println(result +"results");
            return new WriteResultImpl(WriteResultStatus.SUCCESS);
        } catch (FileNotFoundException e) {
            return new WriteResultImpl(WriteResultStatus.FAILURE);
        } catch (IOException e) {
            return new WriteResultImpl(WriteResultStatus.FAILURE);
        }
    }


}

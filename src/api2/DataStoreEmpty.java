package api2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;


import api2.WriteResult.WriteResultStatus;
public class DataStoreEmpty implements DataStore{

    private input Received;
    private output send;
    private File myObj;

    public DataStoreEmpty (input Received){
        this.Received = Received;
    }

    @Override
    public String read(input input) {
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
    public WriteResult appendSingleResult(output output, String result) {
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

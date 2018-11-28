package FromUrl;


import java.io.*;
import java.util.ArrayList;

public class LocalFile {
    public ArrayList<String> GetLogFromFile(String filePath) {
        BufferedReader br;
        ArrayList<String> res = new ArrayList<String>();
        try {
            br = new BufferedReader(new FileReader(filePath));
            try {
                String x;
                while ( (x = br.readLine()) != null ) {
                    // Printing out each line in the file
                    res.add(x);
                }
                for (int i = 0; i < res.size(); i++) {
                    String tmp = res.get(i);
                    int n = tmp.lastIndexOf(" ");
                    res.set(i, tmp.substring(21, n).trim());
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return res;
    }
}
package FromUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class FromUrl {

        private String _URL;

        public FromUrl(String URL) {
            this._URL = URL;
        }

        public ArrayList<String> read() throws IOException {
            ArrayList<String> res = new ArrayList<String>();
            URL website = new URL(_URL);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(website.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                res.add(inputLine);

            in.close();

            for (int i = 0; i < res.size(); i++) {
                String tmp = res.get(i);
                int n = tmp.lastIndexOf(" ");
                res.set(i, tmp.substring(21, n).trim());
            }
            return res;
        }
    }


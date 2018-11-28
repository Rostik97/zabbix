package FromUrl;




import java.util.ArrayList;

public class Mapper {

    private ArrayList<String> errors400;
    private ArrayList<String> errors;
    private ArrayList<String> errors500;
    private ArrayList<String> avg;
    private ArrayList<String> max;
    private ArrayList<String> ipCount;



    public Mapper(ArrayList<String> list) {
        errors = new ArrayList<String>();
        errors400 = new ArrayList<String>();
        errors500 = new ArrayList<String>();
        avg = new ArrayList<String>();
        max = new ArrayList<String>();
        ipCount = new ArrayList<String>();

        classificate(list);
        System.out.println("CODERRORS: " + errors);
        System.out.println();
        System.out.println("CODERROR400: " + errors400);
        System.out.println();
        System.out.println("CODERROR500: " + errors500);
        System.out.println();
        System.out.println("MAX: " + max);
        System.out.println();
        System.out.println("AVG: " + avg);
        System.out.println();
        System.out.println("IP_COUNT: " + ipCount);
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public ArrayList<String> getIpCount() {
        return ipCount;
    }


    public ArrayList<String> getErrors400() {
        return errors400;
    }


    public ArrayList<String> getAvg() {
        return avg;
    }


    public ArrayList<String> getMax() {
        return max;
    }


    public ArrayList<String> getErrors500() {
        return errors500;
    }


    private void classificate(ArrayList<String> base){
        for (String s :base) {
            if (s.contains("max")){
                max.add(s);
            } else if (s.contains("avg")){
                avg.add(s);
            } else if (s.contains("coderror.4")){
                errors400.add(s);
            } else if (s.contains("coderror.5")) {
                errors500.add(s);
            } else if (s.contains("coderror")) {
                errors.add(s);
            }else {
                ipCount.add(s);
            }
        }
    }
}
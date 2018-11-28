package ConnectZab;
import FromUrl.*;
import java.io.IOException;
import java.util.ArrayList;



public class Main {

    public static void main(String[] args) throws IOException {
        String login = args[0];
        String pass = args[1];
        String graphName = args[2];
        String url = args[3];
        String localGraphName = args[4];
        String localFilePath = args[5];
        String host = args[6];
        Config.setHost(host);
        Config.setLocalFilePath(localFilePath);
        Config.setLocalGraphName(localGraphName);
        Config.setUrl(url);
        Config.setGraphName(graphName);
        Config.setName(login);
        Config.setPass(pass);

        FromUrl reader = new FromUrl(Config.getUrl());
        Mapper mapUrl = new Mapper(reader.read());

        LocalFile read = new LocalFile();
        Mapper mapLocal = new Mapper(read.GetLogFromFile(Config.getLocalFilePath()));

        Create id = new Create();
        String hostid = id.getHostId(Config.getHost());
        String interfaceid = id.getInterfaceId(hostid);
        Config.setHostid(hostid);
        Config.setInterfaceid(interfaceid);

        //Create items from URL
        ArrayList<String> errorIdList400 = id.createItems(mapUrl.getErrors400());
        ArrayList<String> errorIdList500 = id.createItems(mapUrl.getErrors500());
        ArrayList<String> avgIdList = id.createItems(mapUrl.getAvg());
        ArrayList<String> countIdList = id.createItems(mapUrl.getIpCount());
        ArrayList<String> maxIdList = id.createItems(mapUrl.getMax());

        //Create graph from URL
        String graphMaxId = id.createGraph(maxIdList,Config.getGraphName()+".max");
        String graphIpCount = id.createGraph(countIdList,Config.getGraphName()+".count");
        String graphAvgId = id.createGraph(avgIdList, Config.getGraphName()+".avg");
        String grapherror400 = id.createGraph(errorIdList400,Config.getGraphName()+".coderror.400");
        String grapherror500 = id.createGraph(errorIdList500,Config.getGraphName()+".coderror.500");

        ArrayList<String> graphs = new ArrayList<String>();
        graphs.add(graphMaxId);
        graphs.add(graphIpCount);
        graphs.add(grapherror400);
        graphs.add(graphAvgId);
        graphs.add(grapherror500);
        //Create screen from URL
        String screenId = id.createScreen(Config.getGraphName() ,graphs);
        System.out.println("ScreenURL "+screenId);


        Create ids = new Create();
        //Create items from local file
        ArrayList<String> errorIdListLocal = ids.createItems(mapLocal.getErrors());
        ArrayList<String> avgIdListLocal = ids.createItems(mapLocal.getAvg());
        ArrayList<String> countIdListLocal = ids.createItems(mapLocal.getIpCount());
        ArrayList<String> maxIdListLocal = ids.createItems(mapLocal.getMax());

        //Create graph from local file
        String graphMaxIdLocal = ids.createGraph(maxIdListLocal,Config.getLocalGraphName()+".max");
        String graphIpCountLocal = ids.createGraph(countIdListLocal,Config.getLocalGraphName()+".count");
        String graphAvgIdLocal = ids.createGraph(avgIdListLocal, Config.getLocalGraphName()+".avg");
        String graphErrorLocal = ids.createGraph(errorIdListLocal,Config.getLocalGraphName()+".coderror");

        ArrayList<String> graphsLocal = new ArrayList<String>();
        graphsLocal.add(graphMaxIdLocal);
        graphsLocal.add(graphErrorLocal);
        graphsLocal.add(graphIpCountLocal);
        graphsLocal.add(graphAvgIdLocal);

       //Create screen from local file
        String screenIdFromLocal = ids.createScreen(Config.getLocalGraphName() ,graphsLocal);

        System.out.println("ScreenLocalFile " + screenIdFromLocal);



//        System.out.println("Graph MAX "+ graphMaxId);
//        System.out.println("Graph count "+graphIpCount);
//        System.out.println("Graph 500 "+grapherror500);
//        System.out.println("Graph 400 "+grapherror400);
//        System.out.println("Graph avg "+graphAvgId);

//       maxIdList = id.createItems(mapUrl.getMax());
//       avgIdList = id.createItems(mapUrl.getAvg());
//       countIdList = id.createItems(mapUrl.getIpCount());




        }


        }







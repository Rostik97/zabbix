package ConnectZab;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.github.hengyunabc.zabbix.api.DefaultZabbixApi;
import io.github.hengyunabc.zabbix.api.Request;
import io.github.hengyunabc.zabbix.api.RequestBuilder;
import java.util.ArrayList;


public class Create {
    public DefaultZabbixApi zabbixApi;


    public Create() {
        String url = "https://zabbix.it.loc/n13/api_jsonrpc.php";
        zabbixApi = new DefaultZabbixApi(url);
        zabbixApi.init();

        boolean login = zabbixApi.login(Config.getName(), Config.getPass());
        System.err.println("login: " + login);
    }

    public ArrayList<String> createItems(ArrayList<String> list) {

        ArrayList<String> resp = new ArrayList<String>();
        for (String s : list) {
            resp.add(createItem(s, s.replace(" ", ".")));
        }
        return resp;
    }
//    public ArrayList<String> itemUpdates (ArrayList<String> list) {
//
//        ArrayList<String> resp = new ArrayList<String>();
//        for (String s : list) {
//            resp.add(itemUpdate(s));
//        }
//        return resp;
//    }

    public String getHostId(String host) {
        JSONObject filter = new JSONObject();
        filter.put("host", host);
        Request request = RequestBuilder.newBuilder()
                .method("host.get")
                .paramEntry("filter", filter)
                .build();
        JSONObject response = zabbixApi.call(request);

        String hostid = response.getJSONArray("result")
                .getJSONObject(0)
                .getString("hostid");
        return hostid;
    }

    public String getInterfaceId(String hostid) {
        Request request = RequestBuilder.newBuilder()
                .method("hostinterface.get")
                .paramEntry("hostids", hostid)
                .build();
        JSONObject response = zabbixApi.call(request);

        String interfaceid = response.getJSONArray("result")
                .getJSONObject(0)
                .getString("interfaceid");
        return interfaceid;
    }


    public String createItem(String name, String key) {

        String itemids;
        JSONArray filter = new JSONArray();
        filter.add(0, "1301300000001042");
        filter.add(1, "1301300000001120");
        Request request = RequestBuilder.newBuilder()
                .method("item.create")
                .paramEntry("name", name)
                .paramEntry("key_", key)
                .paramEntry("hostid", Config.getHostid())
                .paramEntry("type", 2)
                .paramEntry("value_type", 0)
                .paramEntry("delay", 10)
                .paramEntry("applications", filter)
                .paramEntry("interfaceid", Config.getInterfaceid()).build();

        JSONObject response = zabbixApi.call(request);
//        System.err.println(response);

        if (JSON.toJSONString(response).contains("error")) {
            itemids = getItemsIds(key);
        } else {
            itemids = response.getJSONObject("result").getJSONArray("itemids").getString(0);
        }
        return itemids;
    }


    public String getItemsIds(String key) {
        JSONObject filter = new JSONObject();
        filter.put("key_", key);
        Request request = RequestBuilder.newBuilder()
                .method("item.get")
                .paramEntry("hostids", Config.getHostid())
                .paramEntry("search", filter)
                .build();
        JSONObject response = zabbixApi.call(request);
        String itemids = response
                .getJSONArray("result")
                .getJSONObject(0)
                .getString("itemid");
//        System.out.println(itemids);
        return itemids;
    }

    public String createGraph(ArrayList<String> itemids, String name) {
        String graphids;
        JSONArray gitems = new JSONArray();
        for (String s : itemids) {
            JSONObject items = new JSONObject();
            items.put("itemid", s);
            items.put("color", Config.getColor());
            gitems.add(items);
        }

        Request request = RequestBuilder.newBuilder()
                .method("graph.create")
                .paramEntry("name", name)
                .paramEntry("widht", 900)
                .paramEntry("height", 300)
                .paramEntry("gitems", gitems)
                .build();
//        System.err.println(request);
        JSONObject response = zabbixApi.call(request);
//        System.out.println(response);
        if (JSON.toJSONString(response).contains("error")) {
            graphids = getGraphId(name);
        } else {
            graphids = response.getJSONObject("result").getJSONArray("graphids").getString(0);
        }
        return graphids;
    }


    public String getGraphId(String name) {
        JSONObject filter = new JSONObject();
        filter.put("name", name);
        Request request = RequestBuilder.newBuilder()
                .method("graph.get")
                .paramEntry("hostids", Config.getHostid())
                .paramEntry("search", filter)
                .build();
        JSONObject response = zabbixApi.call(request);
        String graphId = response
                .getJSONArray("result")
                .getJSONObject(0)
                .getString("graphid");

        return graphId;

    }

    // Add or change param to items

    //        public String itemUpdate(String itemids){
//            JSONArray filter = new JSONArray();
//            filter.add(0, "1301300000001042");
//            filter.add(1,"1301300000001120");
//            Request request = RequestBuilder.newBuilder()
//                    .method("item.update")
//                    .paramEntry("itemid",itemids)
//                    .paramEntry("applications", filter)
//                    .build();
//            System.out.println(request);
//            JSONObject response = zabbixApi.call(request);
//
//            String itemid = response.getJSONObject("result").getJSONArray("itemids").getString(0);
//            System.out.println(itemid);
//            return itemid;
//        }
    public String createScreen(String name, ArrayList<String> graphs) {
        JSONArray items = new JSONArray();
        String screenid;
        int y = 0;
        int x = 0;
        for (String s : graphs) {
                    JSONObject obj = new JSONObject();
                    obj.put("resourcetype", 0);
                    obj.put("resourceid", s);
                    obj.put("width", 700);
                    obj.put("height",300);
                    obj.put("y", y);
                    obj.put("x", x);
                    items.add(obj);
            if (y < 2)
                y++;
            else if(y == 2){
                x++;
                y = 0;
            }
        }

        Request request = RequestBuilder.newBuilder()
                .method("screen.create")
                .paramEntry("name", name)
                .paramEntry("hsize", 4)
                .paramEntry("vsize", 5)
                .paramEntry("screenitems", items)
                .build();
//        System.err.println(request);
        JSONObject response = zabbixApi.call(request);
        System.err.println(response);
        if (JSON.toJSONString(response).contains("error")){
            screenid = "Screen already exist";
        } else {
            screenid = response.getJSONObject("result").getJSONArray("screenids").getString(0);
        }
        return screenid;
    }


}
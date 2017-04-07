package GraphBuilder.json_representations;

/**
 * Created by Francis O'Brien - 4/3/2017 - 19:48
 */

public class JsonParseTest {

    public static void main(String[] args) {
//        try {
//
//            Gson gson = new Gson();
//            JsonReader reader = new JsonReader(new FileReader("./run/verilog_designs/adder.json"));
//            JsonFile jf = gson.fromJson(reader, JsonFile.class);
//            jf.postInit();
//            jf.print();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        GraphBuilder.GraphBuilder.buildGraph("./run/verilog_designs/test.json").print();
    }

}

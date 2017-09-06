package GraphBuilder.json_representations;

import java.util.HashMap;

/**
 * Created by Francis O'Brien - 4/3/2017 - 19:41
 */

public class JsonFile {

    public HashMap<String, Module> modules;

    public void print(){
        for (Module m : modules.values()){
            m.print();
        }
    }

    public static String tabs(int tabs) {
        String str = "";
        for (int i = 0; i < tabs; i++){
            str += "\t";
        }
        return str;
    }

    public void postInit(){
        for (Module m : modules.values()){
            m.postInit();
        }
    }
}

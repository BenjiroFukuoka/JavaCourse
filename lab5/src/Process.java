import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

class Process {
    private static String path;
    private static Map<String,Integer> list = new HashMap<String, Integer>();
    Process(String path){
        Process.path = path;
    }
    private static boolean checkPath(){
        if(path != null)
            return false;
        else
            return true;
    }
    private static boolean open(File file){
        if(file.isFile())
            return true;
        else{
            System.out.println("Файл не найден!");
            return false;
        }
    }
    private static void giveUp(String[] data){
        for(int i = 0; i < data.length; ++i){
            if(list.containsKey(data[i])){
                int temp = list.get(data[i]) + 1;
                list.replace(data[i], temp);
            }
            else{
                list.put(data[i], 1);
            }
        }
    }
    static void read(String path) throws FileNotFoundException{
        if(checkPath()){
            File file = new File(path);
            if(open(file)){
                BufferedReader scan = new BufferedReader(new FileReader(path));
                String[] data = null;
                try {
                    String line;
                    while((line = scan.readLine()) != null){
                        data = line.split("\s");
                    }
                    giveUp(data);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(scan != null){
                        try {
                            scan.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    static void write() throws IOException{
        int x = 0;
        if(list != null){
            FileWriter write = new FileWriter("output.csv");
            list.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed());
            Map<String, Integer> sortedMap = list.entrySet().stream()
                        .sorted(Comparator.comparingInt(e -> -e.getValue()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (a, b) -> { throw new AssertionError(); },
                                LinkedHashMap::new
                        ));
            System.out.println(sortedMap);
            write.append("Word" + "," + "Frequency" + "," + "Frequency(%)" + '\n');
            for(var tmp : sortedMap.entrySet()){
                x = tmp.getValue();
                write.append(tmp.getKey() + "," + Integer.toString(x) + ","
                + Math.round((x*1.0/list.size())*Math.pow(10, 4))/Math.pow(10,4) +'\n');
            }
            if(write != null){
                write.flush();
                write.close();
            }
        }
        else System.out.println("Список пуст");
    }
}
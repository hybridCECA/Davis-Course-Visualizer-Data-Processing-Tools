import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlowChartGenerator {
    static ArrayList<String> completeClasses = new ArrayList<String>();
    static ArrayList<String> incompleteClasses = new ArrayList<String>();
    static int var = 1;

    private static boolean isComplete(String s){
        for (String element : completeClasses){
            if (s.equals(element)){
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        try {
            FileReader in = null;
            if(args.length > 0) {
                in = new FileReader(args[0]);
            }

            BufferedReader bufferreader = new BufferedReader(in);

            String currentGroup="";
            while (bufferreader.ready()) {
                String line = bufferreader.readLine();

                if(isClass(line)){
                    System.out.println(var + "[" + currentGroup + "]-->" + line);
                    incompleteClasses.add(line);
                } else {
                    currentGroup = line.replaceAll(";", "");
                    var++;
                    System.out.println("style " + var + " fill:#ddd");
                }
            }


        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        for (String className : incompleteClasses) {
            System.out.println("style " + className + " fill:#ade");
            String logic = getPrereqLogic(className);
            if (logic != null && !logic.equals("")) {
                generateFromLogic(className, logic);
            }
        }

    }

    private static void generateFromLogic(String className, String logic){
        int[] bounds = getInnerParen(logic);
        if ((bounds[0] == -1) &&(bounds[1] == -1)) {
            int[] e1 = getElementBounds(logic, 1);
            int[] e2 = getElementBounds(logic, 2);

            String object1 = logic.substring(e1[0]+1, e1[1]).replaceAll(" ", "_");
            if((e2[0] != -1)&&(e2[1] != -1)) {
                String logicOperator = logic.substring(e1[1]+1, e2[0]);
                String message = "";
                if(logicOperator.equals("&")){
                    message = "Both";
                } else if (logicOperator.equals("|")){
                    message = "One";
                }

                String object2 = logic.substring(e2[0]+1, e2[1]).replaceAll(" ", "_");
                System.out.println(object2 + "-->" + className + "[" + message + "]");
                System.out.println(object1 + "-->" + className + "[" + message + "]");
            } else {
                System.out.println(object1 + "-->" + className);
            }
        } else {
            var++;
            int subVar = var;
            String subVarS = "[" + var + "]";
            generateFromLogic(Integer.toString(subVar), logic.substring(bounds[0]+1, bounds[1]));
            String newLogic = logic.substring(0, bounds[0]) + subVarS + logic.substring(bounds[1]+1, logic.length());
            generateFromLogic(className, newLogic);
        }
    }

    // Starts at 1
    private static int[] getElementBounds(String s, int index){
        int[] bounds = {-1, -1};
        int pendingOpens = 0;

        int i=0;

        while(i < s.length()){
            char currentChar = s.charAt(i);
            if((currentChar == '{')||(currentChar == '[')){
                if(pendingOpens == 0){
                    index--;
                    if(index == 0){
                        bounds[0] = i;
                    }
                }

                pendingOpens++;
            } else if ((currentChar == '}')||(currentChar == ']')){
                pendingOpens--;

                if((pendingOpens == 0) && (index == 0)){
                    bounds[1] = i;
                    return bounds;
                }
            }

            i++;
        }

        return bounds;
    }

    private static int[] getInnerParen(String s){
        int[] bounds = {-1, -1};
        int index=0;

        while(index < s.length()){
            char currentChar = s.charAt(index);
            if(currentChar == '{'){
                bounds[0] = index;
            }
            if((currentChar == '}') && (bounds[0] != -1)){
                bounds[1] = index;

                return bounds;
            }
            index++;
        }

        return bounds;
    }


    private static boolean classExists(String s){
        try {
            FileReader prereq1 = new FileReader("prereq_1.txt");
            BufferedReader pr1reader = new BufferedReader(prereq1);
            FileReader noprereq1 = new FileReader("no_prereq_1.txt");
            BufferedReader nopr1reader = new BufferedReader(noprereq1);

            while (pr1reader.ready()) {
                String line = pr1reader.readLine();

                if(line.replaceAll(" ", "_").equals(s)) {
                    return true;
                }
            }

            while (nopr1reader.ready()) {
                String line = nopr1reader.readLine();

                if(line.replaceAll(" ", "_").equals(s)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }

    private static boolean isClass(String s){
       Matcher m = Pattern.compile("[A-Z][A-Z][A-Z]_[0-9][0-9][0-9]").matcher(s);
       return m.find();
    }

    private static String getPrereqLogic(String s){
        try {
            FileReader prereq1 = new FileReader("prereq_1.txt");
            BufferedReader pr1reader = new BufferedReader(prereq1);

            int i=1;
            while (pr1reader.ready()) {
                String line = pr1reader.readLine();

                if(line.replaceAll(" ", "_").equals(s)) {
                    FileReader processed = new FileReader("prereq_3_processed.txt");
                    BufferedReader processedReader = new BufferedReader(processed);

                    for (int counter = 0; counter < i-1; counter++) {
                        processedReader.readLine();
                    }

                    String logic = processedReader.readLine();

                    if (logic != null) {
                        return logic;
                    } else {
                        return "";
                    }



                }
                i++;
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
}

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CPA {
    public static void main(String[] args) {
        try {
            FileReader in = new FileReader("prereq_3_bracket.txt");

            BufferedReader bufferreader = new BufferedReader(in);

            while (bufferreader.ready()) {
                String line = bufferreader.readLine();

                System.out.println(operate(line));
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        //String output = operate("")

    }

    private static String operate(String s){
        int[] bounds = getInnerParen(s);

        if ((bounds[0] == -1) && (bounds[1] == -1)) {
            // And stage
            int i=1;
            int[] e1 = getElementBounds(s, i);
            int[] e2 = getElementBounds(s, i+1);

            while ((e2[0] != -1)&&(e2[1] != -1)){

                String between = s.substring(e1[1], e2[0]);
                if ((between.contains("&")) || (between.toLowerCase().contains(" and "))) {
                    String newString = s.substring(0, e1[0]) + "{" + s.substring(e1[0], e1[1]+1) + "&" + s.substring(e2[0], e2[1]+1) + "}" + s.substring(e2[1]+1, s.length());
                    s = newString;
                } else {
                    i++;
                }

                e1 = getElementBounds(s, i);
                e2 = getElementBounds(s, i+1);
            }

            // Or stage
            i=1;
            e1 = getElementBounds(s, i);
            e2 = getElementBounds(s, i+1);

            while ((e2[0] != -1)&&(e2[1] != -1)){

                String between = s.substring(e1[1], e2[0]);
                if ((between.toLowerCase().contains(" or "))) {
                    String newString = s.substring(0, e1[0]) + "{" + s.substring(e1[0], e1[1]+1) + "|" + s.substring(e2[0], e2[1]+1) + "}" + s.substring(e2[1]+1, s.length());
                    s = newString;
                } else {
                    i++;
                }

                e1 = getElementBounds(s, i);
                e2 = getElementBounds(s, i+1);
            }

            // Suggested stage

            i=1;
            e1 = getElementBounds(s, i);
            e2 = getElementBounds(s, i+1);

            while ((e1[0] != -1)&&(e1[1] != -1)){
                int end;
                if((e2[0] == -1)&&(e2[1] == -1)) {
                    end=s.length();

                } else {
                    end=e2[0];
                }

                String between = s.substring(e1[1], end);

                if ((between.toLowerCase().contains("recommended"))||(between.toLowerCase().contains("suggested"))) {
                    String newString = s.substring(0, e1[0]) + s.substring(end, s.length());
                    s = newString;
                } else {
                    i++;
                }

                e1 = getElementBounds(s, i);
                e2 = getElementBounds(s, i+1);
            }

            // Final and stage
            i=1;
            e1 = getElementBounds(s, i);
            e2 = getElementBounds(s, i+1);

            while ((e2[0] != -1)&&(e2[1] != -1)){
                String between = s.substring(e1[1], e2[0]);

                String newString = s.substring(0, e1[0]) + "{" + s.substring(e1[0], e1[1]+1) + "&" + s.substring(e2[0], e2[1]+1) + "}" + s.substring(e2[1]+1, s.length());
                s = newString;

                e1 = getElementBounds(s, i);
                e2 = getElementBounds(s, i+1);
            }

            int[] element = getElementBounds(s, 1);
            if((element[0] != -1)&&( element[1] != -1)){
                String newString = s.substring(element[0], element[1]+1);
                s = newString;
            } else {
                s = "";
            }

        } else {
            String inner = operate(s.substring(bounds[0]+1, bounds[1]));
            String newString = s.substring(0, bounds[0]) + inner + s.substring(bounds[1]+1, s.length());
            return operate(newString);
        }

        return s;
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
            if(currentChar == '('){
                bounds[0] = index;
            }
            if((currentChar == ')') && (bounds[0] != -1)){
                bounds[1] = index;

                return bounds;
            }
            index++;
        }

        return bounds;
    }
}

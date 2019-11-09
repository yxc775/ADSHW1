import java.io.*;

public class risingCity {
    public static void main(String[] args){
        BufferedReader reader = null;
        System.out.println(args[0]);
        File outfile = new File("output_file.txt");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile)));
        }
        catch(Exception e){
            System.out.println("invalid writing");
        }

        try {
            reader = new BufferedReader(new FileReader(args[0]));
        }
        catch(Exception e){
            System.out.println("No Such File " + args[0]);
        }

        try{
            String line = reader.readLine();
            while(line != null){
                writer.write(line);
                writer.newLine();
                line = reader.readLine();
            }
            reader.close();
            writer.close();
        }
        catch(Exception e){
            System.out.println("Encounter error during reading line");
        }

    }
}

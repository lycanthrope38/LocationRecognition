package tagger;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        String docFile = "trainDoc.txt";
        String trainData = "train.txt";
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader(docFile));
            bw = new BufferedWriter(new FileWriter(trainData,
                true));

            StringBuffer stringBuffer = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line);
            }
            String doc = stringBuffer.toString();
            String[] tokens = doc.split(" ");

            for (String token : tokens) {
                bw.write(token);
                bw.write(" ");
                bw.write("O");
                bw.write("\n");
            }
            bw.write("\n");

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

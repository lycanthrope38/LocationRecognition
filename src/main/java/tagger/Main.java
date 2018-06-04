package tagger;

import jvntagger.CRFTagger;
import vn.hus.nlp.sd.SentenceDetector;
import vn.hus.nlp.tokenizer.VietTokenizer;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        String sentDetectionFile = "models\\sentDetection\\VietnameseSD.bin.gz";
        SentenceDetector sd = null;
        try {
            sd = new SentenceDetector(sentDetectionFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String docFile = "trainDoc.txt";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(docFile));
            StringBuffer stringBuffer = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                stringBuffer.append(line);
            }
            String doc = stringBuffer.toString();

            String[] sentences = sd.sentDetect(doc);

            VietTokenizer tk = new VietTokenizer();
            CRFTagger crfTagger = new CRFTagger("model\\crfs");

            String trainData = "train.txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(trainData,
                true));
            for (String s : sentences) {
                String tokens = tk.segment(s);
//                System.out.println(s);
                String[] pairs = crfTagger.tagging(tokens).split(" ");

                for (String pair : pairs) {
                    String[] elems = pair.split("/");
                    String word = elems[0];
                    String tag = elems[1];
                    bw.append(word);
                    bw.append(" ");
                    bw.append(tag);
                    bw.append("\n");
                }
                bw.append("\n");
            }
            bw.close();
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

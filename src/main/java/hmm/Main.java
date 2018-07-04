package hmm;

import cc.mallet.fst.HMM;
import hmm.trainer.Trainer;
import vn.hus.nlp.sd.SentenceDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a text contains location names: ");
        String text = input.nextLine();

        try {
            SentenceDetector sd = new SentenceDetector
                ("models/sentDetection/VietnameseSD.bin.gz");
            String[] sentences = sd.sentDetect(text);

            HMM hmm = Trainer.loadHMM();
            if (hmm == null) {
                System.out.println("Failed to load HMM object from file");
                System.exit(1);
            }

            ArrayList<String> result = new ArrayList<>();
            for (String s : sentences) {
                ArrayList<String> locs = Helper.predict(hmm, s);
                result.addAll(locs);
            }

            System.out.println("Detected locations: ");
            for (String r : result) {
                System.out.println(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

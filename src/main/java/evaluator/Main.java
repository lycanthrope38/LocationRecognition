package evaluator;

import cc.mallet.fst.HMM;
import hmm.Helper;
import hmm.trainer.Trainer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String testFileName = "test.txt";
        ArrayList<String> sentences = new ArrayList<>();
        ArrayList<ArrayList<String>> actualResults = new ArrayList<>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(testFileName));
            String line = "";
            ArrayList<String> result = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                if ("".equals(line)) {
                    actualResults.add((ArrayList<String>) result.clone());
                    result.clear();
                    continue;
                }

                if (!line.startsWith("->")) {
                    sentences.add(line);
                } else {
                    result.add(line.substring(3));
                }
            }

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
        }

        HMM hmm = Trainer.loadHMM();
        System.out.println("=======");
        double averagePrecision = 0.0;
        double averageRecall = 0.0;

        for (int i = 0; i < sentences.size(); i++) {
            String st = sentences.get(i);

            ArrayList<String> predictedResult = Helper.predict(hmm, st);
            ArrayList<Entity> predicted = Evaluator.getEntities
                (predictedResult, st);
            ArrayList<Entity> groundTruth = Evaluator.getEntities
                (actualResults.get(i), st);

            System.out.println("Test case " + (i + 1));
            System.out.println("Predicted entities: " + predicted);
            System.out.println("Actual entities: " + groundTruth);
            Map<String, Double> evaluator = Evaluator.getPrecisionRecall
                (predicted, groundTruth);
            double precision = evaluator.get("precision");
            double recall = evaluator.get("recall");

            System.out.printf("=== Precision = %f - Recall = %f === %n",
                precision,
                recall);

            averagePrecision += precision;
            averageRecall += recall;
        }

        averagePrecision = averagePrecision / sentences.size();
        averageRecall = averageRecall / sentences.size();

        double f1Score = 2 * averagePrecision * averageRecall /
            (averagePrecision + averageRecall);

        System.out.println("==========================");
        System.out.printf("Average precision = %f, Average recall = %f, F1 " +
            "score = %f %n", averagePrecision, averageRecall, f1Score);
        System.out.println("==========================");
    }
}

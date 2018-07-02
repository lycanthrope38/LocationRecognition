package evaluator;

import helper.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Evaluator {
    public static ArrayList<Entity> getEntities(ArrayList<String> result,
                                                String originalText) {
        int currentIndex = 0;
        ArrayList<Entity> entities = new ArrayList<>();

        for (String r : result) {
            String temp = StringUtil.denormalize(r);
            int startIndex = originalText.indexOf(temp, currentIndex);
            int endIndex = startIndex + r.length() - 1;

            Entity e = new Entity(temp, startIndex, endIndex);
            entities.add(e);

//            System.out.println(e.toString());
            currentIndex = endIndex + 1;
        }

        return entities;
    }

    public static Map<String, Double> getPrecisionRecall(ArrayList<Entity> predicted,
                                                         ArrayList<Entity> groundTruth) {
        Map<String, Double> results = new HashMap<>();

        if (predicted.size() == 0) {
            results.put("precision", 0.0);
            results.put("recall", 0.0);
            return results;
        }

        int relevanceCount = 0;
        for (Entity e1 : groundTruth) {
            for (Entity e2 : predicted) {
                if (e1.equals(e2)) {
                    relevanceCount++;
                }
            }
        }

        double precision = (double) relevanceCount / predicted.size();
        double recall = (double) relevanceCount / groundTruth.size();
        results.put("precision", precision);
        results.put("recall", recall);

        return results;
    }
}

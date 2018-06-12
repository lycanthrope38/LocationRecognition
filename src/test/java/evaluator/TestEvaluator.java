package evaluator;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

public class TestEvaluator {
    @Test
    public void testEntity() {
        Entity e1 = new Entity("Đà Nẵng",55, 61);
        Entity e2 = new Entity("Đà Nẵng",55, 61);

        Assert.assertTrue(e1.equals(e2));
        Assert.assertTrue(!(e1 == e2));
    }

    @Test
    public void testGetEntities() {
        String original = "Đến Việt Nam, du khách không thể bỏ qua một địa " +
            "điểm du lịch nổi tiếng, đó là Đà Nẵng.";
        ArrayList<String> result = new ArrayList<>();
        result.add("Việt Nam");
        result.add("Đà Nẵng");

        ArrayList<Entity> entities = Evaluator.getEntities(result, original);
        System.out.println(entities);
    }

    @Test
    public void testEvaluate() {
        ArrayList<Entity> predicted = new ArrayList<>();
        ArrayList<Entity> groundTruth = new ArrayList<>();

        predicted.add(new Entity("A", 1, 9));
        predicted.add(new Entity("B", 15, 20));
        predicted.add(new Entity("D", 31, 40));

        groundTruth.add(new Entity("A", 1, 9));
        groundTruth.add(new Entity("B", 15, 20));
        groundTruth.add(new Entity("C", 21, 30));
        groundTruth.add(new Entity("E", 41, 55));

        Map<String, Double> results = Evaluator.getPrecisionRecall(predicted,
            groundTruth);
        double precision = results.getOrDefault("precision", 0.0);
        double recall = results.getOrDefault("recall", 0.0);

        System.out.printf("Precision = %f %n", precision);
        System.out.printf("Recall = %f %n", recall);
    }
}

package hmm;

import cc.mallet.fst.HMM;
import hmm.trainer.Trainer;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        HMM hmm = Trainer.loadHMM();

        if (hmm == null) {
            System.out.println("Failed to load HMM object from file.");
            System.exit(1);
        }

        String st = "Đến Đà Nẵng, du khách có thể tham quan các địa danh nổi tiếng tại đây như núi Bà Nà, bãi biển Mỹ Khê, Ngũ Hành Sơn...";

        ArrayList<String> result = Helper.predict(hmm, st);
        System.out.println(result);
    }
}

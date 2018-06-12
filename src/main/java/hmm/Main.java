package hmm;

import cc.mallet.fst.HMM;
import helper.StringUtil;
import hmm.trainer.Trainer;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        HMM hmm = Trainer.loadHMM();

        if (hmm == null) {
            System.out.println("Failed to load HMM object from file.");
            System.exit(1);
        }

        String st = "Đà Nẵng là địa điểm du lịch nổi tiếng ở Việt Nam";

        ArrayList<String> result = Helper.predict(hmm, StringUtil.normalize(st));
        System.out.println(result);
    }
}

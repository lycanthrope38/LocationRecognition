package hmm;

import cc.mallet.fst.HMM;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        HMM hmm = Helper.train("train.txt");
        String st = "Đà Nẵng là địa điểm du lịch nổi tiếng ở Việt Nam";

        ArrayList<String> result = Helper.predict(hmm, Helper.normalize(st));
        System.out.println(result);
    }
}

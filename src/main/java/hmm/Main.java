package hmm;

import cc.mallet.fst.HMM;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        HMM hmm = Helper.train("train.txt");
        String st = "Ăn chơi ở Đà Nẵng";
        Helper.predict(hmm, st);
    }
}

package hmm;

import cc.mallet.fst.HMM;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        HMM hmm = Helper.train("train.txt");
        String st = "Cảnh đẹp ở Đà Nẵng";
        String result = Helper.predict(hmm, st);
        System.out.println(result);
    }
}

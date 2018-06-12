package hmm;

import cc.mallet.fst.HMM;
import helper.StringUtil;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        HMM hmm = null;

        try {
            // Load HMM object from file
            fileInputStream = new FileInputStream("hmm.ser");
            objectInputStream = new ObjectInputStream(fileInputStream);
            hmm = (HMM) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (hmm == null) {
            System.out.println("Failed to load HMM object from file.");
            System.exit(1);
        }

        String st = "Đà Nẵng là địa điểm du lịch nổi tiếng ở Việt Nam";

        ArrayList<String> result = Helper.predict(hmm, StringUtil.normalize(st));
        System.out.println(result);
    }
}

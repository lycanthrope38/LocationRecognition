package hmm.trainer;

import cc.mallet.fst.HMM;
import hmm.Helper;

import java.io.*;

public class Trainer {
    // Serialize HMM object to file
    public static void train() {
        FileOutputStream  fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            HMM hmm = Helper.train("train.txt");
            fileOutputStream = new FileOutputStream("hmm.ser");
            objectOutputStream = new ObjectOutputStream
                (fileOutputStream);

            objectOutputStream.writeObject(hmm);
            System.out.println("HMM was saved to hmm.ser");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        train();
    }
}

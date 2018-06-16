package hmm.trainer;

import cc.mallet.fst.HMM;
import hmm.Helper;

import java.io.*;

public class Trainer {
    // Serialize HMM object to file
    private static void trainAndStoreHMM() {
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

    // De-serialize HMM object from file
    public static HMM loadHMM() {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        HMM hmm = null;

        try {
            // Load HMM object from file
            fileInputStream = new FileInputStream("hmm.ser");
            objectInputStream = new ObjectInputStream(fileInputStream);
            hmm = (HMM) objectInputStream.readObject();
            System.out.println("Loaded HMM object successfully.");
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

        return hmm;
    }

    public static void main(String[] args) {
        trainAndStoreHMM();
    }
}

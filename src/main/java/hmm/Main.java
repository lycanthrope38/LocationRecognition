package hmm;

import cc.mallet.fst.HMM;
import cc.mallet.fst.HMMTrainerByLikelihood;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.SimpleTaggerSentence2TokenSequence;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.iterator.LineGroupIterator;
import cc.mallet.types.Alphabet;
import cc.mallet.types.FeatureSequence;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Sequence;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        ArrayList<Pipe> pipes = new ArrayList<>();

        pipes.add(new SimpleTaggerSentence2TokenSequence());
        pipes.add(new TokenSequence2FeatureSequence());

        Pipe pipe = new SerialPipes(pipes);

        InstanceList trainingInstances;
        trainingInstances = new InstanceList(pipe);
        String trainingFilename = "train.txt";
        try {
            trainingInstances.addThruPipe(new LineGroupIterator(new BufferedReader(new InputStreamReader(new FileInputStream(trainingFilename), "UTF-8")), Pattern.compile("^\\s*$"), true));
            HMM hmm = new HMM(pipe, null);
            hmm.addStatesForLabelsConnectedAsIn(trainingInstances);
            HMMTrainerByLikelihood trainer = new HMMTrainerByLikelihood(hmm);
            trainer.train(trainingInstances, 10);

            Alphabet words = hmm.getInputPipe().getAlphabet();

            String st = "Mr. Vinken is chairman of Elsevier";
            String ws[] = st.split(" ");
            int fs[] = new int[ws.length];
            for (int i = 0; i < ws.length; i++) {
                fs[i] = words.lookupIndex(ws[i]);
            }
            FeatureSequence sp = new FeatureSequence(words, fs);

            Sequence<String> rs = hmm.transduce(sp);
            System.out.println(rs.toString());

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}

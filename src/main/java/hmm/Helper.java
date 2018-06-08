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

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Helper {

    public static HMM train(String trainingFilename) throws
        FileNotFoundException,
        UnsupportedEncodingException {

        ArrayList<Pipe> pipes = new ArrayList<>();

        pipes.add(new SimpleTaggerSentence2TokenSequence());
        pipes.add(new TokenSequence2FeatureSequence());

        Pipe pipe = new SerialPipes(pipes);

        InstanceList trainingInstances;
        trainingInstances = new InstanceList(pipe);

        trainingInstances.addThruPipe(new LineGroupIterator(new BufferedReader(
            new InputStreamReader(
                new FileInputStream(trainingFilename), "UTF-8")), Pattern.compile("^\\s*$"), true));

        HMM hmm = new HMM(pipe, null);
        hmm.addStatesForLabelsConnectedAsIn(trainingInstances);
//        hmm.addFullyConnectedStatesForBiLabels();
        HMMTrainerByLikelihood trainer = new HMMTrainerByLikelihood(hmm);
        trainer.train(trainingInstances, 10);

        return hmm;
    }

    public static void predict(HMM hmm, String sentence) {
        Alphabet words = hmm.getInputPipe().getAlphabet();
        String[] ws = sentence.split(" ");
        int[] fs = new int[ws.length];
        for (int i = 0; i < ws.length; i++) {
            fs[i] = words.lookupIndex(ws[i]);
        }

        FeatureSequence sp = new FeatureSequence(words, fs);
        Sequence<String> rs = hmm.transduce(sp);
        System.out.println(rs.toString());
    }
}

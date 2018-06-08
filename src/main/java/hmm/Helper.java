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
import java.util.Arrays;
import java.util.regex.Pattern;

public class Helper {

    public static ArrayList<String> extractTokens(Alphabet words, String
        input) {
        ArrayList<String> tokens = new ArrayList<>();
        for (String st : input.split(" ")) {
            if (words.contains(st)) {
                tokens.add(st);
            }
        }

        return tokens;
    }

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

    public static String predict(HMM hmm, String sentence) {
        Alphabet words = hmm.getInputPipe().getAlphabet();
        ArrayList<String> tokens = extractTokens(words, sentence);

        int[] fs = new int[tokens.size()];
        for (int i = 0; i < fs.length; i++) {
            fs[i] = words.lookupIndex(tokens.get(i));
        }

        FeatureSequence sp = new FeatureSequence(words, fs);
        Sequence<String> rs = hmm.transduce(sp);

        ArrayList<String> tags = new ArrayList<>(Arrays.asList(rs.toString()
            .trim().split(" ")));

        int bLocIndex = tags.indexOf("B-LOC");
        if (bLocIndex < 0) {
            return "";
        }

        int cLocIndex = tags.lastIndexOf("C-LOC");
        if (cLocIndex < 0) {
            return tokens.get(bLocIndex);
        }

        StringBuffer temp = new StringBuffer();
        for (int i = bLocIndex; i <= cLocIndex; i++) {
            temp.append(tokens.get(i));
            temp.append(" ");
        }

        return temp.toString().trim();
    }
}

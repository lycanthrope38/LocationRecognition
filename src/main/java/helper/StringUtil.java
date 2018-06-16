package helper;

public class StringUtil {
    public static String normalize(String input) {
        String output = input;
        output = output.replace("'", " ' ");
        output = output.replace("\"", " \" ");
        output = output.replace(",", " , ");
        output = output.replace(".", " . ");
        output = output.replace("-", " - ");
        output = output.replace("!", " ! ");

        return output;
    }

    public static String denormalize(String input) {
        String output = input;
        output = output.replace(" ,", ",");
        output = output.replace(" .", ".");

        return output;
    }
}

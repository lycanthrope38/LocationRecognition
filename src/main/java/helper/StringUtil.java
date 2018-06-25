package helper;

public class StringUtil {
    // Used before splitting string
    public static String normalize(String input) {
        String output = input;
        output = output.replace("'", " ' ");
        output = output.replace("\"", " \" ");
        output = output.replace(",", " , ");
        output = output.replace(".", " . ");
        output = output.replace("-", " - ");
        output = output.replace("!", " ! ");
        output = output.replace("(", " ( ");
        output = output.replace(")", " ) ");
        output = output.replace(":", " : ");

        return output;
    }

    // Used when concatenating text after predicting
    public static String denormalize(String input) {
        String output = input;
        output = output.replace(" ,", ",");
        output = output.replace(" .", ".");
        output = output.replace("( ", "(");
        output = output.replace(") ", ")");
        output = output.replace(" \"", "\"");
        output = output.replace(" - ", "-");
        output = output.replace(" !", "!");
        output = output.replace(" :", ":");

        return output;
    }
}

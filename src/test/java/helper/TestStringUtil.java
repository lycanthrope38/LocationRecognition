package helper;

import org.junit.Test;

public class TestStringUtil {
    @Test
    public void testNormalize() {
        String st = "Ở Việt Nam, Đà Nẵng là một điểm đến du lịch nổi tiếng.";
        System.out.println(StringUtil.normalize(st));
    }

    @Test
    public void testDenormalize() {
        String st = "Nam ,";
        System.out.println(StringUtil.denormalize(st));
    }
}

package demo;

import org.junit.Test;

/**
 * @data 10/31/2019 7:23 PM
 * 字符串倒叙
 **/
public class Text {

    @Test
    public void sort() {
        String s ="ctzidlqpfhshcudlqpoucbevznzhdiapdjdpsubamjxikabevzmajshqlqospx" +
                " djsjuqpskzimbahstrxsgdhfkglirpskasrtpcnstbqksocxhamqndbxvzueirotpshagsydbdnxkx " +
                "djskwlpoiuyttrefbxmskamzsdshdgyop";
        char[] ch = s.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = ch.length - 1; i >= 0; i--) {
            sb.append(ch[i]);
        }
        System.out.println(sb.toString());
    }
}

package org.yun.net.core.http;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yunye on 15-6-15.
 */
public class YunHttpUtil {
    public static String encodedString(InputStream inputStream) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = inputStream.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        String type = out.toString();
        return new String(out.toString().getBytes(), YunHttpConfig.ENCODED);
    }
}

package tw.supra.servtest;

/**
 * Created by supra on 16-7-27.
 */
public class Utils {

    /**
     * Kib的单位
     */
    private static final int K = 1024;
    /**
     * Mib的单位
     */
    private static final int M = K * K;
    /**
     * Gib的单位
     */
    private static final int G = K * M;
    /**
     * Tib的单位
     */
    private static final int T = K * G;

    private Utils() {
    }

    /**
     * 将byteCount转成人类可读的形式
     *
     * @param byteCount 字节数
     *
     * @return byteCount人类可读的形式
     */
    public static String humanReadable(long byteCount) {
        long abs = Math.abs(byteCount);
        if (abs < K) {
            return String.format("%dB", byteCount);
        } else if (abs < M) {
            return String.format("%.1fKiB", (Float.valueOf(byteCount) / K));
        } else if (abs < G) {
            return String.format("%.2fMiB", (Float.valueOf(byteCount) / M));
        } else if(abs < T){
            return String.format("%.2fGiB", (Float.valueOf(byteCount) / G));
        } else {
            return String.format("%.2fTiB", (Float.valueOf(byteCount) / T));
        }
    }


}

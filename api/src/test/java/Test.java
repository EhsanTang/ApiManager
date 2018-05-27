import cn.crap.utils.HttpPostGet;

/**
 * @author Ehsan
 * @date 2018/5/27 17:11
 */
public class Test {
    public static void main(String args[]) throws Exception{
        String str = HttpPostGet.get("https://hstech.crap.cn/mc/brandCategory.json", null, null);
        System.out.print(str);
    }
}

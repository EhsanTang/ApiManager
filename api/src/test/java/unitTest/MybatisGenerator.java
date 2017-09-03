package unitTest;


public class MybatisGenerator {
    public static void main(String args[]) throws Exception {
        System.out.println("-----------");
        String f = null;
        try {
            f = MybatisGenerator.class.getResource("/dao-generator.xml").getPath();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("------------"+f);
        GenerateCode.generate(f);
    }

}

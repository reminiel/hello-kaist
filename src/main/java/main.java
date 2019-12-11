public class main {
    public static void main(String[] args){
//        araLoader a = new araLoader();
//        if(a.login("reminiel", "gjrjs_0806") == true) {
//            Container c1 = a.fetchPage(1), c2;
//            int ano = c1.getArticles().get(0).getId();
//            System.out.println(ano);
//            c2 = a.fetchArticle(ano);
//            if(c2 == null){
//                System.out.println("Fetch failure");
//            }
//            Article ar = c2.getArticles().get(0);
//            for (int i = 0; i < ar.contentsSize(); i++) {
//                System.out.println("Datum 1 : " + ar.getContents(i).content);
//            }
//        }
//        return;

        otlLoader o = new otlLoader();
        Container c = o.fetch("CS350");
            if(c == null){
                System.out.println("Fetch failure");
            }
            Article ar = c.getArticles().get(0);
            for (int i = 0; i < ar.contentsSize(); i++) {
                System.out.println("Datum 1 : " + ar.getContents(i).content);
            }
            return;
    }
}
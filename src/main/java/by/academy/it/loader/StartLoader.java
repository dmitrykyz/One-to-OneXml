package by.academy.it.loader;

import by.academy.it.util.HibernateUtil;
import org.apache.log4j.Logger;
import java.util.Locale;

import static by.academy.it.loader.MenuLoadeManyToMany.menu;
import static by.academy.it.loader.MenuLoaderWorker.menuWorker;

public class StartLoader {
    private static Logger log = Logger.getLogger(StartLoader.class);
    public static HibernateUtil util = null;

    public static void main(String[] args) throws Exception {

        Locale.setDefault(Locale.US);
        util = HibernateUtil.getHibernateUtil();
        System.out.println("Start Menu");

        menu();
    }
}



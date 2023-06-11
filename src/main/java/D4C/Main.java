package D4C;


import D4C.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    private static EntityManager entityManager = HibernateUtil.getSessionFactory().createEntityManager();

    public static void main(String[] args) {
        logger.info("Hello world!");



    }
}



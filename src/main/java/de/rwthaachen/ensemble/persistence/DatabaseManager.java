package de.rwthaachen.ensemble.persistence;

import javax.persistence.*;
import java.util.List;

/**
 * Singleton that manages the database
 *
 * Can just save outputs from each into dedicated files with timestamps in dedicated directory
 * This should be easier than writing a fully dynamic DB with multiple versions by time and arbitrary backend numbers
 */
public class DatabaseManager {

    EntityManagerFactory factory = null;
    EntityManager entityManager = null;

    private static DatabaseManager instance;

    protected DatabaseManager() {

        try {
            factory = Persistence.createEntityManagerFactory("PersistenceUnit");
            entityManager = factory.createEntityManager();
            // To externally access database:
            // java -cp /home/fp_work/.m2/repository/com/h2database/h2/1.3.176/h2-1.3.176.jar org.h2.tools.Shell -url jdbc:h2:~/jpa
            // select * from T_QUESTION;
        } catch (Exception e) {
            //LOGGER.log(Level.SEVERE, e.getMessage(), e);
            e.printStackTrace();
        }

//        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//            public void run() {
//                if (entityManager != null) {
//                    entityManager.close();
//                }
//                if (factory != null) {
//                    factory.close();
//                }
//            }
//        }));
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }

        return instance;
    }

    public Long searchQuestionID(String question) {
//        List<Question> resultList = entityManager.createQuery(
//                "SELECT q FROM Question q WHERE q.question LIKE :questionText")
//                .setParameter("questionText", question)
//                .getResultList();
//
//        for(Question i : resultList) {
//            if (i.getInput().equals(question)) {
//                return i.getId();
//            }
//        }

        return null;
    }

    /**
     * Checks whether question is already in DB and only inserts if not
     * @return Whether transaction was successful
     */
    public boolean persistQuestion(de.rwthaachen.ensemble.model.Query question) {
        List<de.rwthaachen.ensemble.model.Query> resultList = entityManager.createQuery(
                "SELECT q FROM Query q WHERE q.question LIKE :questionText")
                .setParameter("questionText", question.getQuestion())
                .getResultList();

        for(de.rwthaachen.ensemble.model.Query i : resultList) {
            if (i.getQuestion().equals(question.getQuestion())) {
                return true;
            }
        }

        return persist(question);
    }

    public boolean persist(Object objectToBePersisted) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist( objectToBePersisted );
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        return false;
    }
}

package eu.deltasource.training.library;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class DatabaseResetter {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void clear(String tableName) {
        String primaryKey = tableName.substring(0, tableName.length()-1).concat("_id");
        entityManager
                .createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN " + primaryKey +" RESTART WITH 1;\n")
                .executeUpdate();
    }
}

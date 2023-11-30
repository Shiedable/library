package eu.deltasource.training.library;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class EntityManagerHelper {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void resetTableId(String tableName) {
        entityManager
                .createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN " +
                        tableName.substring(0, tableName.length()-1).concat("_id") +" RESTART WITH 1;\n")
                .executeUpdate();
    }
}

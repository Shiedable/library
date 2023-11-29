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
                .createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN author_id RESTART WITH 1;\n")
                .executeUpdate();
    }
}

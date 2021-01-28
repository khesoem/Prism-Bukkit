package me.botsko.prism.testHelpers;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import me.botsko.prism.PrismLogHandler;
import me.botsko.prism.database.PrismDataSource;
import me.botsko.prism.database.sql.derby.DerbySqlPrismDataSource;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Random;

/**
 * Created for the Prism-Bukkit Project.
 *
 * @author Narimm on
 */
public class TestPrismDataSource extends DerbySqlPrismDataSource {

    private static Random random = new Random();
    /**
     * Constructor.
     *
     * @param section Config
     */
    public TestPrismDataSource(ConfigurationSection section) {
        super(section);
    }

    @Override
    public PrismDataSource createDataSource() {
        dbConfig.setJdbcUrl("jdbc:derby:memory:test" + random.nextInt(100) + ";create=true");
        setPrefix("prism_");
        try {
            database = new HikariDataSource(dbConfig);
            createSettingsQuery();
            return this;
        } catch (HikariPool.PoolInitializationException e) {
            PrismLogHandler.warn("Hikari Pool did not Initialize: " + e.getMessage());
            database = null;
        }
        return this;

    }
}



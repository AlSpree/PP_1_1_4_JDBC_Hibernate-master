package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.schema.internal.StandardTableExporter;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String url = "jdbc:mysql://localhost:3306/test_first";
    private static final String name = "KATA";
    private static final String pass = "KATA_PP";
    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, name, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getUserSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            settings.setProperty("hibernate.connection.url", url);
            settings.setProperty("hibernate.connection.username", name);
            settings.setProperty("hibernate.connection.password", pass);
            settings.setProperty("hibernate.current_session_context_class","thread");
            settings.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            settings.setProperty("hibernate.show_sql", "true");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(settings)
                    .build();

            configuration.setProperties(settings);

            sessionFactory = configuration
                    .setProperties(settings)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
        } return sessionFactory;
    }
}


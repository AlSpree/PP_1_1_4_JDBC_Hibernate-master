package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory factory = Util.getUserSessionFactory();
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String sqlCommand = "CREATE TABLE IF NOT EXISTS users (\n" +
                "id serial PRIMARY KEY,\n" +
                "name varchar(50),\n" +
                "lastName varchar(50),\n" +
                "age tinyint)";

        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(sqlCommand)
                    .addEntity(User.class)
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sqlCommand = "DROP TABLE IF EXISTS users";

        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(sqlCommand)
                    .addEntity(User.class)
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = factory.getCurrentSession()) {
            User user =new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User добавлен");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                System.out.printf("User(id%d) удален\n", id);
            } else {
                System.out.printf("User с id=%d не существует\n", id);
            }
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String hqlCommand = "from User";
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            userList = session.createQuery(hqlCommand, User.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        userList.forEach(System.out::println);
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String sqlCommand = "TRUNCATE users";

        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(sqlCommand)
                    .addEntity(User.class)
                    .executeUpdate();
            session.getTransaction().commit();
            System.out.println("Данные таблицы удалены");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package by.pvt.pojo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.Service;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

public class PersonTest {

    Person person1 = new Person();
    Person person2 = new Person();
    SessionFactory sessionFactory;

    private void initPerson(Person person){

        person.setAge(35);
        person.setId(2);
        person.setSecondName("Vladislavov");
        person.setName("Vladislav");
        person.setDateOfBirth(new GregorianCalendar(1900,1,1).getTime());
    }

    @Before
    public void setUp() throws Exception {
        initPerson(person1);
        initPerson(person2);
    }

    private SessionFactory setUpHibernate(){

        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().configure()
                                                                .build();

        sessionFactory = new MetadataSources(standardServiceRegistry).buildMetadata()
                            .buildSessionFactory();

        return sessionFactory;
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void hashCodeTest() {

        assertEquals(person1.hashCode(),person2.hashCode());
        assertEquals(person2.hashCode(),person1.hashCode());

        assertEquals(person1.hashCode(),person1.hashCode());
        assertEquals(person2.hashCode(),person2.hashCode());
    }

    @Test
    public void equalsTest() {

        assertTrue(person1.equals(person1));
        assertTrue(person2.equals(person2));

        assertTrue(person1.equals(person2)&&person2.equals(person1));

        assertFalse(person1.equals(null));
        assertFalse(person2.equals(null));
    }

    @Test
    public void testHibernate(){

        Transaction tx = null;

        try(Session session = setUpHibernate().openSession()) {

            tx = session.beginTransaction();

            Serializable id1 = session.save(person1);
            Serializable id2 = session.save(person2);

            System.out.println("My POJO id1: "+id1);
            System.out.println("My POJO id2: "+id2);

            assertNotNull(id1);
            assertNotNull(id2);

            tx.commit();

        } catch (Exception e) {
            if(tx!=null){tx.rollback();}
        }

        Session session2 = setUpHibernate().openSession();
        session2.beginTransaction();
        List<Person> list = session2.createQuery("from Person ").list();
        for(Person p:list){
            System.out.println("Person: "+p);
        }
        session2.getTransaction().commit();
        session2.close();
    }
}
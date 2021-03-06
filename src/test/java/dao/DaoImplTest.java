package dao;

import by.pvt.pojo.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DaoImplTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void saveOrUpdate() {

        DaoImpl<Person> dao = new DaoImpl();

        assertNull(dao.saveOrUpdate(null));
        assertNotNull(dao.saveOrUpdate(new Person()));

        Person person = new Person();
        Person person2 = dao.saveOrUpdate(person);

        assertEquals(person,person2);

    }

    @Test
    public void load() {


    }

    @Test
    public void find() {
    }
}
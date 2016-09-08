package com.getjavajob.dao.DAOImpls;

import com.getjavajob.dao.DAOException;
import com.getjavajob.models.Account;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dao-context.xml", "classpath:dao-context-override.xml"})
public class AccountDAOTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AccountDao accountDAO;

    private Account expected;

    @Before
    public void setUp() throws FileNotFoundException, SQLException {
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/H2DB_test_query.sql"));
        expected = new Account();
        expected.setId(1);
        expected.setName("Daenerys");
        expected.setLastName("Targaryen");
        expected.setEmail("dt@gmail.com");
        expected.setPassword("motherOfDragons");
        expected.setDateOfBirth(new GregorianCalendar(1991, 2, 11).getTime());
        expected.setRole(Account.Role.ADMIN);
    }

    @Test
    public void createTest() throws Exception {
        Account account = new Account();
        account.setName("Ned");
        account.setLastName("Stark");
        account.setEmail("ns@mail.ru");
        account.setPassword("winterfell4ever");
        account.setDateOfBirth(new GregorianCalendar(2005, 11, 12).getTime());
        account.setRole(Account.Role.ADMIN);

        Account expected = accountDAO.create(account);

        Account actual = accountDAO.getById(expected.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void getByIdTest() throws DAOException {
        Account actual = accountDAO.getById(1);

        assertEquals(expected, actual);
    }

    @Test
    public void updateAccTest() throws Exception {

        Account acc = accountDAO.getById(1);
        assertEquals(acc.getRole(), Account.Role.ADMIN);

        acc.setRole(Account.Role.USER);

        accountDAO.update(acc);

        Account actual = accountDAO.getById(acc.getId());

        assertEquals(Account.Role.USER, actual.getRole());

    }

    @Test
    public void getAllAccsTest() throws Exception {

        Account account2 = new Account();
        account2.setName("Ned");
        account2.setLastName("Stark");
        account2.setEmail("ns@mail.ru");
        account2.setPassword("winterfell4ever");
        account2.setDateOfBirth(new GregorianCalendar(2005, 11, 12).getTime());
        account2.setRole(Account.Role.ADMIN);

        accountDAO.create(account2);
        List<Account> expected = accountDAO.getAll();

        System.out.println(expected);
        assertEquals(expected.size(), 6);
    }

    @Test
    public void getByEmailAndPasswordTest() throws DAOException {
        Account account = accountDAO.getByEmailAndPass("dt@gmail.com", "motherOfDragons");
        assertEquals(expected, account);
    }
}

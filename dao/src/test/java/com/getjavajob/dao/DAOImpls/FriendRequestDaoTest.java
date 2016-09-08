package com.getjavajob.dao.DAOImpls;

import com.getjavajob.models.Account;
import com.getjavajob.models.FriendRequest;
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
import java.util.List;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dao-context.xml", "classpath:dao-context-override.xml"})
public class FriendRequestDaoTest {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private FriendRequestDao friendRequestDao;

    @Autowired
    private DataSource dataSource;

    @Before
    public void setUp() throws FileNotFoundException, SQLException {
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/H2DB_test_query.sql"));
    }


    @Test
    public void deleteTest() throws Exception {
        FriendRequest request = friendRequestDao.getById(5);
        assertNotNull(request);

        friendRequestDao.delete(request);

        assertNull(friendRequestDao.getById(5));

    }

    @Test
    public void getAllRequestTest() throws Exception {
        Account owner = accountDao.getById(1);
        List<FriendRequest> requests = friendRequestDao.getAllRequest(owner);

        for (FriendRequest fr: requests){
            assertEquals(fr.getReceiver(), owner);
        }

        assertEquals(2, requests.size());
    }

    @Test
    public void createTest() throws Exception {
        Account sender = accountDao.getById(5);
        Account receiver = accountDao.getById(1);

        assertNotNull(sender);
        assertNotNull(receiver);

        FriendRequest request = new FriendRequest(receiver, sender);

        FriendRequest newRequest = friendRequestDao.create(request);

        FriendRequest actual = friendRequestDao.getById(newRequest.getId());

        assertEquals(newRequest, actual);
    }

    @Test
    public void getByIdTest() throws Exception {
        Account sender = accountDao.getById(1);
        Account receiver = accountDao.getById(2);

        FriendRequest expected = new FriendRequest(receiver, sender);

        FriendRequest actual = friendRequestDao.getById(1);

        assertEquals(expected, actual);
    }

}
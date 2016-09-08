package com.getjavajob.dao.DAOImpls;

import com.getjavajob.dao.DAOException;
import com.getjavajob.models.Account;
import com.getjavajob.models.Message;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dao-context.xml", "classpath:dao-context-override.xml"})
public class MessageDaoTest {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AccountDao accountDao;

    @Before
    public void setUp() throws FileNotFoundException, SQLException {
        RunScript.execute(dataSource.getConnection(), new FileReader("src/test/resources/H2DB_test_query.sql"));
    }

    @Test
    public void getAllFromAccTest() throws Exception {
        Account sender = accountDao.getById(1);
        Account receiver = accountDao.getById(2);
        assertNotNull(sender);
        assertNotNull(receiver);

        List<Message> messageList = messageDao.getAllFromAcc(sender, receiver);

        assertEquals(5, messageList.size());
    }

    @Test
    public void createTest() throws Exception {

        Account sender = accountDao.getById(3);
        Account receiver = accountDao.getById(4);

        Message message = new Message(sender, receiver, "Hi", Message.Type.ACC_PRIVATE, true);

        Message newMessage = messageDao.create(message);

        assertNotNull(newMessage);

        Message actual = messageDao.getById(newMessage.getId());

        assertEquals("Hi", actual.getMessage());
    }

    @Test
    public void getAllWallMsgTest() throws DAOException {
        Account receiver = accountDao.getById(3);
        List<Message> messageList = messageDao.getAllWallMsg(receiver);
        assertEquals(2, messageList.size());
    }

    @Test
    public void getAllTest() throws DAOException {
        Account receiver = accountDao.getById(1);
        List<Message> messageList = messageDao.getAllUnreadMsgs(receiver);
        assertEquals(2, messageList.size());
    }

}
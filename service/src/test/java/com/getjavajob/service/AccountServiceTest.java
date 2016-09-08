package com.getjavajob.service;

import com.getjavajob.dao.DAOImpls.AccountDao;
import com.getjavajob.models.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountDao accountDAO;

    @Test
    public void updateAccountTest() throws Exception {
        Account actual = new Account();

        accountService.updateAccount(actual);

        verify(accountDAO, times(1)).update(actual);
    }

    @Test
    public void createNewAccountTest() throws Exception {
        Account expected = new Account();

        when(accountDAO.getById(anyInt())).thenReturn(expected);
        accountService.createNewAccount(expected);

        Account actual = accountService.getAccount(1);

        verify(accountDAO, times(1)).create(expected);

        assertEquals(expected, actual);

    }

    @Test
    public void getAccountTest() throws Exception {
        Account actual = new Account();

        when(accountDAO.getById(1)).thenReturn(actual);
        Account expected = accountService.getAccount(1);

        verify(accountDAO, times(1)).getById(1);

        assertEquals(expected, actual);
    }


    @Test
    public void getAllAccountsTest() throws Exception {
        List<Account> accounts = new ArrayList<>();

        when(accountDAO.getAll()).thenReturn(accounts);

        accountService.getAllAccounts();

        verify(accountDAO, times(1)).getAll();
    }

}
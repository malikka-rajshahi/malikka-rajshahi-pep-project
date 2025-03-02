package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    public AccountDAO accountDAO;

    /**
     * No-args constructor for userService which creates a UserDAO.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    /**
     * Constructor for a UserService when a UserDAO is provided.
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /**
     * Retrieve an account from the account table, identified by its username, using accountDAO.
     * @param username
     * @return an account identified by username
     */
    public Account getAccountByUsername(String username) {
        return accountDAO.getAccountByUsername(username);
    }

    /**
     * Retrieve an account from the account table, identified by its account_id, using accountDAO.
     * @param username
     * @return an account identified by username
     */
    public Account getAccountById(int id) {
        return accountDAO.getAccountById(id);
    }

    /**
     * Insert a new account into the account table using accountDAO.
     * @param account
     * @return the inserted account
     */
    public Account registerAccount(Account account) {
        if (getAccountByUsername(account.username) != null) {
            System.out.println("Username already exists");
            return null;
        } else {
            accountDAO.registerAccount(account);
            return accountDAO.getAccountByUsername(account.username);
        }
    }
}

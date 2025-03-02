package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;
    public AccountService accountService;

    /**
     * No-args constructor for messageService which creates a MessageDAO.
     */
    public MessageService() {
        messageDAO = new MessageDAO();
        accountService = new AccountService();
    }

    /**
     * Constructor for a MessageService when a MessageDAO is provided.
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    /**
     * Insert a new message into the message table using messageDAO.
     * @param message
     * @return the inserted message
     */
    public Message postMessage(Message message) {
        if ((message.message_text.length() > 0) && 
            (message.message_text.length() <= 255) && 
            (accountService.getAccountById(message.posted_by) != null)) {
                messageDAO.postMessage(message);
                return getMessageById(message.message_id);
            }
        return null;
    }
    
    /**
     * Retrieve all messages with the messageDAO.
     * @return all messages.
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * Retrieve a message from the table, identified by message_id, with the messageDAO.
     * @return all messages.
     */
    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    /**
     * Remove a message from the table, identified by message_id, with the messageDAO.
     * @return the removed message
     */
    public Message deleteMessage(int id) {
        Message message = getMessageById(id);
        if (message != null) {
            return messageDAO.deleteMessage(message);
        }
        return null;
    }

    /**
     * Update a message's text, identified by message_id, with the messageDAO.
     * @return the removed message
     */
    public Message updateMessageText(int id, String text) {
        Message message = getMessageById(id);
        if ((message != null) && (text.length() > 0) && (text.length() <= 255)) {
            return messageDAO.updateMessageText(message, text);
        }
        return null;
    }

    /**
     * Retrieve all messages from a particular user with the messageDAO.
     * @return all messages.
     */
    public List<Message> getMessagesByUser(int posted_by) {
        return messageDAO.getMessagesByUser(posted_by);
    }
}

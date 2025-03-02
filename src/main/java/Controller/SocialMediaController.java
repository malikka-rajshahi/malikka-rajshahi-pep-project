package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.*;
import Service.*;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUserHandler);
        
        return app;
    }

    /**
     * Handler to post a new account.
     * @param ctx
     * @throws JsonProcessingException
     */
    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.registerAccount(account);
        if (addedAccount!=null) {
            ctx.json(mapper.writeValueAsString(addedAccount));
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handler to verify an account login.
     * @param ctx
     * @throws JsonProcessingException
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account verifiedAccount = accountService.getAccountByUsername(account.username);
        if ((verifiedAccount != null) && (account.password.equals(verifiedAccount.password))) {
            ctx.json(mapper.writeValueAsString(verifiedAccount));
        } else {
            ctx.status(401);
        }
    }

    /**
     * Handler to post a new message.
     * @param ctx
     * @throws JsonProcessingException
     */
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.postMessage(message);
        if (addedMessage != null) {
            ctx.json(mapper.writeValueAsString(addedMessage));
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handler to retrieve all messages.
     * @param ctx
     * @throws JsonProcessingException
     */
    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    /**
     * Handler to get a message, identified by message_id.
     * @param ctx
     */
    private void getMessageHandler(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(id);
        if (message != null) {
            ctx.json(message);
        } else {
            ctx.json("");
        }
    }

    /**
     * Handler to delete a message, identified by message_id.
     * @param ctx
     */
    private void deleteMessageHandler(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessage(id);
        ctx.status(200);
        if (message != null) {
            ctx.json(message);
        } else {
            ctx.json("");
        }
    }

    /**
     * Handler to update a message, identified by message_id.
     * @param ctx
     * @throws JsonProcessingException
     */
    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        JsonNode jsonNode = mapper.readTree(ctx.body());
        String newMessageText = jsonNode.get("message_text").asText();
        Message newMessage = messageService.updateMessageText(id, newMessageText);
        
        if (newMessage != null) {
            ctx.json(newMessage);
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handler to get all messages from a particular user, identified by account_id.
     * @param ctx
     */
    private void getMessagesByUserHandler(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByUser(id);
        if (messages != null) {
            ctx.json(messages);
        } else {
            ctx.json("");
        }
    }
}
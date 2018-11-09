/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Logic;

import java.time.LocalDateTime;

/**
 *
 * @author Peter
 */
public class Transfer {

    LogicFacade logic;
    private final String fromAccount;
    private final String toAccount;
    private final int amount;
    private final String text;
    private final LocalDateTime date = LocalDateTime.now();

    public Transfer(String fromAccount, String amount, String toAccount, String text, LogicFacade logic) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = Integer.parseInt(amount);
        this.text = text;
        this.logic = logic;
    }

    /**
     * Check if everything is in order with the transfer through a serious of
     * checks
     *
     * @param session The current session to compare certain checks to
     * @return An error or valid
     */
    public String validate(CustomerSession session) {
        //Check if user owns account
        if (!session.isAccount(fromAccount)) {
            return "Error; user does not own account.";
        } //Check if there is enough money in account
        else if (amount > Integer.parseInt(logic.getAccountBalance(fromAccount))) {
            return "Error; insufficient funds.";
        } //Check if the receiver exists
        else if (!logic.doesAccountExist(toAccount)) {
            return "Error; recipient not found.";
        }
        return "valid";
    }

    /**
     * Figure out the new balances and update both accounts
     *
     * @return An error or complete
     */
    public String completeTransfer() {
        int fromBalance = Integer.parseInt(logic.getAccountBalance(fromAccount));
        int toBalance = Integer.parseInt(logic.getAccountBalance(toAccount));

        //Calculate new balances
        fromBalance -= amount;
        toBalance += amount;

        //Update the balances
        try {
            logic.updateAccountBalance(fromAccount, fromBalance);
            logic.updateAccountBalance(toAccount, toBalance);
        } catch (Exception e) {
            return "Error; transaction couldn't be completed.";
        }
        saveTransfer();
        return "complete";
    }

    /**
     * Save the transaction. If it fails print out the input for review later
     */
    private void saveTransfer() {
        if (logic.saveTransfer(fromAccount, toAccount, amount, text, date).equals("false")) {
            System.out.println("Transaction from " + fromAccount
                    + " to " + toAccount + " could not be saved. Date: "
                    + date + " amount: " + amount + " message: " + text);
        }
    }

}
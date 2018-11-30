/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService.Logic;

import WebService.Acquaintance.IPersistence;

/**
 *
 * @author Nick
 */
public class ServerProtocol {

    private IPersistence persistence;
    private MailHandler mailHandler;
    public ServerProtocol(IPersistence persistence) {
        this.persistence = persistence;
        mailHandler = new MailHandler();
    }

    public String serverHandler(String[] data) {
        switch (data[0]) {
            case "00":
                String ID = data[1];
                String password = data[2];
                String response00 = persistence.login(ID, password);
                return response00;
            case "01":
                return persistence.getCustomerInfo(data[1]);
            case "02":
                return persistence.getAccountBalance(data[1]);
            case "03":
                return persistence.storeCustomerInfo(data[5], data[1], data[2], data[3], data[4]);
            case "04":
                break;
            case "05":
                String response05;
                Transfer transfer = new Transfer(data[1], data[2], data[3], data[4], persistence, data[5]);
                response05 = transfer.validate();
                //Send back the error if the transfer could not be completed
                if (!response05.equals("valid")) {
                    return response05;
                } else { //Otherwise complete the transfer
                    return transfer.completeTransfer();
                }
            case "06":
                String ID2 = data[1];
                return persistence.getTransactionHistory(ID2);
            case "07":
                String ID1 = data[1];
                String name = data[2];
                String birthday = data[3];
                String phonenumber = data[4];
                String address = data[5];
                String email = data[6];
                String password1 = data[7];
                return persistence.createCustomer(ID1, name, birthday, phonenumber, address, email, password1);
            case "08":
                String answer = "";
                String[] accountNos = persistence.getAccountNos(data[1]);
                for (String no : accountNos) {
                    if (no != null) {
                        answer += no + ";";
                    }
                }
                return answer;
            case "09":
                try {
                    if (data[2].equals("1")) {
                        persistence.openAccount(data[1]);
                    } else if (data[2].equals("0")) {
                        persistence.closeAccount(data[1]);
                    }
                    return "complete";
                } catch (Exception e) {
                    return "Error; could not open/close account";
                }
            case "10":
                //removes all "C" from Customer IDS              
                return persistence.getCustomerIDs().replace("C", "");
            case "11":
                break;
            case "12":
                    String ID3 = data[1];
                    return persistence.checkBankAccountID(ID3);
                    
                
            case "13":
                if (persistence.login(data[1], data[2]).equals("true")) {
                    try {
                        persistence.updatePassword(data[1], data[3]);
                        return "true";
                    } catch (Exception e) {
                        System.out.println("Error; serverHandler; updatePassword");
                        return "Error; Unexpected SQL error";
                    }
                } else {
                    return "Incorrect password";
                }
            case "14":
                try {
                    mailHandler.sendMailAutogenerated(data[1], data[2], data[3], data[4]);
                    return "true";
                } catch (Exception e) {
                    System.out.println("Error; mailHandler; sendAutogeneratedMail");
                    return "false";
                }
            case "16":
                ID = data[1];
                password = data[2];
                String response16 = persistence.login(ID, password);
                return response16;
            case "18":
                return "true";
            case "19":
                break;
            default:
                break;
        }
        return "Error; Protocol number not found";
    }
}

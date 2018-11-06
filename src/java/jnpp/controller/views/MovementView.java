/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jnpp.controller.views;

import java.util.Calendar;
import jnpp.service.dto.movements.DebitDTO;
import jnpp.service.dto.movements.DepositDTO;
import jnpp.service.dto.movements.MovementDTO;
import jnpp.service.dto.movements.PaymentDTO;
import jnpp.service.dto.movements.PurchaseDTO;
import jnpp.service.dto.movements.SaleDTO;
import jnpp.service.dto.movements.TransfertDTO;
import jnpp.service.dto.movements.WithdrawDTO;

/**
 *
 * @author damien
 */
public class MovementView {
    private final MovementDTO.Type type;
    private final int year;
    private final int month;
    private final int day;
    private final String otherAccount;
    private final String value;
    private final String label;
    
    public MovementView(MovementDTO movement, String accountRib) {
        type = movement.getType();
        Calendar cal = Calendar.getInstance();
        cal.setTime(movement.getDate());
        year = cal.get(Calendar.YEAR);
        month = (cal.get(Calendar.MONTH)+1);
        day = cal.get(Calendar.DAY_OF_MONTH);
        switch (movement.getType()) {
            case TRANSFERT:
                TransfertDTO transfert = (TransfertDTO)movement;
                Double amountTransfert = transfert.getMoney();
                if (transfert.getRibFrom().equals(accountRib)) {
                    otherAccount = transfert.getRibTo();
                    amountTransfert = -amountTransfert;
                    
                } else {
                    otherAccount = transfert.getRibFrom();
                }
                value = amountTransfert.toString() + Translator.getInstance().translateCurrency(Translator.Language.FR).get(transfert.getCurrency());
                label = "Transfert";
                break;
            case DEBIT:
                DebitDTO debit = (DebitDTO)movement;
                Double amountDebit = debit.getMoney();
                if (debit.getRibFrom().equals(accountRib)) {
                    otherAccount = debit.getRibTo();                    
                } else {
                    otherAccount = debit.getRibFrom();
                    amountDebit = -amountDebit;
                }
                value = amountDebit.toString() + Translator.getInstance().translateCurrency(Translator.Language.FR).get(debit.getCurrency());
                label = "Débit";
                break;
            case PURCHASE:
                PurchaseDTO purchase = (PurchaseDTO)movement;
                value = purchase.getAmount().toString();
                otherAccount = purchase.getRibFrom();
                label = purchase.getShare().getName();
                break;
            case SALE:
                SaleDTO sale = (SaleDTO)movement;
                value = sale.getAmount().toString();
                label = sale.getShare().getName();
                otherAccount = sale.getRibTo();
                break;
            case WITHDRAW:
                WithdrawDTO withdraw = (WithdrawDTO)movement;
                value = withdraw.getMoney().toString() + Translator.getInstance().translateCurrency(Translator.Language.FR).get(withdraw.getCurrency());
                label = "Retrait";
                otherAccount = "Distributeur";
                break;
            case PAYMENT:
                PaymentDTO payment = (PaymentDTO)movement;
                otherAccount = payment.getTarget();
                value = payment.getMoney().toString() + Translator.getInstance().translateCurrency(Translator.Language.FR).get(payment.getCurrency());
                label = "Paiement";
                break;
            case DEPOSIT:
                DepositDTO deposit = (DepositDTO)movement;
                otherAccount = "JNPP";
                value = deposit.getMoney().toString() + Translator.getInstance().translateCurrency(Translator.Language.FR).get(deposit.getCurrency());
                label = "Dépôt";
                break;
            default:
                throw new AssertionError(movement.getType().name());
        }
    }

    public MovementDTO.Type getType() {
        return type;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getOtherAccount() {
        return otherAccount;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    
}

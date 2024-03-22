package org.bot.visitor;

public interface CommandVisitor {
    void visitSavePortofolioLinkCommand();
    void visitSaveCoinCommand();
    void visitDeleteCoinCommand();
    void visitDeletePortfolioLinkCommand();
    void visitSaveCoinNotifyCommand();
    void visitDepositCommand();
    void visitWithdrawCommand();
    void visitAvailableChainsCommand();
    void visitBalanceCommand();
    void visitTradeCommand();
}

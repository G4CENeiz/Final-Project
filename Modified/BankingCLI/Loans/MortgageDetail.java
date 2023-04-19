package BankingCLI.Loans;

public class MortgageDetail {
    int    facility;
    double debt;
    double downPayment;
    double downPaymentPercentage;
    int    tenor;
    double interest;
    double installment;
    double minimumIncome;

    public MortgageDetail(int facility, double debt, double downPayment, double downPaymentPercentage, int tenor, double interest, double installment, double minimumIncome) {
        this.facility               = facility;
        this.debt                   = debt;
        this.downPayment            = downPayment;
        this.downPaymentPercentage  = downPaymentPercentage;
        this.tenor                  = tenor;
        this.interest               = interest;
        this.installment            = installment;
        this.minimumIncome          = minimumIncome;
    }

    public void print() {
        String ordinal;
        switch (String.valueOf(facility).charAt(String.valueOf(facility).length() - 1)) {
            case '1' -> ordinal = "st";
            case '2' -> ordinal = "nd";
            case '3' -> ordinal = "rd";
            default -> ordinal = "th";
        }

        String prompt = "Mortgage Application";
        String[] varValue = {
            String.format(": %d%s", facility, ordinal),
            String.format(": IDR %,d", debt),
            String.format(": IDR %,d", downPayment),
            String.format(": IDR %,d", (debt - downPayment)),
            String.format(": %d", tenor),
            String.format(": %.2f%s", interest, "%"),
            String.format(": IDR %,.2f", installment),
            String.format(": IDR %,.2f", minimumIncome)
        };

        String[] varName = {
            String.format("Credit facility "),
            String.format("House price "),
            String.format("Down payment %.2f%s ", downPaymentPercentage, "%"),
            String.format("Debt principal "),
            String.format("Tenor "),
            String.format("Interest "),
            String.format("installment "),
            String.format("Minimum income ")
        };

        String barTop = "================================================================";
        String barBot = "||============================================================||";
        String side = "||";

        int headLen = barTop.length();
        int headSpace = (headLen - (2 * side.length()) - prompt.length()) / 2;
        String spacing = String.format("%s%" + (headLen - (2 * side.length())) + "s%s", side, " ", side);
        String title = String.format("%s%" + headSpace + "s%s%" + headSpace + "s%s", side, " ", prompt, " ", side);
        
        int fit = 0;
        for (String varNameElement : varName) {
            if (fit < varNameElement.length()) {
                fit = varNameElement.length();
            }
        }

        System.out.println(barTop);
        System.out.println(title);
        System.out.println(barBot);
        System.out.println(spacing);
        
        for (int i = 0; i < varName.length; i++) {
            int padL = ((headLen / 2) - side.length() - fit);
            int padR = ((headLen / 2) - side.length() - varValue[i].length());
            String content = String.format("%s%" + padL + "s%-" + fit + "s%s%" + padR + "s%s", side, " ", varName[i], varValue[i], " ", side);
            System.out.println(content);
            System.out.println(spacing);
        }

        System.out.println(barBot);
    }
}
package boersenspiel;

public class Player {
    private String name;
    private CashAccount cAcc;
    private ShareDepositAccount sAcc;

    public Player(String name) {
        this.name = name;
        this.cAcc = new CashAccount(name, 2000000);
        this.sAcc = new ShareDepositAccount(name);
    }

    public CashAccount getCAcc() {
        return cAcc;
    }

    public ShareDepositAccount getSAcc() {
        return sAcc;
    }

//    public String toString() {
//        String shareItems = "";
//
//        for (int index = 0; index < sAcc.getCollection().length; index++) {
//            shareItems += sAcc.getCollection()[index].getName();
//            shareItems += "(" + sAcc.getCollection()[index].getSAmount() + ")";
//            shareItems += " ";
//        }
//
//        if (sAcc.getCollection().length == 0) {
//            return "Hallo " + this.name + "! Ihr Kontostand beträgt " + (double) cAcc.getValue() / 100 + "€, Ihr(e) Aktienpaket(e): keine";
//
//        } else {
//            return "Hallo " + this.name + "! Ihr Kontostand beträgt " + (double) cAcc.getValue() / 100 + "€, Ihr(e) Aktienpaket(e): " + shareItems
//                    + ". Der aktuelle Gesamteinkaufswert ihrer Aktien beträgt: " + (double) sAcc.getValue() / 100 + "€.";
//        }
//    }

    public String getName() {
        return name;
    }

}

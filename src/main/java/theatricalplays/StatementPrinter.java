package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class StatementPrinter {

  private static final NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);
  private static final int MIN_FIDELITY_POINTS = 150;
  private static final float DEDUCTION_AMOUNT = 15;
  
  public StringBuffer toText(Invoice invoice, Map<String, Play> plays) {

    StringBuffer result = new StringBuffer("Statement for " + invoice.customer.name + "\n");
    
    float totalAmount = calculAmount(invoice, plays);
    int volumeCredits = calculCredits(invoice, plays);

    for (Performance perf : invoice.performances) {
      Play play = performancePlay(perf, plays);
      float price = play.getPrice(perf.audience);
      // print line for this order
      result = result.append("  " + play.name + ": " + frmt.format(price) + " (" + perf.audience + " seats)\n");
    }

    if(invoice.customer.soldeFidelite >= MIN_FIDELITY_POINTS){
      totalAmount -= DEDUCTION_AMOUNT;
      volumeCredits = invoice.customer.soldeFidelite - 150;

      result.append("Congrats!\nYou have been deducted of " + MIN_FIDELITY_POINTS + " credits and " + frmt.format(DEDUCTION_AMOUNT)+"\n");
      result.append("Amount owed is " + frmt.format(totalAmount) + "\n");
      result.append("You earned " + volumeCredits + " credits\n");

      return result;
    }

    result = result.append("Amount owed is " + frmt.format(totalAmount) + "\n");
    result = result.append("You earned " + volumeCredits + " credits\n");
    
    return result;
  }

  public String toHTML(Invoice invoice, Map<String, Play> plays) {
    StringBuffer result = new StringBuffer("<!DOCTYPE html> \n"+"<html> \n"
    + "\t<head>\n"
    + "\t\t<style>\n" + "\t\t\ttable, th, td {border: 1px solid black; }\n"
    + "\t\t\tth, td { padding: 5px; }\n" 
    + "\t\t</style>\n" + "\t\t</head>\n" 
    + "\t<body> \n"
    + "\t\t<h1>Invoice</h1> \n"+"\t\t<ul><li><p><b>Client :</b> " 
    + invoice.customer.name+ "</ul></li></h2> \n"
    + "\t\t<table style=\"width: 400px; border: 1px solid black\"> \n"
    + "\t\t\t<tr> \n"+"\t\t\t\t <th>Piece</th> \n"+"\t\t\t\t <th>Seats sold</th> \n"
    + "\t\t\t\t <th>Price</th> \n"+"\t\t\t</tr> \n");

    float totalAmount = calculAmount(invoice, plays);
    int volumeCredits = calculCredits(invoice, plays);

    for (Performance perf : invoice.performances) {
      Play play = performancePlay(perf, plays);
      float price = play.getPrice(perf.audience);

      result.append("\t\t\t<tr> \n"
        +"\t\t\t\t <td>" + play.name + "</td> \n"
        +"\t\t\t\t <td>" + perf.audience + "</td> \n"
        +"\t\t\t\t <td>" + frmt.format(price) + "</td> \n"
        +"\t\t\t</tr> \n");
    }   

    if(invoice.customer.soldeFidelite >= MIN_FIDELITY_POINTS){
      totalAmount -= DEDUCTION_AMOUNT;
      volumeCredits = invoice.customer.soldeFidelite - 150;

      result.append("\t\t\t\t<tr> \n"
      + "\t\t\t\t\t <td colspan=\"2\" style=\"text-align: right;\"><b>Total owed:<b></td> \n"
      + "\t\t\t\t\t <td>" + frmt.format(totalAmount)+ "</td> \n"
      + "\t\t\t\t</tr> \n");
      result.append("\t\t\t\t<tr> \n"
      + "\t\t\t\t\t <td colspan=\"2\" style=\"text-align: right;\"><b>Fidelity points earned:<b></td> \n"
      + "\t\t\t\t\t <td>" + volumeCredits + "</td> \n"
      + "\t\t\t\t</tr> \n");

      result.append("\t\t\t</table> \n"
      + "\t\t\t<p><i>Congrats! You have been deducted of 150 credits and " + frmt.format(DEDUCTION_AMOUNT) + "<i></p> \n"
      + "\t\t\t<p><i>Payment is required under 30 days. We can break your knees if you don't do so.<i></p> \n"
      + "\t\t</body> \n"
      + "\t</html> \n");

      return result.toString();
    } 

    result.append("\t\t\t<tr> \n"
    +"\t\t\t\t <td colspan=\"2\" style=\"text-align: right;\"><b>Total owed:<b></td> \n"
    +"\t\t\t\t <td>" + frmt.format(totalAmount)+ "</td> \n"
    +"\t\t\t\t</tr> \n");

    result.append("\t\t\t<tr> \n"
    + "\t\t\t\t <td colspan=\"2\" style=\"text-align: right;\"><b>Fidelity points earned:<b></td> \n"
    + "\t\t\t\t <td>" + volumeCredits + "</td> \n"
    + "\t\t\t</tr> \n");

    result.append("\t\t</table> \n"
    + "\t\t<p><i>Payment is required under 30 days. We can break your knees if you don't do so.<i></p> \n"
    + "\t</body> \n"
    + "</html> \n");

    return result.toString();
  }

  //fonction qui calcul total amount dépend de type
  private float calculAmount(Invoice invoice, Map<String, Play> plays){
    float thisAmount = 0;

    for(Performance perf: invoice.performances){
      thisAmount += performancePlay(perf, plays).getPrice(perf.audience);
    }

    return thisAmount;
  }

  //fonction qui calcul le volume de crédits
  private int calculCredits(Invoice invoice, Map<String, Play> plays){
    int result = 0;
    for(Performance perf: invoice.performances){
      result += performancePlay(perf, plays).getCredits(perf.audience);
    }
    return result;
  }

  // cette fonction permet de récupèrer un objet Play associé à un Performance donné à partir d'une collection de plays
  private Play performancePlay(Performance perf, Map<String, Play> plays) {
    return plays.get(perf.playID);
  }

  //fonction qui precise le format de currency
  /*private String formatCurrency(float totalAmount) {
    return NumberFormat.getCurrencyInstance(Locale.US).format(totalAmount);
  }*/

}

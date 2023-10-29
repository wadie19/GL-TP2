package theatricalplays;

public abstract class Play {
  //variable statique
  public static final String TRAGEDY = "tragedy";
  public static final String COMEDY = "comedy";

  public String name;

  //constructeur sans paramétre
  public Play(){}
  
  //constructeur avec paramétre 
  public Play(String name) {
    this.name = name;
  }

  public abstract float getPrice(int audience);
  public abstract int getCredits(int audience);

}


package theatricalplays;

public class Play {
  //variable statique
  public static final String TRAGEDY = "tragedy";
  public static final String COMEDY = "comedy";

  public String name;
  public String type;

  public Play(String name, String type) {
    this.name = name;
    this.type = type;
  }

}


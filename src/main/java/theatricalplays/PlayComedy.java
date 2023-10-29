package theatricalplays;

public class PlayComedy extends Play{

    public PlayComedy(String name) {
        this.name = name;
      }

    @Override
    public float getPrice(int audience) {
        return 300 + 3 * audience + ((audience > 20) ? 100 + 5 * (audience - 20) : 0);

    }

    @Override
    public int getCredits(int audience) {
        return Math.max(audience - 30, 0) + (int)Math.floor(audience / 5);

    }


}

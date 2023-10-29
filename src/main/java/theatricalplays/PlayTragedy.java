package theatricalplays;

public class PlayTragedy extends Play{

    public PlayTragedy(String name) {
        super(name);
    }

    @Override
    public float getPrice(int audience) {
        return 400 + ((audience > 30) ? 10 * (audience - 30) : 0);
    }

    @Override
    public int getCredits(int audience) {
        return Math.max(audience - 30, 0);
    }

    
}

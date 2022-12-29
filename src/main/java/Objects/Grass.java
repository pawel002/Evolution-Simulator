package Objects;

public class Grass {
    Vector2d position;

    public Grass(Vector2d pos_){
        position = pos_;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "*";
    }
}

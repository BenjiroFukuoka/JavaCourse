package vectors;
public class VectorIndexOutOfBoundsException extends Exception{
    VectorIndexOutOfBoundsException(int index){
        super("Индекс " + index + " имеет недоступное значением ");
    }
    public String getMessage() {
        return super.getMessage();
    }
}
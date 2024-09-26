package vectors;
class IncompatibleVectorSizesException extends Exception{
    IncompatibleVectorSizesException(int size1, int size2){
        super("Несоответствие размерности векторов Size vector1 = " + size1 + ", Size vector2 = " + size2 + '.');
    }
    public String getMessage() {
        return super.getMessage();
    }
}
package vectors;
class Vectors{
    static Vector mul(Vector vector, double alpha) throws VectorIndexOutOfBoundsException{//произведение вектора на число
        int size = vector.size();
        Vector tmpV = vector;
        for(int i = 0; i < size; ++i)
            tmpV.set(i, ((int)((vector.get(i)*alpha)*100))/100.0);
        return tmpV;
    }
    static Vector sum(Vector vector1, Vector vector2) throws IncompatibleVectorSizesException, VectorIndexOutOfBoundsException{//сумма двух векторов
        int size = vector1.size();
        if(size != vector2.size())
            throw new IncompatibleVectorSizesException(size, vector2.size());
        Vector tmpV = new LinkedListArray(size); //временная переменная - результат
        for (int i = 0; i < size; i++)
            tmpV.set(i, ((int)((vector1.get(i) + vector2.get(i))*100))/100.0);
        return tmpV;
    }
    static double mul(Vector vector1, Vector vector2) throws VectorIndexOutOfBoundsException{ //произведение векторов
        int len; //проверка, чтобы не выйти за диапозон
        if (vector1.size() < vector2.size())
            len = vector1.size();
        else
            len = vector2.size();
        double tmp = 0;
        for (int i = 0; i < len; ++i)
            tmp += vector1.get(i) * vector2.get(i);
        return tmp;
    }   
}
package mathserver;

public class PlainMathService implements MathService{
    public double add(double valOne, double valTwo){
        return valOne + valTwo;
    }
    
    public double sub(double valOne, double valTwo){
        return valOne - valTwo;
    }
    
    public double mul(double valOne, double valTwo){
        return valOne * valTwo;
    }
    
    public double div(double valOne, double valTwo){
        if(valTwo != 0)
            return valOne / valTwo;
        return Double.MAX_VALUE;
    }
}

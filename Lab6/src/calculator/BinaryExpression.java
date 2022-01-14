package calculator;

public abstract class BinaryExpression extends Expression{
    private final Expression lft;
    private final Expression rht;
    private final String operator;
    public double evaluate(final Bindings bindings)
    {
        double lft = this.lft.evaluate(bindings);
        double rht = this.rht.evaluate(bindings);
        return _applyOperator(lft, rht);
    }
    protected abstract double _applyOperator(double lft, double rht);
    public String toString()
    {
        return "(" + lft + " " + operator + " "+ rht + ")";
    }
    public BinaryExpression(final Expression lft, final Expression rht, String operator){
        this.lft = lft;
        this.rht = rht;
        this.operator = operator;
    }
    public String getOperator(){
        return operator;
    }

}

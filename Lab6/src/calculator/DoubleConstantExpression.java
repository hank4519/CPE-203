package calculator;

class DoubleConstantExpression extends Expression //or implement Operation
{
   private final double val;
   public DoubleConstantExpression(final double val)
   {
      this.val = val;
   }

   public String toString()
   {
      return String.valueOf(val);
   }

   public double evaluate(final Bindings bindings)
   {
      return val;
   }
}

package calculator;

class IdentifierExpression extends Expression //or implement Operation
{
   private final String id;

   public IdentifierExpression(final String id)
   {
      this.id = id;
   }

   public String toString()
   {
      return id;
   }

   public double evaluate(final Bindings bindings)
   {
      return bindings.lookupBinding(id);
   }
}


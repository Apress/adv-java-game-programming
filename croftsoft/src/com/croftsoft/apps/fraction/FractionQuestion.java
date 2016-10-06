     package com.croftsoft.apps.fraction;

     import java.util.Random;

     import com.croftsoft.core.math.MathLib;
     
     /*********************************************************************
     * Question data.
     *
     * @version
     *   2002-07-20
     * @since
     *   2002-07-20
     * @author
     *   <a href="http://www.croftsoft.com/">David Wallace Croft</a>
     *********************************************************************/

     public final class  FractionQuestion
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private final int  maxDenominator;

     private final Random  random;

     //

     private int  firstDenominator;

     private int  firstNumerator;

     private int  secondDenominator;

     private int  secondNumerator;

     private int  thirdDenominator;

     private int  thirdNumerator;

     //

     private int  commonDenominator;

     private int  firstAnswerNumerator;

     private int  secondAnswerNumerator;

     private int  thirdAnswerNumerator;

     //

     private boolean  first;

     private boolean  second;

     private boolean  third;

     private boolean  fourth;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  FractionQuestion ( int  maxDenominator )
     //////////////////////////////////////////////////////////////////////
     {
       if ( maxDenominator < 1 )
       {
         throw new IllegalArgumentException ( "maxDenominator < 1" );
       }

       this.maxDenominator = maxDenominator;

       this.random = new Random ( );

       reset ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  reset ( )
     //////////////////////////////////////////////////////////////////////
     {
       first  = false;

       second = false;

       third  = false;

       fourth = false;

       int  gcf;

       while ( true )
       {
         firstDenominator  = random.nextInt ( maxDenominator ) + 1;

         firstNumerator    = random.nextInt ( firstDenominator + 1 );

         if ( firstNumerator == 0 )
         {
           continue;
         }
         else
         {
           gcf = MathLib.greatestCommonFactor (
             firstDenominator, firstNumerator );

           firstDenominator /= gcf;

           firstNumerator /= gcf;
         }

         secondDenominator = random.nextInt ( maxDenominator ) + 1;

         secondNumerator   = random.nextInt ( secondDenominator + 1 );

         if ( secondNumerator == 0 )
         {
           continue;
         }
         else
         {
           gcf = MathLib.greatestCommonFactor (
             secondDenominator, secondNumerator );

           secondDenominator /= gcf;

           secondNumerator /= gcf;
         }

         if ( firstDenominator > secondDenominator )
         {
           if ( firstDenominator % secondDenominator == 0 )
           {
             commonDenominator = firstDenominator;
           }
           else
           {
             commonDenominator = firstDenominator * secondDenominator;
           }
         }
         else if ( firstDenominator < secondDenominator )
         {
           if ( secondDenominator % firstDenominator == 0 )
           {
             commonDenominator = secondDenominator;
           }
           else
           {
             commonDenominator = firstDenominator * secondDenominator;
           }
         }
         else
         {
           continue;
         }

         if ( commonDenominator > maxDenominator )
         {
           continue;
         }

         firstAnswerNumerator
           = ( commonDenominator / firstDenominator  ) * firstNumerator;

         secondAnswerNumerator
           = ( commonDenominator / secondDenominator ) * secondNumerator;

         thirdAnswerNumerator = firstAnswerNumerator + secondAnswerNumerator;

         thirdNumerator = thirdAnswerNumerator;

         if ( thirdNumerator == 0 )
         {
           thirdDenominator = 1;
         }
         else
         {
           thirdDenominator = commonDenominator;

           gcf = MathLib.greatestCommonFactor (
             thirdDenominator, thirdNumerator );

           thirdDenominator /= gcf;

           thirdNumerator /= gcf;
         }

         if ( ( thirdNumerator <= thirdDenominator )
           && ( thirdDenominator <= maxDenominator ) )
         {
           break;
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int  getFirstDenominator  ( ) { return firstDenominator;  }

     public int  getFirstNumerator    ( ) { return firstNumerator;    }

     public int  getSecondDenominator ( ) { return secondDenominator; }

     public int  getSecondNumerator   ( ) { return secondNumerator;   }

     public int  getThirdDenominator  ( ) { return thirdDenominator;  }

     public int  getThirdNumerator    ( ) { return thirdNumerator;    }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int  getCommonDenominator ( ) { return commonDenominator; }

     public int  getFirstAnswerNumerator    ( )
       { return firstAnswerNumerator;    }

     public int  getSecondAnswerNumerator   ( )
       { return secondAnswerNumerator;   }

     public int  getThirdAnswerNumerator    ( )
       { return thirdAnswerNumerator;    }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public boolean  getFirst  ( ) { return first;  }

     public boolean  getSecond ( ) { return second; }

     public boolean  getThird  ( ) { return third;  }

     public boolean  getFourth ( ) { return fourth; }

     public void  setFirst  ( boolean  first  ) { this.first  = first;  }

     public void  setSecond ( boolean  second ) { this.second = second; }

     public void  setThird  ( boolean  third  ) { this.third  = third;  }

     public void  setFourth ( boolean  fourth ) { this.fourth = fourth; }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }
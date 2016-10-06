     package com.croftsoft.apps.compiler.mini.code;

     import java.io.PrintStream;
     import java.util.*;

     import com.croftsoft.apps.compiler.mini.node.*;
     import com.croftsoft.apps.compiler.mini.parse.MiniSymbols;

     /*********************************************************************
     * Generates Java source code from MiniNode objects.
     *
     * @see
     *   com.croftsoft.apps.compiler.mini.node.MiniNodeCodeVisitor
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1999-04-27
     *********************************************************************/

     public class  JavaSourceMiniNodeCodeVisitor
       implements MiniNodeCodeVisitor, MiniSymbols
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     protected PrintStream  out;
     protected String       className;

     protected int          depth;
     protected int          nextTemp;

     protected String  margin = "     ";
     protected String  demarkLine
       = "///////////////////////////////////"
       + "///////////////////////////////////";

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public  JavaSourceMiniNodeCodeVisitor (
       PrintStream  out,
       String       className )
     //////////////////////////////////////////////////////////////////////
     {
       this.out       = out;
       this.className = className;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public int  nextTemp ( )
     //////////////////////////////////////////////////////////////////////
     {
       return nextTemp++;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     protected void  indent ( )
     //////////////////////////////////////////////////////////////////////
     {
       out.print ( pad ( ) );
     }

     protected String  pad ( )
     //////////////////////////////////////////////////////////////////////
     {
       String  s = margin;

       for ( int  i = 0; i < depth; i++ ) s += "  ";

       return s;
     }

     protected void  generate (
       String  prefix, List  miniNodeList, String  postfix,
       String  delimiter )
     //////////////////////////////////////////////////////////////////////
     {
       if ( miniNodeList == null ) return;

       Iterator  i = miniNodeList.iterator ( );

       while ( i.hasNext ( ) )
       {
         if ( prefix != null )
         {
           out.print ( prefix );
         }

         ( ( MiniNode ) i.next ( ) ).generate ( this );

         if ( postfix != null ) out.print ( postfix );

         if ( delimiter != null )
         {
           if ( i.hasNext ( ) ) out.print ( delimiter );
         }
       }
     }

     protected void  generate ( List  miniNodeList )
     //////////////////////////////////////////////////////////////////////
     {
       generate ( null, miniNodeList, null, null );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public void  generateAssignmentStatement ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       AssignmentStatementMiniNode  assignmentStatementMiniNode
         = ( AssignmentStatementMiniNode ) miniNode;

       indent ( );

       assignmentStatementMiniNode.getNameMiniNode ( ).generate ( this );

       out.print ( " = " );

       assignmentStatementMiniNode.getExpressionMiniNode ( ).generate (
         this );

       out.println ( ";" );
     }

     public void  generateBlock ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       BlockMiniNode  blockMiniNode = ( BlockMiniNode ) miniNode;

       DeclarationSequenceMiniNode  declarationSequenceMiniNode
         = blockMiniNode.getDeclarationSequenceMiniNode ( );

       if ( declarationSequenceMiniNode != null )
       {
         declarationSequenceMiniNode.generate ( this );
         out.println ( "" );
       }

       StatementSequenceMiniNode  statementSequenceMiniNode
         = blockMiniNode.getStatementSequenceMiniNode ( );

       if ( statementSequenceMiniNode != null )
       {
         statementSequenceMiniNode.generate ( this );
       }
     }

     public void  generateComparison ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       ComparisonMiniNode  comparisonMiniNode
         = ( ComparisonMiniNode ) miniNode;

       comparisonMiniNode.getLeftExpressionMiniNode  ( ).generate ( this );

       out.print ( " " );

       comparisonMiniNode.getRelationMiniNode ( ).generate ( this );

       out.print ( " " );

       comparisonMiniNode.getRightExpressionMiniNode ( ).generate ( this );
     }

     public void  generateConditionalStatement ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       ConditionalStatementMiniNode  conditionalStatementMiniNode
         = ( ConditionalStatementMiniNode ) miniNode;

       indent ( );

       out.print ( "if ( " );

       conditionalStatementMiniNode.getComparisonMiniNode ( ).generate (
         this );

       out.println ( " )" );

       indent ( );

       out.println ( "{" );

       depth++;

       conditionalStatementMiniNode.getThenStatementSequenceMiniNode (
         ).generate ( this );

       depth--;

       indent ( );

       out.println ( "}" );

       StatementSequenceMiniNode  elseStatementSequenceMiniNode
         = conditionalStatementMiniNode.getElseStatementSequenceMiniNode (
         );

       if ( elseStatementSequenceMiniNode != null )
       {
         indent ( );

         out.println ( "else" );

         indent ( );

         out.println ( "{" );

         depth++;

         elseStatementSequenceMiniNode.generate ( this );

         depth--;

         indent ( );

         out.println ( "}" );
       }
     }

     public void  generateConstant ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       ConstantMiniNode  constantMiniNode
         = ( ConstantMiniNode ) miniNode;

       out.print ( constantMiniNode.getI ( ) );
     }

     public void  generateDeclarationSequence ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       DeclarationSequenceMiniNode  declarationSequenceMiniNode
         = ( DeclarationSequenceMiniNode ) miniNode;

       generate ( pad ( ),
         declarationSequenceMiniNode.getDeclarationMiniNodeList ( ),
         null, null );
     }

     public void  generateDefiniteLoopStatement ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       DefiniteLoopStatementMiniNode  definiteLoopStatementMiniNode
         = ( DefiniteLoopStatementMiniNode ) miniNode;

       indent ( );

       // Underscores are not used in the Mini language so it is safe to
       // use them to create new variables without fear of namespace
       // conflicts.

       String  countStr = "count_" + nextTemp ( );
       String  indexStr = "index_" + nextTemp ( );

       out.print ( "int  " + countStr + " = " );

       definiteLoopStatementMiniNode.getExpressionMiniNode ( ).generate (
         this );

       out.println ( ";" );

       indent ( );

       out.println ( "for ( int  " + indexStr + " = 0; "
         + indexStr + " < " + countStr + "; " + indexStr + "++ )" );

       indent ( );

       out.println ( "{" );

       depth++;

       definiteLoopStatementMiniNode.getStatementSequenceMiniNode (
         ).generate ( this );

       depth--;

       indent ( );

       out.println ( "}" );
     }

     public void  generateExpression ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       ExpressionMiniNode  expressionMiniNode
         = ( ExpressionMiniNode ) miniNode;

       MiniNode  expression = expressionMiniNode.getExpressionMiniNode ( );

       if ( expression != null )
       {
         expression.generate ( this );

         out.print ( " " );

         expressionMiniNode.getOperatorMiniNode ( ).generate ( this );

         out.print ( " " );
       }

       expressionMiniNode.getTermMiniNode ( ).generate ( this );
     }

     public void  generateExpressionElement ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       ExpressionElementMiniNode  expressionElementMiniNode
         = ( ExpressionElementMiniNode ) miniNode;

       out.print ( "( " );

       expressionElementMiniNode.getExpressionMiniNode ( ).generate (
         this );

       out.print ( " )" );
     }

     public void  generateExpressionSequence ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       ExpressionSequenceMiniNode  expressionSequenceMiniNode
         = ( ExpressionSequenceMiniNode ) miniNode;

       generate ( null,
         expressionSequenceMiniNode.getExpressionMiniNodeList ( ),
         null, ", " );
     }

     public void  generateIndefiniteLoopStatement ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       IndefiniteLoopStatementMiniNode  indefiniteLoopStatementMiniNode
         = ( IndefiniteLoopStatementMiniNode ) miniNode;

       indent ( );

       out.print ( "while ( " );

       indefiniteLoopStatementMiniNode.getComparisonMiniNode ( ).generate (
         this );

       out.println ( " )" );

       indent ( );

       out.println ( "{" );

       depth++;

       indefiniteLoopStatementMiniNode.getStatementSequenceMiniNode (
         ).generate ( this );

       depth--;

       indent ( );

       out.println ( "}" );
     }

     public void  generateInputStatement ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       InputStatementMiniNode  inputStatementMiniNode
         = ( InputStatementMiniNode ) miniNode;

       NameSequenceMiniNode  nameSequenceMiniNode
         = inputStatementMiniNode.getNameSequenceMiniNode ( );

       out.println ( pad ( ) + "try" );
       out.println ( pad ( ) + "{" );

       generate ( pad ( ) + "  ",
         nameSequenceMiniNode.getNameMiniNodeList ( ),
         " = Integer.parseInt ( new java.io.BufferedReader ("
         + System.getProperty ( "line.separator" )
         + pad ( ) + "    "
         + "new java.io.InputStreamReader ( System.in ) ).readLine ( ) );"
         + System.getProperty ( "line.separator" ),
         null );

       out.println ( pad ( ) + "}" );
       out.println ( pad ( ) + "catch ( java.io.IOException  ex )" );
       out.println ( pad ( ) + "{" );
       out.println ( pad ( )
         + "  throw new RuntimeException ( \"input error\" );" );
       out.println ( pad ( ) + "}" );
     }

     public void  generateIntegerDeclaration ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       IntegerDeclarationMiniNode  integerDeclarationMiniNode
         = ( IntegerDeclarationMiniNode ) miniNode;

       out.print ( "int  " );

       integerDeclarationMiniNode.getNameSequenceMiniNode ( ).generate (
         this );

       out.println ( ";" );
     }

     public void  generateName ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       NameMiniNode  nameMiniNode = ( NameMiniNode ) miniNode;

       out.print ( nameMiniNode.getName ( ) );
     }

     public void  generateNameSequence ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       NameSequenceMiniNode  nameSequenceMiniNode
         = ( NameSequenceMiniNode ) miniNode;

       generate ( null, nameSequenceMiniNode.getNameMiniNodeList ( ),
         null, ", " );
     }

     public void  generateOperator ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       OperatorMiniNode  operatorMiniNode = ( OperatorMiniNode ) miniNode;

       String  s = null;

       switch ( operatorMiniNode.getOperator ( ) )
       {
         case MiniSymbols.DIVIDE:  s = "/"; break;
         case MiniSymbols.MINUS :  s = "-"; break;
         case MiniSymbols.PLUS  :  s = "+"; break;
         case MiniSymbols.TIMES :  s = "*"; break;

         default:
           throw new RuntimeException ( "unknown operator" );
       }

       out.print ( s );
     }

     public void  generateOutputStatement ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       OutputStatementMiniNode  outputStatementMiniNode
         = ( OutputStatementMiniNode ) miniNode;

       ExpressionSequenceMiniNode  expressionSequenceMiniNode
         = outputStatementMiniNode.getExpressionSequenceMiniNode ( );

       generate ( pad ( ) + "System.out.println ( ",
         expressionSequenceMiniNode.getExpressionMiniNodeList ( ),
         " );" + System.getProperty ( "line.separator" ),
         null );
     }

     public void  generateParameterSequence ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       ParameterSequenceMiniNode  parameterSequenceMiniNode
         = ( ParameterSequenceMiniNode ) miniNode;

       NameSequenceMiniNode  nameSequenceMiniNode
         = parameterSequenceMiniNode.getNameSequenceMiniNode ( );

       List  nameMiniNodeList
         = nameSequenceMiniNode.getNameMiniNodeList ( );

       generate ( "int  ", nameMiniNodeList, null, ", " );
     }

     public void  generateProcedureCallStatement ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       ProcedureCallStatementMiniNode  procedureCallStatementMiniNode
         = ( ProcedureCallStatementMiniNode ) miniNode;

       NameMiniNode  nameMiniNode
         = procedureCallStatementMiniNode.getNameMiniNode ( );

       indent ( );

       if ( procedureCallStatementMiniNode.callsNested (
         ).booleanValue ( ) )
       {
         out.print ( "new " );

         nameMiniNode.generate ( this );

         out.print ( " ( ).procedure (" );

       }
       else
       {
         nameMiniNode.generate ( this );
         out.print ( " (" );
       }

       ExpressionSequenceMiniNode  expressionSequenceMiniNode
         = procedureCallStatementMiniNode.getExpressionSequenceMiniNode (
         );

       if ( expressionSequenceMiniNode != null )
       {
         out.print ( " " );

         expressionSequenceMiniNode.generate ( this );
       }

       out.println ( " );" );
     }

     public void  generateProcedureDeclaration ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       ProcedureDeclarationMiniNode  procedureDeclarationMiniNode
         = ( ProcedureDeclarationMiniNode ) miniNode;

       NameMiniNode  nameMiniNode
         = procedureDeclarationMiniNode.getNameMiniNode ( );

       BlockMiniNode  blockMiniNode
         = procedureDeclarationMiniNode.getBlockMiniNode ( );

       DeclarationSequenceMiniNode  declarationSequenceMiniNode
         = blockMiniNode.getDeclarationSequenceMiniNode ( );

       boolean  hasNestedProcedures = false;

       if ( declarationSequenceMiniNode != null )
       {
         List  declarationMiniNodeList
           = declarationSequenceMiniNode.getDeclarationMiniNodeList ( );

         Iterator  i = declarationMiniNodeList.iterator ( );
         while ( i.hasNext ( ) )
         {
           if ( i.next ( ) instanceof ProcedureDeclarationMiniNode )
           {
             hasNestedProcedures = true;
             break;
           }
         }
       }

       out.println ( "" );

       if ( !hasNestedProcedures )
       {
         out.print ( margin + "public void  " );
         nameMiniNode.generate ( this );
         out.print ( " (" );

         ParameterSequenceMiniNode  parameterSequenceMiniNode
           = procedureDeclarationMiniNode.getParameterSequenceMiniNode ( );

         if ( parameterSequenceMiniNode != null )
         {
           out.print ( " " );
           parameterSequenceMiniNode.generate ( this );
         }

         out.println ( " )" );

         out.println ( margin + demarkLine );

         out.println ( margin + "{" );

         depth++;

         blockMiniNode.generate ( this );

         depth--;

         out.println ( margin + "}" );

         out.println ( "" );
       }
       else
       {
         out.print ( margin + "public class  " );
         nameMiniNode.generate ( this );
         out.println ( "" );

         out.println ( margin + demarkLine );
         out.print ( margin + "// Start of inner class " );
         nameMiniNode.generate ( this );
         out.println ( "" );
         out.println ( margin + demarkLine );
         out.println ( margin + "{" );

         if ( declarationSequenceMiniNode != null )
         {
           out.println ( "" );
           declarationSequenceMiniNode.generate ( this );
         }

         out.println ( "" );

         out.print ( margin + "public void  procedure (" );

         ParameterSequenceMiniNode  parameterSequenceMiniNode
           = procedureDeclarationMiniNode.getParameterSequenceMiniNode ( );

         if ( parameterSequenceMiniNode != null )
         {
           out.print ( " " );
           parameterSequenceMiniNode.generate ( this );
         }

         out.println ( " )" );

         out.println ( margin + demarkLine );

         out.println ( margin + "{" );

         depth++;

         blockMiniNode.getStatementSequenceMiniNode ( ).generate ( this );

         depth--;

         out.println ( margin + "}" );

         out.println ( "" );

         out.println ( margin + demarkLine );
         out.print ( margin + "// End of inner class " );
         nameMiniNode.generate ( this );
         out.println ( "" );
         out.println ( margin + demarkLine );
         out.println ( margin + "}" );
         out.println ( "" );
       }
     }

     public void  generateProgram ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       ProgramMiniNode  programMiniNode = ( ProgramMiniNode ) miniNode;

       out.println ( margin + "public class  " + className );
       out.println ( margin + demarkLine );
       out.println ( margin + "// Start of " + className );
       out.println ( margin + demarkLine );
       out.println ( margin + "{" );

       BlockMiniNode  blockMiniNode = programMiniNode.getBlockMiniNode ( );

       DeclarationSequenceMiniNode  declarationSequenceMiniNode
         = blockMiniNode.getDeclarationSequenceMiniNode ( );

       if ( declarationSequenceMiniNode != null )
       {
         out.println ( "" );
         declarationSequenceMiniNode.generate ( this );
       }

       out.println ( "" );
       out.println ( margin + "public void  procedure ( )" );

       out.println ( margin + demarkLine );

       out.println ( margin + "{" );

       depth++;

       blockMiniNode.getStatementSequenceMiniNode ( ).generate ( this );

       depth--;

       out.println ( margin + "}" );

       out.println ( "" );
       out.println (
         margin + "public static void  main ( String [ ]  args )" );
       out.println ( margin + demarkLine );
       out.println ( margin + "{" );
       out.println (
         margin + "  new " + className + " ( ).procedure ( );" );
       out.println ( margin + "}" );
       out.println ( "" );
       
       out.println ( margin + demarkLine );
       out.println ( margin + "// End of " + className );
       out.println ( margin + demarkLine );
       out.println ( margin + "}" );
     }

     public void  generateRelation ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       RelationMiniNode  relationMiniNode = ( RelationMiniNode ) miniNode;

       String  s = null;

       switch ( relationMiniNode.getRelation ( ) )
       {
         case MiniSymbols.EQ:  s = "=="; break;
         case MiniSymbols.LE:  s = "<="; break;
         case MiniSymbols.LT:  s = "<" ; break;
         case MiniSymbols.GT:  s = ">" ; break;
         case MiniSymbols.GE:  s = ">="; break;
         case MiniSymbols.NE:  s = "!="; break;

         default:
           throw new RuntimeException ( "unknown relation" );
       }

       out.print ( s );
     }

     public void  generateStatementSequence ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       StatementSequenceMiniNode  statementSequenceMiniNode
         = ( StatementSequenceMiniNode ) miniNode;

       generate ( null,
         statementSequenceMiniNode.getStatementMiniNodeList ( ),
         null, System.getProperty ( "line.separator" ) );
     }

     public void  generateTerm ( MiniNode  miniNode )
     //////////////////////////////////////////////////////////////////////
     {
       TermMiniNode  termMiniNode = ( TermMiniNode ) miniNode;

       MiniNode  term = termMiniNode.getTermMiniNode ( );

       if ( term != null )
       {
         term.generate ( this );

         out.print ( " " );

         termMiniNode.getOperatorMiniNode ( ).generate ( this );

         out.print ( " " );
       }

       termMiniNode.getElementMiniNode ( ).generate ( this );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }

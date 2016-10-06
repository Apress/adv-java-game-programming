     package com.croftsoft.apps.compiler.mini.node;

     /*********************************************************************
     * Generates code from MiniNode objects.
     *
     * @see
     *   MiniNode
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1999-04-24
     *********************************************************************/

     public interface  MiniNodeCodeVisitor
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     public void  generateAssignmentStatement     ( MiniNode  miniNode );

     public void  generateBlock                   ( MiniNode  miniNode );

     public void  generateComparison              ( MiniNode  miniNode );
                                                
     public void  generateConditionalStatement    ( MiniNode  miniNode );

     public void  generateConstant                ( MiniNode  miniNode );

     public void  generateDeclarationSequence     ( MiniNode  miniNode );

     public void  generateDefiniteLoopStatement   ( MiniNode  miniNode );

     public void  generateExpression              ( MiniNode  miniNode );

     public void  generateExpressionElement       ( MiniNode  miniNode );

     public void  generateExpressionSequence      ( MiniNode  miniNode );

     public void  generateIndefiniteLoopStatement ( MiniNode  miniNode );

     public void  generateInputStatement          ( MiniNode  miniNode );

     public void  generateIntegerDeclaration      ( MiniNode  miniNode );

     public void  generateName                    ( MiniNode  miniNode );

     public void  generateNameSequence            ( MiniNode  miniNode );

     public void  generateOperator                ( MiniNode  miniNode );

     public void  generateOutputStatement         ( MiniNode  miniNode );

     public void  generateParameterSequence       ( MiniNode  miniNode );

     public void  generateProcedureCallStatement  ( MiniNode  miniNode );

     public void  generateProcedureDeclaration    ( MiniNode  miniNode );

     public void  generateProgram                 ( MiniNode  miniNode );

     public void  generateRelation                ( MiniNode  miniNode );

     public void  generateStatementSequence       ( MiniNode  miniNode );

     public void  generateTerm                    ( MiniNode  miniNode );

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }

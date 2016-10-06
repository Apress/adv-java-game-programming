     package com.croftsoft.apps.insight;

     import java.awt.Point;

     public class Matrix {
     //////////////////////////////////////////////////////////////////////
     // Matrix.java v0.0 (C) Copyright 1996 David Wallace Croft.
     // 1996-08-24
     //////////////////////////////////////////////////////////////////////

     int             rows;
     int             columns;
     double [ ] [ ]  data;

     public Matrix ( int  rows, int  columns ) {
     //////////////////////////////////////////////////////////////////////
     // Constructor method
     //////////////////////////////////////////////////////////////////////
       this.rows    = rows;
       this.columns = columns;
       this.data    = new double [ rows ] [ columns ];
     }

     public Matrix ( Matrix  old ) {
     //////////////////////////////////////////////////////////////////////
     // Constructor method
     //////////////////////////////////////////////////////////////////////
       this ( old.rows, old.columns );
       for ( int index_row = 0; index_row < this.rows   ; index_row++ ) {
         for ( int index_col = 0; index_col < this.columns; index_col++ ) {
           this.data [ index_row ] [ index_col ]
             = old.data [ index_row ] [ index_col ];
         }
       }
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////

     public Matrix  add ( double  addend ) {
     //////////////////////////////////////////////////////////////////////
       Matrix  new_Matrix = new Matrix ( this );
     //////////////////////////////////////////////////////////////////////
       for ( int index_row = 0; index_row < this.rows   ; index_row++ ) {
       for ( int index_col = 0; index_col < this.columns; index_col++ ) {
         new_Matrix.data [ index_row ] [ index_col ] += addend;
       }
       }
       return new_Matrix;
     }

     public Matrix  add ( Matrix  addend ) {
     //////////////////////////////////////////////////////////////////////
       if ( ( this.rows    != addend.rows    )
         || ( this.columns != addend.columns ) ) {
         throw new ArrayIndexOutOfBoundsException (
           "Matrix.add ( Matrix ):  columns and rows not equal" );
       }
       Matrix  new_Matrix = new Matrix ( this );
     //////////////////////////////////////////////////////////////////////
       for ( int index_row = 0; index_row < this.rows   ; index_row++ ) {
       for ( int index_col = 0; index_col < this.columns; index_col++ ) {
         new_Matrix.data [ index_row ] [ index_col ]
           += addend.data [ index_row ] [ index_col ];
       }
       }
       return new_Matrix;
     }

     public double [ ]  array_column ( int  column ) {
     //////////////////////////////////////////////////////////////////////
       double [ ]  arr_col = new double [ this.rows ];
     //////////////////////////////////////////////////////////////////////
       for ( int index_row = 0; index_row < this.rows; index_row++ ) {
         arr_col [ index_row ] = this.data [ index_row ] [ column ];
       }
       return arr_col;
     }

     public Matrix  clip ( double  min, double  max ) {
     //////////////////////////////////////////////////////////////////////
       Matrix  new_Matrix = new Matrix ( this );
     //////////////////////////////////////////////////////////////////////
       for ( int index_row = 0; index_row < this.rows   ; index_row++ ) {
       for ( int index_col = 0; index_col < this.columns; index_col++ ) {
         if ( this.data [ index_row ] [ index_col ] < min )
           new_Matrix.data [ index_row ] [ index_col ] = min;
         else if ( this.data [ index_row ] [ index_col ] > max )
           new_Matrix.data [ index_row ] [ index_col ] = max;
       }
       }
       return new_Matrix;
     }

     static public void  demo ( ) {
     //////////////////////////////////////////////////////////////////////
       Matrix  left  = new Matrix ( 2, 3 );
       Matrix  right = new Matrix ( 3, 4 );
       for ( int index_row = 0; index_row < left.rows; index_row++ ) {
         for ( int index_col = 0; index_col < left.columns; index_col++ ) {
           left.data [ index_row ] [ index_col ] = index_row + index_col;
         }
       }
       for ( int index_row = 0; index_row < right.rows; index_row++ ) {
         for ( int index_col = 0; index_col < right.columns; index_col++ ) {
           right.data [ index_row ] [ index_col ] = index_row + index_col;
         }
       }
       left.display ( );
       System.out.println ( "" );
       right.display ( );
       System.out.println ( "" );
       Matrix  product = multiply ( left, right );
       product.display ( );
       System.out.println ( "" );
       Matrix  transposed = product.transpose ( );
       transposed.display ( );
       System.out.println ( "" );
       Matrix  sigmoided = transposed.sigmoid ( );
       sigmoided.display ( );
     }

     public void  display ( ) {
     //////////////////////////////////////////////////////////////////////
       String  line_String;
     //////////////////////////////////////////////////////////////////////
       for ( int index_row = 0; index_row < this.rows; index_row++ ) {
         line_String = "";
         for ( int index_col = 0; index_col < this.columns; index_col++ ) {
           line_String += this.data [ index_row ] [ index_col ] + " ";
         }
         System.out.println ( line_String );
       }
     }

     public Matrix  divide ( double  factor ) {
     //////////////////////////////////////////////////////////////////////
       Matrix  new_Matrix = new Matrix ( this );
     //////////////////////////////////////////////////////////////////////
       for ( int index_row = 0; index_row < this.rows   ; index_row++ ) {
       for ( int index_col = 0; index_col < this.columns; index_col++ ) {
         new_Matrix.data [ index_row ] [ index_col ] /= factor;
       }
       }
       return new_Matrix;
     }

     static public Matrix  identity ( Matrix  old ) {
     //////////////////////////////////////////////////////////////////////
       Matrix  new_Matrix = new Matrix ( old.rows, old.columns );
     //////////////////////////////////////////////////////////////////////
       for ( int index_row = 0; index_row < old.rows   ; index_row++ ) {
       for ( int index_col = 0; index_col < old.columns; index_col++ ) {
         new_Matrix.data [ index_row ] [ index_col ] = 1.0;
       }
       }
       return new_Matrix;
     }

     public Point  max_indices ( ) {
     //////////////////////////////////////////////////////////////////////
       int  max_row = 0;
       int  max_col = 0;
     //////////////////////////////////////////////////////////////////////
       for ( int index_col = 0; index_col < this.columns; index_col++ ) {
       for ( int index_row = 0; index_row < this.rows   ; index_row++ ) {
         if ( this.data [ index_row ] [ index_col ]
           > this.data [ max_row ] [ max_col ] ) {
           max_row = index_row;
           max_col = index_col;
         }
       }
       }
       return new Point ( max_row, max_col );
     }

     public Matrix  multiply ( double  factor ) {
     //////////////////////////////////////////////////////////////////////
       Matrix  new_Matrix = new Matrix ( this );
     //////////////////////////////////////////////////////////////////////
       for ( int index_row = 0; index_row < this.rows   ; index_row++ ) {
       for ( int index_col = 0; index_col < this.columns; index_col++ ) {
         new_Matrix.data [ index_row ] [ index_col ] *= factor;
       }
       }
       return new_Matrix;
     }

     public Matrix  multiply ( Matrix  right ) {
     //////////////////////////////////////////////////////////////////////
       return multiply ( this, right );
     }

     static public Matrix  multiply ( Matrix  left, Matrix  right ) {
     //////////////////////////////////////////////////////////////////////
       if ( left.columns != right.rows ) {
         throw new ArrayIndexOutOfBoundsException (
           "Matrix.multiply:  left.columns != right.rows" );
       }
       Matrix  product = new Matrix ( left.rows, right.columns );
     //////////////////////////////////////////////////////////////////////
       for ( int index_row = 0; index_row < product.rows; index_row++ ) {
         for ( int index_col = 0; index_col < product.columns; index_col++ ) {
           product.data [ index_row ] [ index_col ] = 0.0;
           for ( int index = 0; index < left.columns; index++ ) {
             product.data [ index_row ] [ index_col ]
               += left.data [ index_row ] [ index ]
               * right.data [ index ] [ index_col ];
           }
         }
       }
       return product;
     }

     static public Matrix  multiply_elements ( Matrix  a, Matrix  b ) {
     //////////////////////////////////////////////////////////////////////
       if ( ( a.rows != b.rows ) || ( a.columns != b.columns ) ) {
         throw new ArrayIndexOutOfBoundsException (
           "Matrix.multiply_pairwise:  rows or columns not equal" );
       }
       Matrix  product = new Matrix ( a.rows, a.columns );
     //////////////////////////////////////////////////////////////////////
       for ( int index_row = 0; index_row < a.rows   ; index_row++ ) {
       for ( int index_col = 0; index_col < a.columns; index_col++ ) {
         product.data [ index_row ] [ index_col ]
           = a.data [ index_row ] [ index_col ]
           * b.data [ index_row ] [ index_col ];
       }
       }
       return product;
     }

     public Matrix  randomize_uniform ( double  min, double  max ) {
     //////////////////////////////////////////////////////////////////////
       Matrix  new_Matrix = new Matrix ( this.rows, this.columns );
     //////////////////////////////////////////////////////////////////////
       for ( int index_row = 0; index_row < this.rows   ; index_row++ ) {
       for ( int index_col = 0; index_col < this.columns; index_col++ ) {
         new_Matrix.data [ index_row ] [ index_col ]
           = Croft_Random.uniform ( min, max );
       }
       }
       return new_Matrix;
     }

     public Matrix  sigmoid ( ) {
     //////////////////////////////////////////////////////////////////////
       Matrix  new_Matrix = new Matrix ( this.rows, this.columns );
     //////////////////////////////////////////////////////////////////////
       for ( int index_row = 0; index_row < this.rows   ; index_row++ ) {
       for ( int index_col = 0; index_col < this.columns; index_col++ ) {
         new_Matrix.data [ index_row ] [ index_col ]
           = Croft_Math.sigmoid ( this.data [ index_row ] [ index_col ] );
       }
       }
       return new_Matrix;
     }

     public Matrix  sigmoid_derivative ( ) {
     //////////////////////////////////////////////////////////////////////
       Matrix  new_Matrix = new Matrix ( this.rows, this.columns );
     //////////////////////////////////////////////////////////////////////
       for ( int index_row = 0; index_row < this.rows   ; index_row++ ) {
       for ( int index_col = 0; index_col < this.columns; index_col++ ) {
         new_Matrix.data [ index_row ] [ index_col ]
           = Croft_Math.sigmoid_derivative
           ( this.data [ index_row ] [ index_col ] );
       }
       }
       return new_Matrix;
     }

     public Matrix  square_elements ( ) {
     //////////////////////////////////////////////////////////////////////
       Matrix  new_Matrix = new Matrix ( this.rows, this.columns );
       for ( int index_row = 0; index_row < this.rows   ; index_row++ ) {
       for ( int index_col = 0; index_col < this.columns; index_col++ ) {
         new_Matrix.data [ index_row ] [ index_col ]
           = this.data [ index_row ] [ index_col ]
           * this.data [ index_row ] [ index_col ];
       }
       }
       return new_Matrix;
     }

     public Matrix  sub_matrix (
       int  row_start, int  row_end,
       int  col_start, int  col_end ) {
     //////////////////////////////////////////////////////////////////////
     //  Note that the first row is row 0.
     //////////////////////////////////////////////////////////////////////
       Matrix  sub
         = new Matrix ( row_end - row_start + 1, col_end - col_start + 1 );
     //////////////////////////////////////////////////////////////////////
       for ( int index_row = 0; index_row < sub.rows   ; index_row++ ) {
       for ( int index_col = 0; index_col < sub.columns; index_col++ ) {
         sub.data [ index_row ] [ index_col ]
           = this.data [ index_row + row_start ] [ index_col + col_start ];
       }
       }
       return sub;
     }

     public Matrix  subtract ( Matrix  subtractor ) {
     //////////////////////////////////////////////////////////////////////
       if ( ( this.rows    != subtractor.rows    )
         || ( this.columns != subtractor.columns ) ) {
         throw new ArrayIndexOutOfBoundsException (
           "Matrix.subtract ( Matrix ):  columns and rows not equal" );
       }
       Matrix  new_Matrix = new Matrix ( this );
     //////////////////////////////////////////////////////////////////////
       for ( int index_row = 0; index_row < this.rows   ; index_row++ ) {
       for ( int index_col = 0; index_col < this.columns; index_col++ ) {
         new_Matrix.data [ index_row ] [ index_col ]
           -= subtractor.data [ index_row ] [ index_col ];
       }
       }
       return new_Matrix;
     }

     public double  sum ( ) {
     //////////////////////////////////////////////////////////////////////
       double  temp = 0.0;
     //////////////////////////////////////////////////////////////////////
       for ( int index_row = 0; index_row < this.rows   ; index_row++ ) {
       for ( int index_col = 0; index_col < this.columns; index_col++ ) {
         temp += this.data [ index_row ] [ index_col ];
       }
       }
       return temp;
     }

     public Matrix  transpose ( ) {
     //////////////////////////////////////////////////////////////////////
       Matrix  transposed = new Matrix ( this.columns, this.rows );
     //////////////////////////////////////////////////////////////////////
       for ( int index_row = 0;
                 index_row < transposed.rows;
                 index_row++ ) {
         for ( int index_col = 0;
                   index_col < transposed.columns;
                   index_col++ ) {
           transposed.data [ index_row ] [ index_col ]
             = this.data [ index_col ] [ index_row ];
         }
       }
       return transposed;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }

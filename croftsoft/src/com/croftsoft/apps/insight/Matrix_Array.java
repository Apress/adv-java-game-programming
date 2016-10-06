     package com.croftsoft.apps.insight;

     public class Matrix_Array {
     //////////////////////////////////////////////////////////////////////
     // Matrix_Array.java v0.0 (C) Copyright 1996 David Wallace Croft.
     // 1996-09-02
     //////////////////////////////////////////////////////////////////////

     Matrix [ ]  m;  // matrices

     public Matrix_Array ( int  matrices_count ) {
     //////////////////////////////////////////////////////////////////////
     // Constructor method
     //////////////////////////////////////////////////////////////////////
       this.m = new Matrix [ matrices_count ];
       for ( int index_matrix = 0;
                 index_matrix < matrices_count;
                 index_matrix++ ) {
         this.m [ index_matrix ] = new Matrix ( 1, 1 );
       }
     }

     public Matrix_Array ( Matrix [ ]  matrices_array ) {
     //////////////////////////////////////////////////////////////////////
     // Constructor method
     //////////////////////////////////////////////////////////////////////
       this.m = new Matrix [ matrices_array.length ];
       for ( int index_matrix = 0;
                 index_matrix < matrices_array.length;
                 index_matrix++ ) {
         this.m [ index_matrix ]
           = new Matrix ( matrices_array [ index_matrix ] );
       }
     }

     public Matrix_Array ( Matrix_Array  template ) {
     //////////////////////////////////////////////////////////////////////
     // Constructor method
     //////////////////////////////////////////////////////////////////////
       this.m = new Matrix [ template.m.length ];
       for ( int index_matrix = 0;
                 index_matrix < template.m.length;
                 index_matrix++ ) {
         this.m [ index_matrix ]
           = new Matrix ( template.m [ index_matrix ] );
       }
     }

     public Matrix_Array  add ( Matrix_Array  addend ) {
     //////////////////////////////////////////////////////////////////////
       if ( this.m.length != addend.m.length ) {
         throw new ArrayIndexOutOfBoundsException (
           "Matrix_Array.add ( Matrix_Array ):  arrays mismatched" );
       }
       Matrix_Array  new_matrix_array = new Matrix_Array ( this );
       for ( int index_matrix = 0;
                 index_matrix < this.m.length;
                 index_matrix++ ) {
         new_matrix_array.m [ index_matrix ]
           = this.m [ index_matrix ].add ( addend.m [ index_matrix ] );
       }
       return new_matrix_array;
     }

     public Matrix_Array  multiply ( double  factor ) {
     //////////////////////////////////////////////////////////////////////
       Matrix_Array  new_matrix_array = new Matrix_Array ( this );
       for ( int index_matrix = 0;
                 index_matrix < this.m.length;
                 index_matrix++ ) {
         new_matrix_array.m [ index_matrix ]
           = this.m [ index_matrix ].multiply ( factor );
       }
       return new_matrix_array;
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }

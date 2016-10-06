     package com.croftsoft.apps.compiler;
     import java.io.*;
     import java.text.*;
     import java.util.*;
     import java_cup.runtime.*;
     /*********************************************************************
     * Token scanner for Calc.
     *
     * <B>Reference:</B>
     *
     * <P>
     *
     * "JLex: A Lexical Analyzer Generator for Java"<BR>
     * <A HREF="http://www.cs.princeton.edu/~appel/modern/java/JLex/">
     * http://www.cs.princeton.edu/~appel/modern/java/JLex/</A>
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1999-03-15
     *********************************************************************/
     //////////////////////////////////////////////////////////////////////
     // Portions of this code machine-generated by JLex.
     //////////////////////////////////////////////////////////////////////


public class CalcScanner implements CalcSymbols {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final char YYEOF = '\uFFFF';

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     private static final String [ ]  TEST_DATA = {
         "1",
         "22",
         "333",
         ";",
         "*",
         "write(a);",
         "bill_123" };
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     public static void  main ( String [ ]  args )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       for ( int  i = 0; i < TEST_DATA.length; i++ )
       {
         test ( TEST_DATA [ i ] );
       }
     }
     public static void  test ( String  testText )
       throws Exception
     //////////////////////////////////////////////////////////////////////
     {
       Reader  reader = new StringReader ( testText );
       CalcScanner  calcScanner = new CalcScanner ( reader );
       Symbol  symbol = null;
       loop:
       while ( ( symbol = calcScanner.nextToken ( ) ).sym != EOF )
       {
         System.out.println (
           "Symbol:  " + symbol + "  Value:  " + symbol.value );
       }
     }
     public static Symbol  scanInteger ( String  text )
     //////////////////////////////////////////////////////////////////////
     {
       try
       {
         return new Symbol ( INTEGER, new Integer ( text ) );
       }
       catch ( NumberFormatException  ex )
       {
         return null;
       }
     }
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private int yy_lexical_state;

	public CalcScanner (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public CalcScanner (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private CalcScanner () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private char yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YYEOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YYEOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_start () {
		if ((byte) '\n' == yy_buffer[yy_buffer_start]) {
			++yyline;
		}
		++yychar;
		++yy_buffer_start;
	}
	private void yy_pushback () {
		--yy_buffer_end;
	}
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ((byte) '\n' == yy_buffer[i]) {
				++yyline;
			}
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
private int [][] unpackFromString(int size1, int size2, String st)
    {
      int colonIndex = -1;
      String lengthString;
      int sequenceLength = 0;
      int sequenceInteger = 0;
      int commaIndex;
      String workString;
      int res[][] = new int[size1][size2];
      for (int i= 0; i < size1; i++)
	for (int j= 0; j < size2; j++)
	  {
	    if (sequenceLength == 0) 
	      {	
		commaIndex = st.indexOf(',');
		if (commaIndex == -1)
		  workString = st;
		else
		  workString = st.substring(0, commaIndex);
		st = st.substring(commaIndex+1);
		colonIndex = workString.indexOf(':');
		if (colonIndex == -1)
		  {
		    res[i][j] = Integer.parseInt(workString);
		  }
		else 
		  {
		    lengthString = workString.substring(colonIndex+1);  
		    sequenceLength = Integer.parseInt(lengthString);
		    workString = workString.substring(0,colonIndex);
		    sequenceInteger = Integer.parseInt(workString);
		    res[i][j] = sequenceInteger;
		    sequenceLength--;
		  }
	      }
	    else 
	      {
		res[i][j] = sequenceInteger;
		sequenceLength--;
	      }
	  }
      return res;
    }
	private int yy_acpt[] = {
		YY_NOT_ACCEPT,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR,
		YY_NO_ANCHOR
	};
	private int yy_cmap[] = {
		0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0,
		0, 0, 0, 0, 0, 0, 0, 0,
		0, 1, 0, 0, 0, 2, 0, 0,
		3, 4, 5, 6, 0, 7, 0, 8,
		9, 9, 9, 9, 9, 9, 9, 9,
		9, 9, 10, 11, 12, 13, 14, 0,
		0, 15, 15, 15, 15, 15, 15, 15,
		15, 15, 15, 15, 15, 15, 15, 15,
		15, 15, 15, 15, 15, 15, 15, 15,
		15, 15, 15, 0, 0, 0, 16, 17,
		0, 15, 15, 15, 15, 18, 15, 15,
		15, 19, 15, 15, 15, 15, 15, 15,
		15, 15, 20, 15, 21, 15, 15, 22,
		15, 15, 15, 0, 0, 0, 0, 0
		
	};
	private int yy_rmap[] = {
		0, 1, 1, 1, 1, 1, 1, 1,
		1, 2, 1, 1, 1, 3, 1, 1,
		1, 1, 3, 4, 5, 6, 7, 8,
		9, 10 
	};
	private int yy_nxt[][] = unpackFromString(11,23,
"1,19,2,3,4,5,6,7,8,9,21,10,11,22,12,13,14,1,13:4,25,-1:32,9,-1:22,13,-1:5,13,-1,13:6,-1:13,15,-1:18,13,-1:5,13,-1,13,18,13:4,-1:13,16,-1:22,17,-1:18,13,-1:5,13,-1,13:4,20,13,-1:9,13,-1:5,13,-1,13:2,23,13:3,-1:9,13,-1:5,13,-1,13:3,24,13:2");
	public Symbol nextToken ()
		throws java.io.IOException {
		char yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			if (YYEOF != yy_lookahead) {
				yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YYEOF == yy_lookahead && true == yy_initial) {

  return new Symbol ( EOF );
				}
				else if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_to_mark();
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_pushback();
					}
					if (0 != (YY_START & yy_anchor)) {
						yy_move_start();
					}
					switch (yy_last_accept_state) {
					case 1:
						{ yybegin ( YYINITIAL ); }
					case -2:
						break;
					case 2:
						{ return new Symbol ( MOD       ); }
					case -3:
						break;
					case 3:
						{ return new Symbol ( LPAREN    ); }
					case -4:
						break;
					case 4:
						{ return new Symbol ( RPAREN    ); }
					case -5:
						break;
					case 5:
						{ return new Symbol ( TIMES     ); }
					case -6:
						break;
					case 6:
						{ return new Symbol ( PLUS      ); }
					case -7:
						break;
					case 7:
						{ return new Symbol ( MINUS     ); }
					case -8:
						break;
					case 8:
						{ return new Symbol ( DIVIDE    ); }
					case -9:
						break;
					case 9:
						{ return scanInteger ( yytext ( ) ); }
					case -10:
						break;
					case 10:
						{ return new Symbol ( SEMICOLON ); }
					case -11:
						break;
					case 11:
						{ return new Symbol ( LT        ); }
					case -12:
						break;
					case 12:
						{ return new Symbol ( GT        ); }
					case -13:
						break;
					case 13:
						{ return new Symbol ( VARIABLE, yytext ( ) );  }
					case -14:
						break;
					case 14:
						{ return new Symbol ( EXP       ); }
					case -15:
						break;
					case 15:
						{ return new Symbol ( NEQ       ); }
					case -16:
						break;
					case 16:
						{ return new Symbol ( ASSIGN    ); }
					case -17:
						break;
					case 17:
						{ return new Symbol ( EQ        ); }
					case -18:
						break;
					case 18:
						{ return new Symbol ( WRITE     ); }
					case -19:
						break;
					case 19:
						{ yybegin ( YYINITIAL ); }
					case -20:
						break;
					case 20:
						{ return new Symbol ( VARIABLE, yytext ( ) );  }
					case -21:
						break;
					case 21:
						{ yybegin ( YYINITIAL ); }
					case -22:
						break;
					case 22:
						{ yybegin ( YYINITIAL ); }
					case -23:
						break;
					case 23:
						{ return new Symbol ( VARIABLE, yytext ( ) );  }
					case -24:
						break;
					case 24:
						{ return new Symbol ( VARIABLE, yytext ( ) );  }
					case -25:
						break;
					case 25:
						{ return new Symbol ( VARIABLE, yytext ( ) );  }
					case -26:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
					}
				}
			}
		}
	}
}

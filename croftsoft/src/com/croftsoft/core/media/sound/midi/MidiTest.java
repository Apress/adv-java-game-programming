     package com.croftsoft.core.media.sound.midi;

     import javax.sound.midi.*;

     /*********************************************************************
     * Tests all MIDI Instruments.
     *
     * <P>
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   2001-05-29
     * @version
     *   1999-11-07
     *********************************************************************/
     public final class  MidiTest
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     private  MidiTest ( ) { }

     //////////////////////////////////////////////////////////////////////
     // Static methods
     //////////////////////////////////////////////////////////////////////

     public static void  main ( String [ ]  args )
       throws MidiUnavailableException
     //////////////////////////////////////////////////////////////////////
     {
       Sequencer  sequencer = MidiSystem.getSequencer ( );

       Synthesizer  synthesizer = MidiSystem.getSynthesizer ( );

       synthesizer.open ( );

       Instrument [ ]  instruments
         = synthesizer.getDefaultSoundbank ( ).getInstruments ( );

       MidiChannel [ ]  midiChannels = synthesizer.getChannels ( );

       for ( int  instrumentIndex = 0;
             instrumentIndex < instruments.length;
             instrumentIndex++ )
       {
         Instrument  instrument = instruments [ instrumentIndex ];

         System.out.println ( instrument.getName ( ) );

         synthesizer.loadInstrument ( instrument );

         midiChannels [ 0 ].programChange ( instrumentIndex );

         int  velocity = 64;

         for ( int  noteNumber = 0; noteNumber < 128; noteNumber++ )
         {
           midiChannels [ 0 ].noteOn ( noteNumber, velocity );

           try
           {
             Thread.sleep ( 100 );
           }
           catch ( InterruptedException  e )
           {
           }

           midiChannels [ 0 ].noteOff ( noteNumber );

           try
           {
             Thread.sleep ( 100 );
           }
           catch ( InterruptedException  e )
           {
           }

         }
       }

       synthesizer.close ( );

       sequencer.close ( );
     }

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }

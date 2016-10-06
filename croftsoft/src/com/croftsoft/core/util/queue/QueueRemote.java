     package com.croftsoft.core.util.queue;

     import java.rmi.Remote;
     import java.rmi.RemoteException;

     /*********************************************************************
     *
     * @author
     *   <A HREF="http://www.alumni.caltech.edu/~croft/">David W. Croft</A>
     * @version
     *   1998-11-27
     *********************************************************************/

     public interface  QueueRemote extends Remote
     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     {

     /*********************************************************************
     * Appends the object to the queue and notifies any threads that
     * are blocked in pull().
     *
     * @return
     *   Returns true if the object could be added to the queue.
     *********************************************************************/
     public boolean  append ( Object  o )
       throws RemoteException;

     /*********************************************************************
     * Poll this queue to see if an object is available, immediately
     * removing one if so.  If the queue is empty, this method
     * immediately returns null.
     *
     * @return
     *   An object, if one was immediately available, otherwise null.
     *********************************************************************/
     public Object  poll ( )
       throws RemoteException;

     /*********************************************************************
     * Remove the next object in this queue, blocking until one becomes
     * available.
     *
     * @throws InterruptedException
     *    If the wait is interrupted.
     *********************************************************************/
     public Object  pull ( ) throws InterruptedException,
       RemoteException;

     /*********************************************************************
     * Remove the next object in this queue, blocking until one becomes
     * available or the given timeout period expires.
     *
     * @param timeout
     *   If positive, block for up to <I>timeout</I> milliseconds while
     *   waiting for an object to be added to this queue.
     *   If zero, block indefinitely.
     *
     * @return
     *   An object, if one was available within the specified timeout
     *   period, otherwise null.
     *
     * @throws IllegalArgumentException
     *    If the value of the timeout argument is negative.
     * @throws InterruptedException
     *    If the timeout wait is interrupted.
     *********************************************************************/
     public Object  pull ( long  timeout )
       throws IllegalArgumentException, InterruptedException,
       RemoteException;

     /*********************************************************************
     * Replaces the first occurrence of any equal object in the queue.
     * The new object is placed in the same queue order as the old.
     * If the new object is unique, calls add(o).
     *
     * @return
     *   The old object, if available, otherwise null.
     *********************************************************************/
     public Object  replace ( Object  o )
       throws RemoteException;

     //////////////////////////////////////////////////////////////////////
     //////////////////////////////////////////////////////////////////////
     }

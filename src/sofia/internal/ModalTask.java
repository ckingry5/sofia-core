package sofia.internal;

import android.os.Bundle;
import android.os.Looper;

// -------------------------------------------------------------------------
/**
 * <p>
 * Android does not provide any real support for synchronously invoking modal
 * UI constructs on the GUI thread, such as dialog boxes or sub-activities.
 * Instead, callbacks are used to communicate this information back to the
 * caller. This class abuses knowledge of the internal workings of the Android
 * {@code MessageQueue} class in order to provide a synchronous modal
 * construct, by causing what is essentially a "nested" invocation of the GUI
 * thread's run loop so that messages can still be dispatched while the GUI
 * thread otherwise blocks, waiting for the modal task to communicate its
 * result.
 * </p><p>
 * This class is used as follows:
 * <ol>
 * <li>Create an anonymous instance of this class and implement its
 * {@link #run()} method to display the modal construct.</li>
 * <li>In the listener(s) for the modal construct, call the
 * {@link #endModal(Object)} method, passing to it the result that should be
 * returned by the modal task.</li>
 * <li>After the instance of this class is created, call the
 * {@link #executeTask()} method. This method will block until the listener(s)
 * for the modal construct indicate that the result is ready, which is then
 * returned by this method.
 * </li>
 * </ol>
 * </p>
 *
 * @param <E> the type of the result returned by the modal task
 *
 * @author  Tony Allevato
 * @version 2011.10.13
 */
public abstract class ModalTask<E>
{
    //~ Instance/static variables .............................................

    // The result returned by the modal task.
    private E result;

    private Bundle extras;


    // ----------------------------------------------------------
    public ModalTask()
    {
        extras = new Bundle();
    }


    //~ Methods ...............................................................

    // ----------------------------------------------------------
    /**
     * Subclasses should override this to present a modal construct such as a
     * dialog box on the screen.
     */
    protected abstract void run();


    // ----------------------------------------------------------
    public Bundle getExtras()
    {
        return extras;
    }


    // ----------------------------------------------------------
    /**
     * Called by user code to run the task and wait until its result is ready.
     * While the call is waiting, GUI events will continue to be processed, so
     * mouse clicks and repaint events will still be processed as expected.
     *
     * @return the result of the modal task
     */
    public E executeTask()
    {
        // Execute the user's code, which is expected to present a GUI
        // construct that we want to synchronously wait for, such as an alert
        // dialog.

        run();

        // Manually re-run the event dispatch loop. This method will block
        // until a quit message is put into the message queue, which happens
        // when the endModal method is called.

        Looper.loop();

        // Return the result that was sent to endModal.

        return result;
    }


    // ----------------------------------------------------------
    /**
     * Called from inside the modal task to notify that the task has ended and
     * generated a result.
     *
     * @param modalResult the result generated by the task
     */
    public void endModal(E modalResult)
    {
        this.result = modalResult;

        SofiaUtils.quitLoop();
    }
}

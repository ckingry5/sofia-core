package sofia.internal.events;

import java.util.List;

import android.view.MotionEvent;

//-------------------------------------------------------------------------
/**
 * TODO document
 *
 * @author  Tony Allevato
 * @version 2012.10.24
 */
public class MotionEventDispatcher extends EventDispatcher
{
    //~ Fields ................................................................

    private MethodTransformer xyTransformer;


    //~ Constructors ..........................................................

    // ----------------------------------------------------------
    public MotionEventDispatcher(String method)
    {
        super(method);
    }


    //~ Protected methods .....................................................

    // ----------------------------------------------------------
    @Override
    protected List<MethodTransformer> lookupTransformers(
            Object receiver, List<Class<?>> argTypes)
    {
        List<MethodTransformer> descriptors =
                super.lookupTransformers(receiver, argTypes);

        getXYTransformer().addIfSupportedBy(receiver, descriptors);

        return descriptors;
    }


    // ----------------------------------------------------------
    /**
     * Transforms an event with signature (MouseEvent event) to one with
     * signature (float x, float y).
     */
    protected MethodTransformer getXYTransformer()
    {
        if (xyTransformer == null)
        {
            xyTransformer = new MethodTransformer(float.class, float.class)
            {
                // ----------------------------------------------------------
                protected Object[] transform(Object... args)
                {
                    MotionEvent e = (MotionEvent) args[0];
                    return new Object[] { (float) e.getX(), (float) e.getY() };
                }
            };
        }

        return xyTransformer;
    }
}

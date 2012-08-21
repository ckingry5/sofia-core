package sofia.util;

//-------------------------------------------------------------------------
/**
 *  This subclass of {@link java.util.Random} adds extra methods useful
 *  for testing purposes.  Normally, you might generate a new random number
 *  by calling {@link #nextInt()}, {@link #nextDouble()}, or one of the
 *  other generation methods provided by {@link java.util.Random}.  Normally,
 *  this intentionally makes your code behave in a random way, which may
 *  make it harder to test.  This class allows you to control directly
 *  the sequence of numbers that will be generated by such calls.
 *
 *  <p>Suppose your code is written this way:</p>
 *  <pre>
 *  Random random = Random.generator();  // or new Random()
 *  ...
 *  int x = random.nextInt(64);
 *  </pre>
 *
 *  <p>You can then write test cases that look like this:</p>
 *  <pre>
 *  public void testSomeFeature()
 *  {
 *      // Set the return values for the next 6 calls to nextInt(),
 *      // No matter which instance of TestableRandom the method is called on
 *      TestableRandom.setNextInts(5, 10, 22, 13, 12, 47);
 *
 *      // Perform tests, knowing in advance the exact sequence of numbers
 *      // That will now be generated
 *  }
 *  </pre>
 *
 *  <p>This class provides separate methods to preset the sequence of
 *  booleans, ints, doubles, floats, bytes, or Gaussian-distributed doubles
 *  that will be generated.  You can pass in as many specific values to
 *  the setNext...() methods that you like, or you can even pass in an
 *  array:</p>
 *
 *  <pre>
 *  int[] someValues = new int[] { 1, 2, 3, 4, 5, 6, 7 };
 *  TestableRandom.setNextInts(someValues);
 *  </pre>
 *
 *  @author  Stephen Edwards
 *  @author  Last changed by $Author: stedwar2 $
 *  @version $Date: 2010/02/25 19:27:24 $
 */
public class Random
    extends java.util.Random
{
    //~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Returns a {@code Random} instance that is shared across
     * all classes in an application.  Using this shared instance of the
     * generator is preferable to allocating new instances of
     * {@code Random}. If you create several random generators in
     * succession, they will typically generate the same sequence of values.
     * @return  A shared {@code Random} object.
     */
    public static Random generator()
    {
        if (instance == null)
        {
            instance = new Random();
        }
        return instance;
    }


    // ----------------------------------------------------------
    /**
     * Creates a new random number generator. This constructor sets the seed
     * of the random number generator to a value very likely to be distinct
     * from any other invocation of this constructor.
     *
     * <p>Most clients will not use the constructor directly but will
     * instead call {@link #generator()} to obtain a {@code Random}
     * object that is shared by all classes in the application.
     */
    public Random()
    {
        super();
    }


    // ----------------------------------------------------------
    /**
     * Creates a new random number generator using a single long seed. The
     * seed is the initial value of the internal state of the pseudorandom
     * number generator which is maintained by method {@link #next(int)}.
     *
     * <p>The invocation new Random(seed) is equivalent to:</p>
     * <pre>
     * Random rnd = new Random();
     * rnd.setSeed(seed);
     * </pre>
     *
     * @param seed the initial seed
     * @see java.util.Random#setSeed(long)
     */
    public Random(long seed)
    {
        super(seed);
    }

    //~ Public static methods .................................................

    // ----------------------------------------------------------
    /**
     * This method allows one to provide a predefined series of values that
     * will override the results provided by {@link #nextInt()} and
     * {@link #nextInt(int)}.  This is useful during testing, when you want
     * to control the results generated by a random number generator.  If
     * you do not use this method, {@link #nextInt()} and {@link #nextInt(int)}
     * behave normally.
     *
     * <p>Note that the sequence of int values you provide will be shared
     * by all instances of this class--so, no matter how many TestableRandom
     * instances you have created, the sequence of random numbers generated
     * by calls to their methods will be determined by what you pass in
     * here.</p>
     *
     * <p>If previous values from an earlier call to this method have not
     * yet been used, they will be replaced by any parameters you provide
     * in the next call to this method.  If previous values from an earlier
     * call to this method have not yet been used, and you provide no
     * arguments in your next call to this method, those unused values will
     * be replaced with nothing, so normal pseudorandom generation behavior
     * will resume immediately.</p>
     *
     * @param values a sequence of int values to use as the results in
     *        subsequent calls to {@link #nextInt()} or {@link #nextInt(int)}
     */
    public static void setNextInts(int ... values)
    {
        synchronized (nextValues)
        {
            if (values != null && values.length > 0)
            {
                nextValues.nextInts = values;
            }
            else
            {
                nextValues.nextInts = null;
            }
            nextValues.nextIntPos = 0;
        }
    }


    // ----------------------------------------------------------
    /**
     * This method allows one to provide a predefined series of values that
     * will override the results provided by {@link #nextLong()}.  This is
     * useful during testing, when you want to control the results generated
     * by a random number generator.  If you do not use this method,
     * {@link #nextLong()} behaves normally.
     *
     * <p>Note that the sequence of long values you provide will be shared
     * by all instances of this class--so, no matter how many TestableRandom
     * instances you have created, the sequence of random numbers generated
     * by calls to their methods will be determined by what you pass in
     * here.</p>
     *
     * <p>If previous values from an earlier call to this method have not
     * yet been used, they will be replaced by any parameters you provide
     * in the next call to this method.  If previous values from an earlier
     * call to this method have not yet been used, and you provide no
     * arguments in your next call to this method, those unused values will
     * be replaced with nothing, so normal pseudorandom generation behavior
     * will resume immediately.</p>
     *
     * @param values a sequence of long values to use as the results in
     *        subsequent calls to {@link #nextLong()}
     */
    public static void setNextLongs(long ... values)
    {
        synchronized (nextValues)
        {
            if (values != null && values.length > 0)
            {
                nextValues.nextLongs = values;
            }
            else
            {
                nextValues.nextLongs = null;
            }
            nextValues.nextLongPos = 0;
        }
    }


    // ----------------------------------------------------------
    /**
     * This method allows one to provide a predefined series of values that
     * will override the results provided by {@link #nextBoolean()}.  This is
     * useful during testing, when you want to control the results generated
     * by a random number generator.  If you do not use this method,
     * {@link #nextBoolean()} behaves normally.
     *
     * <p>Note that the sequence of boolean values you provide will be shared
     * by all instances of this class--so, no matter how many TestableRandom
     * instances you have created, the sequence of random booleans generated
     * by calls to their methods will be determined by what you pass in
     * here.</p>
     *
     * <p>If previous values from an earlier call to this method have not
     * yet been used, they will be replaced by any parameters you provide
     * in the next call to this method.  If previous values from an earlier
     * call to this method have not yet been used, and you provide no
     * arguments in your next call to this method, those unused values will
     * be replaced with nothing, so normal pseudorandom generation behavior
     * will resume immediately.</p>
     *
     * @param values a sequence of boolean values to use as the results in
     *        subsequent calls to {@link #nextBoolean()}
     */
    public static void setNextBooleans(boolean ... values)
    {
        synchronized (nextValues)
        {
            if (values != null && values.length > 0)
            {
                nextValues.nextBooleans = values;
            }
            else
            {
                nextValues.nextBooleans = null;
            }
            nextValues.nextBooleanPos = 0;
        }
    }


    // ----------------------------------------------------------
    /**
     * This method allows one to provide a predefined series of values that
     * will override the results provided by {@link #nextFloat()}.  This is
     * useful during testing, when you want to control the results generated
     * by a random number generator.  If you do not use this method,
     * {@link #nextFloat()} behaves normally.
     *
     * <p>Note that the sequence of float values you provide will be shared
     * by all instances of this class--so, no matter how many TestableRandom
     * instances you have created, the sequence of random numbers generated
     * by calls to their methods will be determined by what you pass in
     * here.</p>
     *
     * <p>If previous values from an earlier call to this method have not
     * yet been used, they will be replaced by any parameters you provide
     * in the next call to this method.  If previous values from an earlier
     * call to this method have not yet been used, and you provide no
     * arguments in your next call to this method, those unused values will
     * be replaced with nothing, so normal pseudorandom generation behavior
     * will resume immediately.</p>
     *
     * @param values a sequence of float values to use as the results in
     *        subsequent calls to {@link #nextFloat()}
     */
    public static void setNextFloats(float ... values)
    {
        synchronized (nextValues)
        {
            if (values != null && values.length > 0)
            {
                nextValues.nextFloats = values;
            }
            else
            {
                nextValues.nextFloats = null;
            }
            nextValues.nextFloatPos = 0;
        }
    }


    // ----------------------------------------------------------
    /**
     * This method allows one to provide a predefined series of values that
     * will override the results provided by {@link #nextDouble()}.  This is
     * useful during testing, when you want to control the results generated
     * by a random number generator.  If you do not use this method,
     * {@link #nextDouble()} behaves normally.
     *
     * <p>Note that the sequence of double values you provide will be shared
     * by all instances of this class--so, no matter how many TestableRandom
     * instances you have created, the sequence of random numbers generated
     * by calls to their methods will be determined by what you pass in
     * here.</p>
     *
     * <p>If previous values from an earlier call to this method have not
     * yet been used, they will be replaced by any parameters you provide
     * in the next call to this method.  If previous values from an earlier
     * call to this method have not yet been used, and you provide no
     * arguments in your next call to this method, those unused values will
     * be replaced with nothing, so normal pseudorandom generation behavior
     * will resume immediately.</p>
     *
     * @param values a sequence of double values to use as the results in
     *        subsequent calls to {@link #nextDouble()}
     */
    public static void setNextDoubles(double ... values)
    {
        synchronized (nextValues)
        {
            if (values != null && values.length > 0)
            {
                nextValues.nextDoubles = values;
            }
            else
            {
                nextValues.nextDoubles = null;
            }
            nextValues.nextDoublePos = 0;
        }
    }


    // ----------------------------------------------------------
    /**
     * This method allows one to provide a predefined series of values that
     * will override the results provided by {@link #nextGaussian()}.  This is
     * useful during testing, when you want to control the results generated
     * by a random number generator.  If you do not use this method,
     * {@link #nextGaussian()} behaves normally.
     *
     * <p>Note that the sequence of double values you provide will be shared
     * by all instances of this class--so, no matter how many TestableRandom
     * instances you have created, the sequence of random numbers generated
     * by calls to their methods will be determined by what you pass in
     * here.</p>
     *
     * <p>If previous values from an earlier call to this method have not
     * yet been used, they will be replaced by any parameters you provide
     * in the next call to this method.  If previous values from an earlier
     * call to this method have not yet been used, and you provide no
     * arguments in your next call to this method, those unused values will
     * be replaced with nothing, so normal pseudorandom generation behavior
     * will resume immediately.</p>
     *
     * @param values a sequence of double values to use as the results in
     *        subsequent calls to {@link #nextGaussian()}
     */
    public static void setNextGaussians(double ... values)
    {
        synchronized (nextValues)
        {
            if (values != null && values.length > 0)
            {
                nextValues.nextGaussians = values;
            }
            else
            {
                nextValues.nextGaussians = null;
            }
            nextValues.nextGaussianPos = 0;
        }
    }


    // ----------------------------------------------------------
    /**
     * This method allows one to provide a predefined series of values that
     * will override the results provided by {@link #nextBytes(byte[])}.
     * This is useful during testing, when you want to control the results
     * generated by a random number generator.  If you do not use this method,
     * {@link #nextBytes(byte[])} behaves normally.
     *
     * <p>Note that the sequence of byte values you provide will be shared
     * by all instances of this class--so, no matter how many TestableRandom
     * instances you have created, the sequence of random numbers generated
     * by calls to their methods will be determined by what you pass in
     * here.</p>
     *
     * <p>If previous values from an earlier call to this method have not
     * yet been used, they will be replaced by any parameters you provide
     * in the next call to this method.  If previous values from an earlier
     * call to this method have not yet been used, and you provide no
     * arguments in your next call to this method, those unused values will
     * be replaced with nothing, so normal pseudorandom generation behavior
     * will resume immediately.</p>
     *
     * @param values a sequence of byte values to use as the results in
     *        subsequent calls to {@link #nextBytes(byte[])}
     */
    public static void setNextBytes(byte ... values)
    {
        synchronized (nextValues)
        {
            if (values != null && values.length > 0)
            {
                nextValues.nextBytes = values;
            }
            else
            {
                nextValues.nextBytes = null;
            }
            nextValues.nextBytePos = 0;
        }
    }


    //~ Public instance methods ...............................................

    // ----------------------------------------------------------
    /**
     * Returns the next pseudorandom, uniformly distributed {@code int}
     * value from this random number generator's sequence. The general
     * contract of {@code nextInt} is that one {@code int} value is
     * pseudorandomly generated and returned. All 2<font size="-1"><sup>32
     * </sup></font> possible {@code int} values are produced with
     * (approximately) equal probability.
     *
     * <p>If {@link #setNextInts(int...)} has been called, the next available
     * value from the provided sequence will be returned until that sequence
     * is exhausted.  One all provided values have been returned, then
     * true pseudorandom generation will resume.</p>
     *
     * @return the next pseudorandom, uniformly distributed {@code int}
     *         value from this random number generator's sequence
     */
    public int nextInt()
    {
        synchronized (nextValues)
        {
            if (nextValues.nextInts != null)
            {
                int result = nextValues.nextInts[nextValues.nextIntPos++];
                if (nextValues.nextIntPos >= nextValues.nextInts.length)
                {
                    nextValues.nextInts = null;
                    nextValues.nextIntPos = 0;
                }
                return result;
            }
        }
        return super.nextInt();
    }


    // ----------------------------------------------------------
    /**
     * Returns a pseudorandom, uniformly distributed {@code int} value
     * between 0 (inclusive) and the specified value (exclusive), drawn from
     * this random number generator's sequence.  The general contract of
     * {@code nextInt} is that one {@code int} value in the specified range
     * is pseudorandomly generated and returned.  All {@code n} possible
     * {@code int} values are produced with (approximately) equal
     * probability.
     *
     * <p>If {@link #setNextInts(int...)} has been called, the next available
     * value from the provided sequence (modulo n) will be returned until that
     * sequence is exhausted.  One all provided values have been returned, then
     * true pseudorandom generation will resume.</p>
     *
     * @param n the bound on the random number to be returned.  Must be
     *        positive.
     * @return the next pseudorandom, uniformly distributed {@code int}
     *         value between {@code 0} (inclusive) and {@code n} (exclusive)
     *         from this random number generator's sequence
     * @exception IllegalArgumentException if n is not positive
     */
    public int nextInt(int n)
    {
        if (n <= 0)
        {
            throw new IllegalArgumentException("n must be positive");
        }

        synchronized (nextValues)
        {
            if (nextValues.nextInts != null)
            {
                int result = nextValues.nextInts[nextValues.nextIntPos++];
                if (nextValues.nextIntPos >= nextValues.nextInts.length)
                {
                    nextValues.nextInts = null;
                    nextValues.nextIntPos = 0;
                }
                return result % n;
            }
        }
        return super.nextInt(n);
    }


    // ----------------------------------------------------------
    /**
     * Returns the next random integer in the specified range.  For example,
     * you can generate the roll of a six-sided die by calling:
     * <pre>
     * generator.nextInt(1, 6);
     * </pre>
     *
     * <p>or a random decimal digit by calling::</p>
     *
     * <pre>
     * generator.nextInt(0, 9);
     * </pre>
     *
     * @param low  The low end of the range.
     * @param high The high end of the range.
     * @return The next random {@code int} between {@code low} and
     *         {@code high}, inclusive.
     */
    public int nextInt(int low, int high)
    {
        if (nextValues.nextInts != null || nextValues.nextDoubles != null)
        {
            int raw = nextInt();
            if (low <= raw && raw <= high)
            {
                return raw;
            }
            else
            {
                return low + raw % (high - low + 1);
            }
        }
        else
        {
            return low + (int) ((high - low + 1) * nextDouble());
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns the next pseudorandom, uniformly distributed {@code long}
     * value from this random number generator's sequence. The general
     * contract of {@code nextLong} is that one {@code long} value is
     * pseudorandomly generated and returned.
     *
     * <p>If {@link #setNextLongs(long...)} has been called, the next available
     * value from the provided sequence will be returned until that sequence
     * is exhausted.  One all provided values have been returned, then
     * true pseudorandom generation will resume.</p>
     *
     * @return the next pseudorandom, uniformly distributed {@code long}
     *         value from this random number generator's sequence
     */
    public long nextLong()
    {
        synchronized (nextValues)
        {
            if (nextValues.nextLongs != null)
            {
                long result = nextValues.nextLongs[nextValues.nextLongPos++];
                if (nextValues.nextLongPos >= nextValues.nextLongs.length)
                {
                    nextValues.nextLongs = null;
                    nextValues.nextLongPos = 0;
                }
                return result;
            }
        }
        return super.nextLong();
    }


    // ----------------------------------------------------------
    /**
     * Returns the next random {@code long} in the specified range.  Behaves
     * exactly like {@link #nextInt(int, int)}, but for {@code long} values.
     *
     * @param low  The low end of the range.
     * @param high The high end of the range.
     * @return The next random {@code int} between {@code low} and
     *         {@code high}, inclusive.
     */
    public long nextLong(long low, long high)
    {
        if (nextValues.nextLongs != null || nextValues.nextDoubles != null)
        {
            long raw = nextLong();
            if (low <= raw && raw <= high)
            {
                return raw;
            }
            else
            {
                return low + raw % (high - low + 1);
            }
        }
        else
        {
            return low + (long) ((high - low + 1) * nextDouble());
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns the next pseudorandom, uniformly distributed
     * {@code boolean} value from this random number generator's
     * sequence. The general contract of {@code nextBoolean} is that one
     * {@code boolean} value is pseudorandomly generated and returned.  The
     * values {@code true} and {@code false} are produced with
     * (approximately) equal probability.
     *
     * <p>If {@link #setNextBooleans(boolean...)} has been called, the next
     * available value from the provided sequence will be returned until that
     * sequence is exhausted.  One all provided values have been returned, then
     * true pseudorandom generation will resume.</p>
     *
     * @return the next pseudorandom, uniformly distributed {@code boolean}
     *         value from this random number generator's sequence
     */
    public boolean nextBoolean()
    {
        synchronized (nextValues)
        {
            if (nextValues.nextBooleans != null)
            {
                boolean result =
                    nextValues.nextBooleans[nextValues.nextBooleanPos++];
                if (nextValues.nextBooleanPos >= nextValues.nextBooleans.length)
                {
                    nextValues.nextBooleans = null;
                    nextValues.nextBooleanPos = 0;
                }
                return result;
            }
        }
        return super.nextBoolean();
    }


    // ----------------------------------------------------------
    /**
     * Returns the next pseudorandom, uniformly distributed
     * {@code float} value between {@code 0.0f} and
     * {@code 1.0f} from this random number generator's sequence.
     *
     * <p>If {@link #setNextFloats(float...)} has been called, the next
     * available value from the provided sequence will be returned until that
     * sequence is exhausted.  One all provided values have been returned, then
     * true pseudorandom generation will resume.</p>
     *
     * @return the next pseudorandom, uniformly distributed {@code float}
     *         value from this random number generator's sequence
     */
    public float nextFloat()
    {
        synchronized (nextValues)
        {
            if (nextValues.nextFloats != null)
            {
                float result =
                    nextValues.nextFloats[nextValues.nextFloatPos++];
                if (nextValues.nextFloatPos >= nextValues.nextFloats.length)
                {
                    nextValues.nextFloats = null;
                    nextValues.nextFloatPos = 0;
                }
                return result;
            }
        }
        return super.nextFloat();
    }


    // ----------------------------------------------------------
    /**
     * Returns the next random real number in the specified range.  The
     * resulting value is always at least {@code low} but always strictly
     * less than {@code high}. You can use this method to generate continuous
     * random values.  For example, you can set the variables {@code x} and
     * {@code y} to specify a random point inside the unit square as follows:
     *
     * <pre>
     * &nbsp;    float x = generator.nextFloat(0.0f, 1.0f);
     * &nbsp;    float y = generator.nextFloat(0.0f, 1.0f);
     * </pre>
     *
     * @param low  The low end of the range.
     * @param high The high end of the range.
     * @return A random {@code float} value <i>d</i> in the range
     * {@code low} &le; <i>d</i> &lt; {@code high}.
     */
    public float nextFloat(float low, float high)
    {
        float raw = nextFloat();
        if (low <= raw && raw < high)
        {
            return raw;
        }
        else
        {
            return low + (high - low) * raw;
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns the next pseudorandom, uniformly distributed
     * {@code double} value between {@code 0.0} and
     * {@code 1.0} from this random number generator's sequence.
     *
     * <p>If {@link #setNextDoubles(double...)} has been called, the next
     * available value from the provided sequence will be returned until that
     * sequence is exhausted.  One all provided values have been returned, then
     * true pseudorandom generation will resume.</p>
     *
     * @return the next pseudorandom, uniformly distributed {@code double}
     *         value from this random number generator's sequence
     */
    public double nextDouble()
    {
        synchronized (nextValues)
        {
            if (nextValues.nextDoubles != null)
            {
                double result =
                    nextValues.nextDoubles[nextValues.nextDoublePos++];
                if (nextValues.nextDoublePos >= nextValues.nextDoubles.length)
                {
                    nextValues.nextDoubles = null;
                    nextValues.nextDoublePos = 0;
                }
                return result;
            }
        }
        return super.nextDouble();
    }


    // ----------------------------------------------------------
    /**
     * Returns the next random real number in the specified range.  The
     * resulting value is always at least {@code low} but always strictly
     * less than {@code high}. You can use this method to generate continuous
     * random values.  For example, you can set the variables {@code x} and
     * {@code y} to specify a random point inside the unit square as follows:
     *
     * <pre>
     * &nbsp;    double x = generator.nextDouble(0.0, 1.0);
     * &nbsp;    double y = generator.nextDouble(0.0, 1.0);
     * </pre>
     *
     * @param low  The low end of the range.
     * @param high The high end of the range.
     * @return A random {@code double} value <i>d</i> in the range
     * {@code low} &le; <i>d</i> &lt; {@code high}.
     */
    public double nextDouble(double low, double high)
    {
        double raw = nextDouble();
        if (low <= raw && raw < high)
        {
            return raw;
        }
        else
        {
            return low + (high - low) * raw;
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns the next pseudorandom, Gaussian ("normally") distributed
     * {@code double} value with mean {@code 0.0} and standard
     * deviation {@code 1.0} from this random number generator's sequence.
     *
     * <p>If {@link #setNextGaussians(double...)} has been called, the next
     * available value from the provided sequence will be returned until that
     * sequence is exhausted.  One all provided values have been returned, then
     * true pseudorandom generation will resume.</p>
     *
     * @return the next pseudorandom, Gaussian ("normally") distributed
     *         {@code double} value with mean {@code 0.0} and
     *         standard deviation {@code 1.0} from this random number
     *         generator's sequence
     */
    public double nextGaussian()
    {
        synchronized (nextValues)
        {
            if (nextValues.nextGaussians != null)
            {
                double result =
                    nextValues.nextGaussians[nextValues.nextGaussianPos++];
                if (nextValues.nextGaussianPos
                    >= nextValues.nextGaussians.length)
                {
                    nextValues.nextGaussians = null;
                    nextValues.nextGaussianPos = 0;
                }
                return result;
            }
        }
        return super.nextGaussian();
    }


    // ----------------------------------------------------------
    /**
     * Generates random bytes and places them into a user-supplied
     * byte array.  The number of random bytes produced is equal to
     * the length of the byte array.
     *
     * <p>If {@link #setNextBytes(byte...)} has been called, unused
     * values from the provided sequence will be used to fill the provided
     * array until that sequence is exhausted.  One all provided values have
     * been used up, true pseudorandom generation will resume for filling
     * any remaining slots in this or future calls.</p>
     *
     * @param  bytes the byte array to fill with random bytes
     * @throws NullPointerException if the byte array is null
     */
    public void nextBytes(byte[] bytes)
    {
        synchronized (nextValues)
        {
            if (nextValues.nextBytes != null)
            {
                int remaining =
                    nextValues.nextBytes.length - nextValues.nextBytePos;
                if (remaining >= bytes.length)
                {
                    System.arraycopy(
                        nextValues.nextBytes,
                        nextValues.nextBytePos,
                        bytes,
                        0,
                        bytes.length);
                    nextValues.nextBytePos += bytes.length;
                }
                else
                {
                    int size =
                        nextValues.nextBytes.length - nextValues.nextBytePos;
                    System.arraycopy(
                        nextValues.nextBytes,
                        nextValues.nextBytePos,
                        bytes,
                        0,
                        size);
                    nextValues.nextBytePos += size;
                    byte[] remainder = new byte[bytes.length - size];
                    super.nextBytes(remainder);
                    System.arraycopy(
                        remainder,
                        0,
                        bytes,
                        bytes.length - remainder.length,
                        remainder.length);
                }
                if (nextValues.nextBytePos
                    >= nextValues.nextBytes.length)
                {
                    nextValues.nextBytes = null;
                    nextValues.nextBytePos = 0;
                }
                return;
            }
        }
        super.nextBytes(bytes);
    }


    //~ Private declarations ..................................................

    private static class NextValues
    {
        byte[]    nextBytes;
        int       nextBytePos;
        int[]     nextInts;
        int       nextIntPos;
        long[]    nextLongs;
        int       nextLongPos;
        boolean[] nextBooleans;
        int       nextBooleanPos;
        float[]   nextFloats;
        int       nextFloatPos;
        double[]  nextDoubles;
        int       nextDoublePos;
        double[]  nextGaussians;
        int       nextGaussianPos;
    }


    //~ Instance/static variables .............................................

    private static volatile NextValues nextValues = new NextValues();
    private static volatile Random instance;
    private static final long serialVersionUID = 6662016631254672060L;
}
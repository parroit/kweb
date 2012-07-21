package kw.model;


import junit.framework.Assert;
import org.junit.Test;


import javax.swing.*;

/**
* Author: parroit
* Created: 08/05/12 20.51
*/
public class FieldTypeManagerTest {
    private final FieldTypeManager<Integer> integralType = new FieldTypeManager<Integer>(Integer.class, FieldTypeManager.INTEGRAL_TYPE_FORMATTER, null) {
    };
    private final FieldTypeManager<Double> doubleType = new FieldTypeManager<Double>(Double.class, FieldTypeManager.DECIMAL_TYPE_FORMATTER, null) {
    };
    private final FieldTypeManager<CharSequence> charSequenceType = new FieldTypeManager(CharSequence.class, FieldTypeManager.CHAR_SEQUENCE_TYPE_FORMATTER, null) {
    };
    private final FieldTypeManager<Character> charType = new FieldTypeManager(Character.class, FieldTypeManager.CHAR_TYPE_FORMATTER, null) {
    };
    private final FieldTypeManager<JLabel> defaultParser = new FieldTypeManager(JLabel.class, null, FieldTypeManager.DEFAULT_PARSER) {
    };
    private final FieldTypeManager<Long> longPrimitiveParser = new FieldTypeManager(Long.TYPE, null, FieldTypeManager.LONG_PARSER) {
    };
    private final FieldTypeManager<Long> longParser = new FieldTypeManager(Long.class, null, FieldTypeManager.LONG_PARSER) {
    };

    @Test
    public void LongAreParsedAsLong() {

        Assert.assertSame(longParser.toRuntimeValue(Long.valueOf(12)).getClass(), Long.class);
    }

    @Test
    public void PrimitiveAreParsedAsLong() {

        Assert.assertSame(longParser.toRuntimeValue(12L).getClass(), Long.class);
    }

    @Test
    public void LongAreParsedAsPrimitive() {

        Assert.assertSame(longPrimitiveParser.toRuntimeValue(Long.valueOf(12)), 12L);
    }

    @Test
    public void PrimitiveAreParsedAsPrimitive() {

        Assert.assertSame(longPrimitiveParser.toRuntimeValue(12L), 12L);
    }

    @Test
    public void nullAreParsedAreNullByDefaultParser() {

        Assert.assertNull(defaultParser.toRuntimeValue(null));
    }

    @Test
    public void instanceAreParsedAreSelf() {

        JLabel label = new JLabel("test");
        Assert.assertSame(label, defaultParser.toRuntimeValue(label));
    }


    /*@Test
    public void registeredManagersAreFound() {
        FieldTypeManager.Registry.registerManager(charType, Character.class);

        Assert.assertSame(FieldTypeManager.Registry.findManager(Character.class), charType);
    }*/


    @Test
    public void nullIntegralFormattedAlaSql() {
        Assert.assertEquals(integralType.toEngineValue(null), "");
    }

    @Test
    public void nullCharSequenceFormattedAlaSql() {
        Assert.assertEquals(charSequenceType.toEngineValue(null), "");
    }

    @Test
    public void emptyStringFormattedWithSeparators() {
        Assert.assertEquals(charSequenceType.toEngineValue(""), "");
    }

    @Test
    public void workWithCharacterFormattedWithSeparators() {
        Assert.assertEquals(charType.toEngineValue('c'), "c");
    }

    @Test
    public void nullCharFormattedAsNull() {
        Assert.assertEquals(charType.toEngineValue('\0'), "");
    }

    @Test
    public void emptyBufferFormattedWithSeparators() {
        Assert.assertEquals(charSequenceType.toEngineValue(new StringBuffer()), "");
    }

    @Test
    public void simpleTextFormattedWithTextAndSeparators() {
        Assert.assertEquals(charSequenceType.toEngineValue("ciao"), "ciao");
    }

    @Test
    public void handleUnicode() {
        Assert.assertEquals(charSequenceType.toEngineValue("€èòa"), "€èòa");
    }




    @Test
    public void zeroIntegralFormattedAsZero() {
        Assert.assertEquals(integralType.toEngineValue(0), "0");
    }

    @Test
    public void positiveIntegralFormattedAsNumber() {
        Assert.assertEquals(integralType.toEngineValue(12340), "12340");
    }

    @Test
    public void negativeIntegralNumberFormattedWithSign() {
        Assert.assertEquals(integralType.toEngineValue(-12340), "-12340");
    }

    @Test
    public void zeroDecimalFormattedAsZero() {
        Assert.assertEquals(doubleType.toEngineValue(0.0), "0.0");
    }

    @Test
    public void positiveDecimalFormattedAsNumber() {
        Assert.assertEquals(doubleType.toEngineValue(12340.0), "12340.0");
    }

    @Test
    public void negativeDecimalFormattedWithSign() {
        Assert.assertEquals(doubleType.toEngineValue(-12340.0), "-12340.0");
    }

    @Test
    public void positiveDecimalPreservedFormattedAsNumber() {
        Assert.assertEquals(doubleType.toEngineValue(12340.123456), "12340.123456");
    }

    @Test
    public void negativeDecimalPreservedFormattedWithSign() {
        Assert.assertEquals(doubleType.toEngineValue(-12340.123456), "-12340.123456");
    }


}

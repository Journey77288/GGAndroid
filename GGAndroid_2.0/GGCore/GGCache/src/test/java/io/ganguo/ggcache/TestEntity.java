package io.ganguo.ggcache;

/**
 * Created by Lynn on 2016/12/16.
 */

class TestEntity {
    static final int TEST_INT = 100;
    static final long TEST_LONG = 10000L;
    static final char TEST_CHAR = 'G';
    static final boolean TEST_BOOLEAN = true;
    static final float TEST_FLOAT = 999F;
    static final String TEST_STRING = "test_entity_value";

    int intValue;
    long longValue;
    char charValue;
    float floatValue;
    boolean booleanValue;

    String strValue;

    @Override
    public String toString() {
        return "\nTestEntity {" + "\n" +
                "\tintValue=" + intValue + "\n" +
                "\tlongValue=" + longValue + "\n" +
                "\tcharValue=" + charValue + "\n" +
                "\tfloatValue=" + floatValue + "\n" +
                "\tbooleanValue=" + booleanValue + "\n" +
                "\tstrValue='" + strValue + '\'' + "\n" +
                '}';
    }

    public static TestEntity newTestEntity() {
        final TestEntity entity = new TestEntity();
        entity.intValue = TEST_INT;
        entity.longValue = TEST_LONG;
        entity.charValue = TEST_CHAR;
        entity.floatValue = TEST_FLOAT;
        entity.booleanValue = TEST_BOOLEAN;
        entity.strValue = TEST_STRING;

        return entity;
    }
}

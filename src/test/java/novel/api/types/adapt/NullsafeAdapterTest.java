package novel.api.types.adapt;

import novel.api.Novel;
import novel.internal.testutil.DataPenQueue;
import novel.internal.testutil.TestPrimitives;
import novel.api.types.token.TypeToken;
import novel.api.types.read.DataPaper;
import novel.api.types.read.DataPaperReadException;
import novel.api.types.write.pens.DataPen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

public class NullsafeAdapterTest {

    @Test
    void testRecursion() {
        var dummyAdapter = new ObjectDataAdapter<>() {
            @Override public void write(DataPen<?> pen, Object object) {}
            @Override public Object read(DataPaper paper) throws DataPaperReadException {return null;}
        };
        var nullsafe = dummyAdapter.nullSafe();
        Assertions.assertNotEquals(dummyAdapter, nullsafe);
        var nullsafeForReal = nullsafe.nullSafe();
        Assertions.assertEquals(nullsafe, nullsafeForReal);
        var nullsafeForRealsiesThisTime = nullsafeForReal.nullSafe().nullSafe()
            .nullSafe().nullSafe().nullSafe().nullSafe().nullSafe().nullSafe();
        Assertions.assertEquals(nullsafe, nullsafeForRealsiesThisTime);
    }

    @Test
    void testPrimitives() {
        var novel = Novel.newBuilder().build();
        for(var boxedPrim : TestPrimitives.BOXED_EXAMPLES) {
            testNullsafeAdapter(novel, boxedPrim);
        }
    }

    @Test
    void testRegisteredObject() {
        Novel novel = Novel.newBuilder().withAdapter(new ObjectDataAdapter<>(){
                @Override public void write(DataPen<?> pen, BigInteger object) { pen.strings(object);}
                @Override public BigInteger read(DataPaper paper) throws DataPaperReadException {
                    return new BigInteger(paper.strings());
                }
            }, BigInteger.class//Can replace with anything really, as long as it's registered
        ).build();
        testNullsafeAdapter(novel, BigInteger.TEN);
    }

    @SuppressWarnings("unchecked")
    <T> void testNullsafeAdapter(Novel novel, T nonNullExample) {
        if(nonNullExample == null) {
            throw new NullPointerException("nonNullExample is null...");
        }
        //Prepare for test
        TypeToken<T> type = (TypeToken<T>) TypeToken.get(nonNullExample.getClass());
        var possiblyUnsafeAdapter = novel.contents().adapter(type);
        Assertions.assertNotNull(possiblyUnsafeAdapter);
        var nullsafeAdapter = possiblyUnsafeAdapter.nullSafe();
        DataPenQueue pen;
        //Write & Read null
        pen = new DataPenQueue();
        nullsafeAdapter.write(pen, null);
        Assertions.assertNull(nullsafeAdapter.read(pen.paper()));
        //Write & Read nonNullExample
        pen = new DataPenQueue();
        nullsafeAdapter.write(pen, nonNullExample);
        Assertions.assertEquals(nonNullExample, nullsafeAdapter.read(pen.paper()));
    }
}

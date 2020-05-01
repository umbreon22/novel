package novel.internal.reflective;

import novel.api.Novel;
import novel.api.types.adapt.Novelable;
import novel.api.types.annotations.Folio;
import novel.api.types.annotations.Inked;
import novel.testutil.DataPenQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

public class ReflectiveAdapterTest {

    @Inked()
    static class Dummy implements Novelable {
        String firstField = "firstField";

        @Folio(2)
        String secondField = "secondField";

        String thirdField = "thirdField";

        @Folio(4)
        String fourthField = "fourthField";

        String fifthField = "fifthField";

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Dummy dummy = (Dummy) o;
            return Objects.equals(firstField, dummy.firstField) &&
                    Objects.equals(secondField, dummy.secondField) &&
                    Objects.equals(thirdField, dummy.thirdField) &&
                    Objects.equals(fourthField, dummy.fourthField) &&
                    Objects.equals(fifthField, dummy.fifthField);
        }
    }

    @Test
    void testNoFallbackOrder() {
        Novel noFallback = Novel.newBuilder().withoutFallbackOrdering().build();
        Dummy dummy = new Dummy();
        List<String> expectedOrder = List.of(//This is actually compiler-specific? not sure of a clean way to do it otherwise
            dummy.secondField, dummy.fourthField, dummy.firstField, dummy.thirdField, dummy.fifthField
        );
        testOrder(noFallback, dummy, expectedOrder);
    }

    @Test
    void testNamedFallbackOrder() {
        Novel nameFallback = Novel.newBuilder().withFallbackOrderingByName().build();
        Dummy dummy = new Dummy();
        List<String> expectedOrder = List.of(
                dummy.secondField, dummy.fourthField, dummy.fifthField, dummy.firstField, dummy.thirdField
        );
        testOrder(nameFallback, dummy, expectedOrder);
    }

    @Test
    void testNamedReverseOrder() {
        Novel reverseNameFallback = Novel.newBuilder().withFallbackOrderingBy((a, b) -> CharSequence.compare(b.getName(), a.getName())).build();
        Dummy dummy = new Dummy();
        List<String> expectedOrder = List.of(
                dummy.secondField, dummy.fourthField, dummy.thirdField, dummy.firstField, dummy.fifthField
        );
        testOrder(reverseNameFallback, dummy, expectedOrder);
    }

    private void testOrder(Novel novel, Dummy dummy, List<String> expectedOrder) {
        DataPenQueue pen = new DataPenQueue();
        novel.write(pen, dummy);
        Assertions.assertEquals(
                expectedOrder, pen.queue()
        );
        Dummy readInOrder = novel.read(pen.paper(), dummy.castToken());
        Assertions.assertEquals(dummy, readInOrder);
    }
}
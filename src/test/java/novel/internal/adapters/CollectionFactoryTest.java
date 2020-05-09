package novel.internal.adapters;

import novel.api.Novel;
import novel.api.streams.NovelPaperStream;
import novel.api.streams.NovelPenStream;
import novel.api.types.adapt.Novelable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

public class CollectionFactoryTest {

    @Test
    void simpleIOTest() throws IOException {
        var novel = Novel.newBuilder().build();
        var data = new Dummy();
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream();
            var out = new NovelPenStream(baos)) {
            novel.write(out, data);
            out.flush();
            try(var in = new NovelPaperStream(baos.toByteArray())) {
                Dummy dummy = novel.read(in, Dummy.class);
                Assertions.assertEquals(data, dummy);
            }
        }
    }

    enum TestEnum {
        A,
        B,
        C
    }

    private static class Dummy implements Novelable {

        List<Character> a = List.of('a');
        EnumSet<TestEnum> b = EnumSet.of(TestEnum.A, TestEnum.B, TestEnum.C);
        LinkedList<Integer> c = ((Supplier<LinkedList<Integer>>)()->{
            var ret = new LinkedList<Integer>();
            ret.add(2);
            ret.add(4);
            ret.add(6);
            return ret;
        }).get();
        Set<String> d = Set.of("d");

        ArrayDeque<String> e = ((Supplier<ArrayDeque<String>>)()->{
            var ret = new ArrayDeque<String>();
            ret.add("hello");
            ret.add("goodbye");
            ret.add("xD");
            return ret;
        }).get();

        AbstractList<Integer> f = new AbstractList<>() {
            @Override
            public Integer get(int index) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }
        };

        TreeSet<Boolean> g = ((Supplier<TreeSet<Boolean>>)()->{
            var ret = new TreeSet<Boolean>();
            ret.add(false);
            ret.add(true);
            return ret;
        }).get();

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Dummy)) return false;
            Dummy dummy = (Dummy) o;
            return iteratingEquals(a, dummy.a)
                    && iteratingEquals(b, dummy.b)
                    && iteratingEquals(c, dummy.c)
                    && iteratingEquals(d, dummy.d)
                    && iteratingEquals(e, dummy.e)
                    && iteratingEquals(f, dummy.f)
                    && iteratingEquals(g, dummy.g)
                    ;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b, c, d);
        }
    }

    private static boolean iteratingEquals(Collection a, Collection b) {
        if(a.size() != b.size()) {
            return false;
        }
        Iterator ita = a.iterator();
        Iterator itb = b.iterator();
        while(ita.hasNext() && itb.hasNext()) {
            if(!ita.next().equals(itb.next())) {
                return false;
            }
        }
        return true;

    }


}

